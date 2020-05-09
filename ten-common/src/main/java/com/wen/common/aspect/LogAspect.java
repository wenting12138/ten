package com.wen.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("execution(* com.wen.*.web.*.*(..))")
    public void verify(){}

    @Before("com.wen.common.aspect.LogAspect.verify()")
    public void logBefore(JoinPoint joinPoint){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        request.setAttribute("RequestTime", System.currentTimeMillis());
        log.info("有请求到达: url: {}, ip: {}", url, ip);
    }


    @AfterReturning("com.wen.common.aspect.LogAspect.verify()")
    public void logAfter(JoinPoint joinPoint){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        request.setAttribute("RequestTime", System.currentTimeMillis());
        log.info("有请求结束: url: {}, ip: {}", url, ip);
        long start = (long) request.getAttribute("RequestTime");
        long end = System.currentTimeMillis();
        log.info("消耗时间: time: {}", (end - start) + "ms");
      }

}
