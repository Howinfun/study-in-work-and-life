# Spring Cloud OpenFeign

## 简介

- Feign是一个声明式Web服务客户端。具备可插入的注释支持，包括Feign注释和JAX-RS注释。Feign还支持可插拔编码器和解码器。Spring Cloud增加了对Spring MVC注释的支持，并使用了`HttpMessageConverters` Spring WEB中默认使用的注释。Spring Cloud集成了Ribbon和Eureka，可在使用Feign时提供负载均衡的Http客户端。

## 依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

## 引导类

```java
@SpringBootApplication
@EnableFeignClients
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

## 定义服务请求接口

```java
@FeignClient("stores")
public interface StoreClient {
    @RequestMapping(method = RequestMethod.GET, value = "/stores")
    List<Store> getStores();

    @RequestMapping(method = RequestMethod.POST, value = "/stores/{storeId}", consumes = "application/json")
    Store update(@PathVariable("storeId") Long storeId, Store store);
}
```

- ` @FeignClient(value="服务名"，url = “”)`：
  - value：服务名指的是注册到Eureka Server调用的服务名称，也就是` spring.application.name` 。
  - url：还可以使用URL属性（绝对值或主机名）来指定URL。
  - configuration：Feign配置类，可以自定义Feign的Encoder、Decoder、LogLevel、Contract。
  - fallback：回退。定义容错处理类，当调用远程接口失败或者超时的时候，会调用对应接口的容错逻辑，fallback指定的类必须实现` @FeignClient` 标记的接口。
  - fallbackFactory：用于成Fallback类示例，通过重写create可以获取导致回退触发的原因。
  - path：定义当前FeignClient的统一前缀。
  - primary：是否定义为主要的，默认值为true。

## 调用

```java
@RestController
public class Controller {
    private final StoreClient storeClient;

    public CouponController(StoreClient storeClient) {
        this.storeClient = storeClient;
    }

    @PostMapping("/select")
    public ResponseEntity<ResultVo> exChangeCoupons() {
        /* 服务调用，避免因为回退到会Null导致出现NullPointException */
        Optional<ResultVo> resultVo = Optional.ofNullable(storeClient.getStores());
        return resultVo.map(o -> ResponseEntity.ok(new ResultVo(HttpStatus.OK.value(), "成功", o.getData())))
                .orElseGet(() -> ResponseEntity.ok(new ResultVo(HttpStatus.BAD_REQUEST.value(), "请求失败")));
    }
```



# Cloud-Feign-Hystrix回退

Spring Cloud OpenFeign默认集成了Hystrix，并且Feign启动Hystrix回退。如果需要在Feign中禁用Hystrix，则

```yaml
# To disable Hystrix in Feign
feign:
  hystrix:
    enabled: false
```

如果需要在Feign使用RequestInterceptor使用ThreadLocal，则需要

```yaml
# To disable Hystrix in Feign
feign:
  hystrix:
    enabled: false

# To set thread isolation to SEMAPHORE
hystrix:
  command:
    default:
      execution:
        isolation:
          strategy: SEMAPHORE
```

## Feign Hystrix 回退

- Hystrix支持回退的概念，当电路断开或者出现错误时执行的默认代码路径。要为` @FeignClient` 启动回退，有两种方式。

1. 使用` fallback=Class.class` 属性设置为实现回退的类名。

   ```java
   @FeignClient(name = "hello", fallback = HystrixClientFallback.class)
   protected interface HystrixClient {
       @RequestMapping(method = RequestMethod.GET, value = "/hello")
       Hello iFailSometimes();
   }
   
   static class HystrixClientFallback implements HystrixClient {
       @Override
       public Hello iFailSometimes() {
           return new Hello("fallback");
       }
   }
   ```

   

2. 如果需要访问导致回退触发的原因，可以使用` fallbackFactory=Class.class` 属性。

   ```java
   @FeignClient(name = "hello", fallbackFactory = HystrixClientFallbackFactory.class)
   protected interface HystrixClient {
   	@RequestMapping(method = RequestMethod.GET, value = "/hello")
   	Hello iFailSometimes();
   }
   
   @Component
   static class HystrixClientFallbackFactory implements FallbackFactory<HystrixClient> {
   	@Override
   	public HystrixClient create(Throwable cause) {
   		return new HystrixClient() {
   			@Override
   			public Hello iFailSometimes() {
   				return new Hello("fallback; reason was: " + cause.getMessage());
   			}
   		};
   	}
   }
   ```

## Feign请求/响应GZIP压缩

- 如果有需要为Feign请求或响应GZIP压缩。可以通过其中其中一个属性来执行此操作：

  ```properties
  feign.compression.request.enabled=true
  feign.compression.response.enabled=true
  ```

- Feign请求提供Web服务器设置：

  ```properties
  feign.compression.request.enabled=true
  feign.compression.request.mime-types=text/xml,application/xml,application/json
  feign.compression.request.min-request-size=2048
  ```

  