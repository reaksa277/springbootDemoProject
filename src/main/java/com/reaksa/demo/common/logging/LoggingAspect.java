package com.reaksa.demo.common.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class LoggingAspect {
    String LOG_FORMAT = "%s | className =  %s, method = %s";

    @Autowired
    private LogFormatter formatter;

    @Around("execution(* com.reaksa.demo.service..*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger log = (Logger) LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        long startTime = System.currentTimeMillis();
        String requestId = UUID.randomUUID().toString();

        log.info(LOG_FORMAT.formatted("Request", className, methodName,startTime));

        try {
            // execute the original method logic
            Object result = joinPoint.proceed();

            long endTime = System.currentTimeMillis();
            // logging
            log.info(LOG_FORMAT.formatted("Response", className, methodName, startTime, endTime));

            return result;
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();

            log.info(LOG_FORMAT.formatted("Error", className, methodName, startTime, endTime));

            throw e;
        }
    }
}
