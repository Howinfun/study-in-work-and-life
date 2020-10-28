### 场景
不管是传统行业还是互联网行业，我们都需要保证大部分操作是幂等性的，简单点说，就是无论用户点击多少次，操作多少遍，产生的结果都是一样的，是唯一的。而今次公司的项目里，又被我遇到了这么一个幂等性的问题，就是用户的余额充值、创建订单和订单支付，不管用户点击多少次，只会有一条充值记录，一条新订单记录，一条订单支付记录。

### 技术方案
现在使用比较广泛的方案都是基于Redis。
**方案：Redis+token**
- 处理流程：数据提交前，前端要向服务端的申请token，token（带有过期时间）放到redis；当数据提交时带上token，如果删除token成功则表明token未过期，然后进行业务逻辑，
否则就是token已过期，提示前端请勿重复提交数据。

而我将使用不同的方案。因为此时前后端对接已走一半，不想让前端再增加请求token的接口（毕竟后端能搞定的，还是别麻烦前端同学了）。
**方案：自定义注解+分布式锁**
- 处理流程：将需要幂等性的接口加上自定义注解。然后编写一个切面，在around方法里逻辑：尝试获取分布式锁(带过期时间)，成功表明没重复提交，否则就是重复提交了。

### 讲解开始
**1、添加Redis依赖：**
```java
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
**2、自定义注解：**
```java
/**
 * @author Howinfun
 * @desc 自定义注解：分布式锁
 * @date 2019/11/12
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheLock {

    /** key前缀 */
    String prefix() default "";

    /** 过期秒数,默认为5秒 */
    int expire() default 5;

    /** 超时时间单位，默认为秒 */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /** Key的分隔符（默认 :）  */
    String delimiter() default ":";
}
```
**3、自定义切面：**
```java
/**
 * @author Howinfun
 * @desc 自定义切面
 * @date 2019/11/12
 */
@Aspect
@Component
public class LockCheckAspect {

    /** lua */
    private static final String RELEASE_LOCK_LUA_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 增强带有CacheLock注解的方法
    @Pointcut("@annotation(cn.gdmcmc.system.api.config.aop.CacheLock)")
    public void pointCut(){}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{

       // 可以根据业务获取用户唯一的个人信息，例如手机号码
       String phone = .....;
        
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        CacheLock cacheLock = method.getAnnotation(CacheLock.class);
        String prefix = cacheLock.prefix();
        if (StringUtils.isBlank(prefix)){
            throw new GlobalException("CacheLock prefix can't be null");
        }
        // 拼接 key
        String delimiter = cacheLock.delimiter();
        StringBuilder sb = new StringBuilder();
        sb.append(prefix).append(delimiter).append(phone);
        final String lockKey = sb.toString();
        final String UUID = cn.hutool.core.lang.UUID.fastUUID().toString();
        try {
            // 获取锁
            boolean success = redisTemplate.opsForValue().setIfAbsent(lockKey,UUID,cacheLock.expire(),cacheLock.timeUnit());
            if (!success){
                throw new CustomDeniedException("请勿重复提交");
            }
            Object result= joinPoint.proceed();
            return result;
        }finally {
            // 最后记得释放锁
            DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(RELEASE_LOCK_LUA_SCRIPT,Long.class);
            Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey),UUID);
        }

    }
}
```
**4、到此，只要为需要保证幂等性的接口上加上@CacheLock注解，就可以了。**
```java
@RestController
@RequestMapping(value = "/charge")
@AllArgsConstructor
public class ChargeController {    
    
    @PostMapping("/startCharge")
    @CacheLock(prefix = "recharge")
    public Result startCharge(@RequestBody @Validated({ChargeQuery.QRCodeNotBlank.class}) ChargeQuery query){
        return this.chargeChargeService.startCharge(query);
    }
}
```


