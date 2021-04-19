### Spring Boot 2.x 的两种 Redis 客户端

首先，我们都知道，从 **Spring Boot 2.x** 开始 **Lettuce** 已取代 **Jedis** 成为首选 Redis 的客户端。当然 Spring Boot 2.x 仍然支持 Jedis，并且你可以任意切换客户端。至于为什么会使用 Lettuce 替换 Jedis，大家可自行上网搜索。



### 我就是要使用 Jedis ？

那么如果我们还是要在项目中使用 `Jedis` 作为 Redis 的客户端呢？是不是引入 Jedis 依赖即可？下面来试试。

##### 引入依赖：

```xml
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
</dependency>
```

##### 配置：

可以加上下面的关于连接池配置，或者不加，因为有默认值。

```properties
spring.redis.jedis.pool.max-active=10
spring.redis.jedis.pool.min-idle=1
.....
```

##### 例子代码：

我们在访问接口时，打印 RedisTemplate 的 ConnectionFactory 即可知道使用的是哪个客户端。

```java
/**
 * @author Howinfun
 * @desc
 * @date 2020/1/15
 */
@RestController
public class TestController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/test")
    public String test(){
        System.out.println(redisTemplate.getConnectionFactory());
        return "hello";
    }
}
```

##### 神奇的 Lettuce 客户端：

启动项目，访问接口，可是我们看到，控制台打印出来的竟然还是 Lettuce 的ConnectionFactory。

```
org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory@21891c74
```



### 源码分析

##### Redis 的自动配置类：

首先看到RedisAutoConfiguration

```java
@Configuration
@ConditionalOnClass({RedisOperations.class})
@EnableConfigurationProperties({RedisProperties.class})
// 导入Lettuce和Jedis的连接配置类，而下面的创建RedisTemplate的RedisConnectionFactory在这两个配置类里都有生成。
@Import({LettuceConnectionConfiguration.class, JedisConnectionConfiguration.class})
public class RedisAutoConfiguration {
    public RedisAutoConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean(
        name = {"redisTemplate"}
    )
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    @ConditionalOnMissingBean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}

```

##### Lettuce 的连接配置类：

LettuceConnectionConfiguration

```java
@Configuration
// 当存在RedisClient.class时执行。ps：RedisClient是Lettuce依赖里面的类
@ConditionalOnClass({RedisClient.class})
class LettuceConnectionConfiguration extends RedisConnectionConfiguration {
    // .... 省略 

    @Bean
    // 当Spring容器不存在RedisConnectionFactory类型的bean对象时执行
    @ConditionalOnMissingBean({RedisConnectionFactory.class})
    public LettuceConnectionFactory redisConnectionFactory(ClientResources clientResources) throws UnknownHostException {
        
        LettuceClientConfiguration clientConfig = this.getLettuceClientConfiguration(clientResources, this.properties.getLettuce().getPool());
        // 创建connectinFactory
        return this.createLettuceConnectionFactory(clientConfig);
    }

    // ..... 省略
}
```

##### Jedis 的连接配置类：

JedisConnectionConfiguration

```java
@Configuration
// 当同时存在GenericObjectPool.class, JedisConnection.class, Jedis.class时执行。ps：JedisConnection和Jedis都是Jedis依赖里面的类。GenericObjectPool是spring-boot-starter-data-redis里的类。
@ConditionalOnClass({GenericObjectPool.class, JedisConnection.class, Jedis.class})
class JedisConnectionConfiguration extends RedisConnectionConfiguration {
    
    // .... 省略

    @Bean
    // 当Spring容器不存在RedisConnectionFactory类型的bean对象时执行
    @ConditionalOnMissingBean({RedisConnectionFactory.class})
    public JedisConnectionFactory redisConnectionFactory() throws UnknownHostException {
        
        // 创建connectinFactory
        return this.createJedisConnectionFactory();
    }

    // .... 省略
}
```

##### 总结：

从上面的源码分析可得。首先 Redis 的自动化配置依靠的是 `RedisAutoConfiguration` ，而   `RedisAutoConfiguration` 会按照顺序分别引入 `LettuceConnectionConfiguration` 和 `JedisConnectionConfiguration` 。而它们都会判断 `Spring容器` 中是否存在 `ConnectionFactory` ，不存在则创建。正是这个**引入的顺序**，导致 `LettuceConnectionConfiguration` 要先比 `JedisConnectionConfiguration` 执行，所以当 `LettuceConnectionConfiguration` 创建了 `ConnectionFactory` 后， `JedisConnectionConfiguration` 判断不为空而不继续创建了。所以即使我们引入了 `Jedis` 依赖，最后也还是使用 `Lettuce` 客户端。

<font color="red">ps： **Spring Boot 2.x** 使用 **Lettuce** 的原理：首先是依靠 **@Import** 的引入顺序，然后是 **spring-boot-starter-data-redis** 里有 **Lettuce** 的依赖，而没有 **Jedis** 的依赖 。</font>



### 如何正确使用 Jedis 客户端

从上面的源码分析看到，我们有两种方案。

##### 第一：直接去掉 Lettuce 的依赖

使 LettuceConnectionConfiguration 不能生效，因为 @ConditionalOnClass({RedisClient.class}) 的不再成立，导致其不会生效执行。

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
    <exclusions>
        <exclusion>
            <groupId>io.lettuce</groupId>
            <artifactId>lettuce-core</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```



##### 第二：在启动类的 @SpringBootApplication 去掉 RedisAutoConfiguration ，然后自己自定义一个 RedisAutoConfiguration

在自定义的 RedisAutoConfiguration 中修改 @Import 关于两个客户端的 ConnectionConfiguration 的引入顺序即可。但是其实这样做很没必要，还不如直接去掉依赖。

```java
@SpringBootApplication(scanBasePackages = "cn.gdmcmc",exclude = RedisAutoConfiguration.class)
```

```java
@Configuration
@ConditionalOnClass({RedisOperations.class})
@EnableConfigurationProperties({RedisProperties.class})
// 先 Jedis 后 Lettuce
@Import({JedisConnectionConfiguration.class, LettuceConnectionConfiguration.class})
public class MyRedisAutoConfiguration {
    public MyRedisAutoConfiguration() {
    }
    
    // ...... 省略
}
```



##### 最后

我们可以看到控制台打印的终于是 JedisConnectionFactory了。

```
org.springframework.data.redis.connection.jedis.JedisConnectionFactory@16c9834c
```

