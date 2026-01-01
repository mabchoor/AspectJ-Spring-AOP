package com.mabchour.spring_aop.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Around("@annotation(com.mabchour.spring_aop.aspects.Log)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startNs = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally {
            long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
            log.info("{} took {} ms", joinPoint.getSignature().toShortString(), tookMs);
        }
    }

    @AfterThrowing(pointcut = "@annotation(com.mabchour.spring_aop.aspects.Log)", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        log.warn("Exception in {}: {}", joinPoint.getSignature().toShortString(), ex.getMessage());
    }
}
