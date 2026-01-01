package com.mabchour.spring_aop.aspects;

import com.mabchour.spring_aop.demo.ConsoleColors;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DemoExceptionHandlingAspect {
    private static final Logger log = LoggerFactory.getLogger(DemoExceptionHandlingAspect.class);

    @Around("@annotation(handleExceptions)")
    public Object handle(ProceedingJoinPoint joinPoint, HandleExceptions handleExceptions) throws Throwable {
        String title = handleExceptions.title();
        if (title != null && !title.isBlank()) {
            System.out.println(ConsoleColors.cyan("=== " + title + " ==="));
        }

        try {
            return joinPoint.proceed();
        } catch (RuntimeException ex) {
            log.error("Handled exception in {}: {}", joinPoint.getSignature().toShortString(), ex.getMessage());
            System.out.println(ConsoleColors.red(ex.getMessage()));
            return null;
        }
    }
}

