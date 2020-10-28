### 背景
有时候，我们需要一次性操作多个 Redis 命令，但是 这样的多个操作不具备原子性，而且 Redis 的事务也不够强大，不支持事务的回滚，还无法实现命令之间的逻辑关系计算。
所以，一般在开发中，我们会利用 lua 脚本来实现 Redis 的事务。

### lua 脚本
Redis 中使用 lua 脚本，我们需要注意的是，从 Redis 2.6.0后才支持 lua 脚本的执行。
使用 lua 脚本的好处：
- 原子操作：lua脚本是作为一个整体执行的，所以中间不会被其他命令插入。
- 减少网络开销：可以将多个请求通过脚本的形式一次发送，减少网络时延。
- 复用性：lua脚本可以常驻在redis内存中，所以在使用的时候，可以直接拿来复用，也减少了代码量。

### Redis 中执行 lua 脚本
1、命令格式：
```
EVAL script numkeys key [key ...] arg [arg ...]
```
说明：
- script是第一个参数，为Lua 5.1脚本(字符串)。
- 第二个参数numkeys指定后续参数有几个key。
- key [key ...]，被操作的key，可以多个，在lua脚本中通过KEYS[1], KEYS[2]获取
- arg [arg ...]，参数，可以多个，在lua脚本中通过ARGV[1], ARGV[2]获取。

2、如果直接使用 redis-cli命令：
```
redis-cli --eval lua_file key1 key2 , arg1 arg2 arg3
```
说明：
- eval 命令后不再是 lua 脚本的字符串形式，而是一个 lua 脚本文件。后缀为.lua
- 不再需要numkeys参数，而是用 , 隔开多个key和多个arg

### 使用 RedisTemplate 执行 lua 脚本
例子：删除 Redis 分布式锁
引入依赖：此依赖为我们整合了 Redis ，并且提供了非常好用的 RedisTemplate。
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
方式一：lua 脚本文件
1、新建 lua 脚本文件：
```
if redis.call("get",KEYS[1]) == ARGV[1] then
    return redis.call("del",KEYS[1])
else
    return 0
end
```
说明：先获取指定key的值，然后和传入的arg比较是否相等，相等值删除key，否则直接返回0

2、代码测试：
```java
/**
 * @author Howinfun
 * @desc lua 测试
 * @date 2019/11/5
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = ThirdPartyServerApplication.class)
public class RedisTest {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Test
    public void contextLoads() {
        String lockKey = "123";
        String UUID = cn.hutool.core.lang.UUID.fastUUID().toString();
        boolean success = redisTemplate.opsForValue().setIfAbsent(lockKey,UUID,3, TimeUnit.MINUTES);
        if (!success){
            System.out.println("锁已存在");
        }
        // 执行 lua 脚本
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        // 指定 lua 脚本
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis/DelKey.lua")));
        // 指定返回类型
        redisScript.setResultType(Long.class);
        // 参数一：redisScript，参数二：key列表，参数三：arg（可多个）
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey),UUID);
        System.out.println(result);
    }
}
```
方式二：直接编写 lua 脚本（字符串）
1、代码测试：
```java
/**
 * @author Howinfun
 * @desc lua 脚本测试
 * @date 2019/11/5
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = ThirdPartyServerApplication.class)
public class RedisTest {

    /** 释放锁lua脚本 */
    private static final String RELEASE_LOCK_LUA_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Test
    public void contextLoads() {
        String lockKey = "123";
        String UUID = cn.hutool.core.lang.UUID.fastUUID().toString();
        boolean success = redisTemplate.opsForValue().setIfAbsent(lockKey,UUID,3, TimeUnit.MINUTES);
        if (!success){
            System.out.println("锁已存在");
        }
        // 指定 lua 脚本，并且指定返回值类型
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(RELEASE_LOCK_LUA_SCRIPT,Long.class);
        // 参数一：redisScript，参数二：key列表，参数三：arg（可多个）
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey),UUID);
        System.out.println(result);
    }
}
```
注意：可能会有同学发现，为什么返回值不用 Integer 接收而是用 Long。这里是因为 spring-boot-starter-data-redis 提供的返回类型里面不支持 Integer。
大家可以看看源码：
```java
/**
 * Represents a data type returned from Redis, currently used to denote the expected return type of Redis scripting
 * commands
 *
 * @author Jennifer Hickey
 * @author Christoph Strobl
 */
public enum ReturnType {

	/**
	 * Returned as Boolean
	 */
	BOOLEAN,

	/**
	 * Returned as {@link Long}
	 */
	INTEGER,

	/**
	 * Returned as {@link List<Object>}
	 */
	MULTI,

	/**
	 * Returned as {@literal byte[]}
	 */
	STATUS,

	/**
	 * Returned as {@literal byte[]}
	 */
	VALUE;

	/**
	 * @param javaType can be {@literal null} which translates to {@link ReturnType#STATUS}.
	 * @return never {@literal null}.
	 */
	public static ReturnType fromJavaType(@Nullable Class<?> javaType) {

		if (javaType == null) {
			return ReturnType.STATUS;
		}
		if (javaType.isAssignableFrom(List.class)) {
			return ReturnType.MULTI;
		}
		if (javaType.isAssignableFrom(Boolean.class)) {
			return ReturnType.BOOLEAN;
		}
		if (javaType.isAssignableFrom(Long.class)) {
			return ReturnType.INTEGER;
		}
		return ReturnType.VALUE;
	}
}
```
所以当我们使用 Integer 作为返回值的时候，是报以下错误：
```java
org.springframework.data.redis.RedisSystemException: Redis exception; nested exception is io.lettuce.core.RedisException: java.lang.IllegalStateException

	at org.springframework.data.redis.connection.lettuce.LettuceExceptionConverter.convert(LettuceExceptionConverter.java:74)
	at org.springframework.data.redis.connection.lettuce.LettuceExceptionConverter.convert(LettuceExceptionConverter.java:41)
```