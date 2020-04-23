package com.example.demo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class ControllerAspect {

    // 定义切点Pointcut  自行写入对应的controller包路径
    @Pointcut("@annotation(com.example.demo.aspect.WebLogger)")
    public void webLoggerPointcut() {
    }

    //	前置增强
    @Before("webLoggerPointcut()")
    public void Mybefore(JoinPoint jp) {
        log.info("*前置增强*调用了【" + jp.getTarget().getClass().getSimpleName() +
                "】的【" + jp.getSignature().getName() + "】的方法，方法入参为【"
                + Arrays.toString(jp.getArgs()) + "】");
        // 接收到请求，记录请求内容(这里同样可以在前置增强配置请求的相关信息)
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("请求的地址URL : " + request.getRequestURL().toString());
        log.info("请求的方式HTTP_METHOD : " + request.getMethod());
        log.info("请求的IP : " + request.getRemoteAddr());
        log.info("请求的全类名 : " + jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName());
        log.info("请求的参数(数组形式) : " + Arrays.toString(jp.getArgs()));
    }

    //后置增强
    @AfterReturning(pointcut = "webLoggerPointcut()", returning = "result")
    public void MyafterReturing(JoinPoint jp, Object result) {
        log.info("*后置增强*调用了【" + jp.getTarget().getClass().getSimpleName() +
                "】的【" + jp.getSignature().getName() + "】的方法，方法返回值【" + result + "】");
    }

    //	异常抛出增强
    @AfterThrowing(pointcut = "webLoggerPointcut()", throwing = "e")
    public void afterThrowing(JoinPoint jp, RuntimeException e) {
        log.error("*异常增强*【" + jp.getSignature().getName().getClass().getSimpleName() + "】方法发生异常【" + e + "】");
    }

    //	最终增强
    @After("webLoggerPointcut()")
    public void afterLogger(JoinPoint jp) {
        log.info("*最终增强*【" + jp.getSignature().getName() + "】方法结束执行。");
    }

    //环绕增强
    @Around("webLoggerPointcut()")
    public Object aroundLogger(ProceedingJoinPoint jp) throws Throwable {
        log.info("在==>>" + jp.getTarget().getClass().getName() + "类里面使用AOP环绕增强==");
        log.info("*环绕增强*调用【" + jp.getTarget().getClass().getSimpleName() + "】的【 " + jp.getSignature().getName()
                + "】方法。方法入参【" + Arrays.toString(jp.getArgs()) + "】");
        try {
            Object result = jp.proceed();
            log.info("*环绕增强*调用 " + jp.getTarget() + "的【 "
                    + jp.getSignature().getName() + "】方法。方法返回值【" + result + "】");
            return result;
        } catch (Throwable e) {
            log.error(jp.getSignature().getName() + " 方法发生异常【" + e + "】");
            throw e;
        } finally {
            log.info("*环绕增强*执行finally【" + jp.getSignature().getName() + "】方法结束执行<<==。");
        }
    }


}