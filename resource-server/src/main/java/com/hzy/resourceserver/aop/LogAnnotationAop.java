package com.hzy.resourceserver.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author HZY
 * @created 2018/10/15 16:59
 */
@Aspect
@Component
public class LogAnnotationAop {

    public final static Logger logger = LoggerFactory.getLogger(LogAnnotationAop.class);

    //定义一个切入点LogAnnotation
    @Pointcut("@annotation(com.hzy.resourceserver.aop.LogAnnotation)")
    public void LogAnnotation(){
    }

    //定义一个切入点serviceStatistics
    @Pointcut("@annotation(logAnnotation)")
    public void serviceStatistics(LogAnnotation logAnnotation) {
    }

    //定义一个切入方法
    @Pointcut("execution(* com.hzy.resourceserver..*.*(..))")
    public void webLog(){
    }

    //通过定义的切入方法，拦截
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("我是AOP的before。。。。。。。。。。。");
    }

    //通过字典一的切入方法serviceStatistics 拦截，并通过logAnnotation注解获取属性值
    @After("serviceStatistics(logAnnotation)")
    public void doAfter(JoinPoint joinPoint,LogAnnotation logAnnotation) {
        System.out.println("我是AOP的doAfter==============="+logAnnotation.name());
    }

    //通过多个切入方法组合
    @Around(value = "LogAnnotation() && webLog() && @annotation(logAnnotation)")//环绕切点,在进入切点前,跟切点后执行
    public Object  doAround(ProceedingJoinPoint pjp, LogAnnotation logAnnotation)throws Throwable {
        Object result = null;
//        System.out.println("环绕。。。。。。1"+logAnnotation.value());

        //接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("URI : " + request.getRequestURI());
        logger.info("URL : " + request.getRequestURL());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + pjp.getSignature().getDeclaringTypeName() + "_" + pjp.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(pjp.getArgs()));
        System.out.println("我是AOP的Around。。。。。。。。。。。"+logAnnotation.name());
        return pjp.proceed();
    }

}
