# 关于Feign的超时详解：

在`Spring Cloud`微服务架构中，大部分公司都是利用`Open Feign`进行服务间的调用，而比较简单的业务使用默认配置是不会有多大问题的，但是如果是业务比较复杂，服务要进行比较繁杂的业务计算，那后台很有可能会出现`Read Timeout`这个异常。

## 1、关于hystrix的熔断超时

如果`Feign`开启了熔断，必须要重新设置熔断超时的时间，因为默认的熔断超时时间太短了，只有1秒，这容易导致业务服务的调用还没完成然后超时就被熔断了。

如何配置熔断超时：

```properties
#Feign如何开启熔断
feign.hystrix.enabled=true

#是否开始超时熔断，如果为false，则熔断机制只在服务不可用时开启（spring-cloud-starter-openfeign中的HystrixCommandProperties默认为true）
hystrix.command.default.execution.timeout.enabled=true

#设置超时熔断时间（spring-cloud-starter-openfeign中的HystrixCommandProperties默认为1000毫秒）
hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=6000
```

<font color="red">**注意：关于`hystrix`在`application.properties`配置是没提示的，但是`HystrixCommandProperties`是会获取的。**</font>

```java
// 构造函数
protected HystrixCommandProperties(HystrixCommandKey key, HystrixCommandProperties.Setter builder, String propertyPrefix) {
        
	// .... 省略很多其他配置
  
  	// propertyPrefix：hystrix，key：default
    this.executionTimeoutInMilliseconds = getProperty(propertyPrefix, key, "execution.isolation.thread.timeoutInMilliseconds", builder.getExecutionIsolationThreadTimeoutInMilliseconds(), default_executionTimeoutInMilliseconds);
}

// 具体获取属性的方法
private static HystrixProperty<String> getProperty(String propertyPrefix, HystrixCommandKey key, String instanceProperty, String builderOverrideValue, String defaultValue) {
   return HystrixPropertiesChainedProperty.forString().add(propertyPrefix + ".command." + key.name() + "." + instanceProperty, builderOverrideValue).add(propertyPrefix + ".command.default." + instanceProperty, defaultValue).build();
}
```




## 2、关于Ribbon超时。

`Feign`调用默认是使用`Ribbon`进行负载均衡的，所以我们还需要了解关于`Ribbon`的超时。

### ①、Feign的调用链路
看一下Feign的请求是否有使用Ribbon的超时时间，而且是如何读取Ribbon的超时时间的？

(1)、org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient#execute

(2)、com.netflix.client.AbstractLoadBalancerAwareClient#executeWithLoadBalancer(S, com.netflix.client.config.IClientConfig)

(3)、org.springframework.cloud.openfeign.ribbon.CachingSpringLoadBalancerFactory#create

		​创建Client，这里会判断对应ClientName的链接Client是否创建过，如果创建过复用之前的Client；
		如果不存在则创建一个并且放入cache缓存。

```java
public FeignLoadBalancer create(String clientName) {
		FeignLoadBalancer client = this.cache.get(clientName);
		if(client != null) {
			return client;
		}
		IClientConfig config = this.factory.getClientConfig(clientName);
		ILoadBalancer lb = this.factory.getLoadBalancer(clientName);
		ServerIntrospector serverIntrospector = this.factory.getInstance(clientName, ServerIntrospector.class);
    	// 判断是否有重试
		client = loadBalancedRetryFactory != null ? new RetryableFeignLoadBalancer(lb, config, serverIntrospector,
			loadBalancedRetryFactory) : new FeignLoadBalancer(lb, config, serverIntrospector);
		this.cache.put(clientName, client);
		return client;
	}
```

(4)、com.netflix.client.AbstractLoadBalancerAwareClient#executeWithLoadBalancer(S, com.netflix.client.config.IClientConfig)

		​负载均衡器抽象类

(5)、org.springframework.cloud.openfeign.ribbon.FeignLoadBalancer#execute

	​	Feign的负载均衡器实现类。到这里我们可以看到，连接超时和读超时的配置都在这里：
		如果application.properties配置文件中的超时时间不为空，则使用配置的超时时间。
		如果为空则使用默认值，而从FeignLoadBalancer的构造函数可以看到，默认值也是取的RibbonProperties的默认超时时间。

```java
public RibbonResponse execute(RibbonRequest request, IClientConfig configOverride)
			throws IOException {
    Request.Options options;
    // 设置超时时间。，如果orride的配置为空，则用默认值
    if (configOverride != null) {
        RibbonProperties override = RibbonProperties.from(configOverride);
        options = new Request.Options(
            override.connectTimeout(this.connectTimeout),
            override.readTimeout(this.readTimeout));
    }
    else {
        options = new Request.Options(this.connectTimeout, this.readTimeout);
    }
    // 发起请求
    Response response = request.client().execute(request.toRequest(), options);
    return new RibbonResponse(request.getUri(), response);
}

// 构造函数
public FeignLoadBalancer(ILoadBalancer lb, IClientConfig clientConfig, ServerIntrospector serverIntrospector) {
    super(lb, clientConfig);
    this.setRetryHandler(RetryHandler.DEFAULT);
    this.clientConfig = clientConfig;
    this.ribbon = RibbonProperties.from(clientConfig);
    RibbonProperties ribbon = this.ribbon;
    this.connectTimeout = ribbon.getConnectTimeout();
    this.readTimeout = ribbon.getReadTimeout();
    this.serverIntrospector = serverIntrospector;
}
```

### ②、Ribbon的默认超时时间

在`RibbonClientConfiguration`中：

```java
public static final int DEFAULT_CONNECT_TIMEOUT = 1000;
public static final int DEFAULT_READ_TIMEOUT = 1000;
```

### ③、如何自定义Ribbon超时时间

 首先，`RibbonProperties`的超时时间的读取的源码如下：
```java
public Integer getConnectTimeout() {
    return (Integer)this.get(CommonClientConfigKey.ConnectTimeout);
}

public Integer getReadTimeout() {
    return (Integer)this.get(CommonClientConfigKey.ReadTimeout);
}
```

然后，可以在`CommonClientConfigKey`中可以看到两个超时时间的名称：

```java
// ConnectTimeout:
public static final IClientConfigKey<Integer> ConnectTimeout = new CommonClientConfigKey<Integer>("ConnectTimeout") {};

// ReadTimeout:
public static final IClientConfigKey<Integer> ReadTimeout = new CommonClientConfigKey<Integer>("ReadTimeout") {};
```

然后，在`IClientConfig`的默认实现类：`DefaultClientConfigImpl`中，可以发现`Ribbon`配置的前缀

```java
public static final String DEFAULT_PROPERTY_NAME_SPACE = "ribbon";
```

所以，最后`Ribbon`该这么配置超时时间：

```properties
ribbon.ConnectTimeout=5000
ribbon.ReadTimeout=5000
```
## 总结
如何配置好`Hystrix`和`Ribbon`的超时时间呢？
其实是有套路的。因为`Feign`的请求：其实是`Hystrix`+`Ribbon`。`Hystrix`在最外层，然后再到`Ribbon`，最后里面的是`http`请求。所以说。`Hystrix`的熔断时间必须大于`Ribbon`的 ( `ConnectTimeout` + `ReadTimeout` )。而如果`Ribbon`开启了重试机制，还需要乘以对应的重试次数，保证在`Ribbon`里的请求还没结束时，`Hystrix`的熔断时间不会超时。 
