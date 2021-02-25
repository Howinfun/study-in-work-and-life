package com.winfun.aop;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.winfun.entity.LogRecord;
import com.winfun.entity.enums.LogRecordEnum;
import com.winfun.mapper.LogRecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * LogRecord Aspect
 * @author winfun
 * @date 2020/11/3 4:52 下午
 **/
@Slf4j
@Aspect
@Component
public class LogRecordAspect {

    private ExpressionParser parser = new SpelExpressionParser();
    private LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    @Autowired
    ApplicationContext applicationContext;
    @Resource
    private LogRecordMapper logRecordMapper;

    @Pointcut(value = "@annotation(com.winfun.aop.LogRecordAnno)")
    public void pointcut(){}

    @Around("@annotation(logRecordAnno))")
    public void around(ProceedingJoinPoint point, LogRecordAnno logRecordAnno) throws Throwable {

        Object[] args = point.getArgs();
        Class mapperName = logRecordAnno.mapperName();
        BaseMapper mapper = (BaseMapper) applicationContext.getBean(mapperName);
        LogRecordEnum logRecordEnum = logRecordAnno.logType();
        LogRecord logRecord = new LogRecord();
        switch (logRecordEnum){
            case INSERT:
                logRecord.setLogType(LogRecordEnum.INSERT);
                point.proceed();
                //根据spel表达式获取值
                Method method = this.getMethod(point);
                EvaluationContext context = this.bindParam(method, args);
                Expression expression = parser.parseExpression(logRecordAnno.id());
                String id = (String) expression.getValue(context);
                logRecord.setBeforeRecord("");
                Object result = mapper.selectById(id);
                logRecord.setAfterRecord(JSON.toJSONString(result));
                break;
            case UPDATE:
                break;
            case DELETE:
                break;
            default:
                break;
        }
        // 插入记录
        logRecordMapper.insert(logRecord);
    }

    /**
     * 获取当前执行的方法
     *
     * @param pjp
     * @return
     * @throws NoSuchMethodException
     */
    private Method getMethod(ProceedingJoinPoint pjp) throws NoSuchMethodException {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        Method targetMethod = pjp.getTarget().getClass().getMethod(method.getName(), method.getParameterTypes());
        return targetMethod;
    }
    /**
     * 将方法的参数名和参数值绑定
     *
     * @param method 方法，根据方法获取参数名
     * @param args   方法的参数值
     * @return
     */
    private EvaluationContext bindParam(Method method, Object[] args) {
        //获取方法的参数名
        String[] params = discoverer.getParameterNames(method);

        //将参数名与参数值对应起来
        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0; len < params.length; len++) {
            context.setVariable(params[len], args[len]);
        }
        return context;
    }
}
