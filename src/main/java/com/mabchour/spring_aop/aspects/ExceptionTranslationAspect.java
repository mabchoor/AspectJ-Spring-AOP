package com.mabchour.spring_aop.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionTranslationAspect {
    private static final Logger log = LoggerFactory.getLogger(ExceptionTranslationAspect.class);

    @AfterThrowing(pointcut = "@annotation(com.mabchour.spring_aop.aspects.SecuredByAspect)", throwing = "ex")
    public void translate(JoinPoint joinPoint, Throwable ex) {
        if (ex instanceof AuthenticationCredentialsNotFoundException) {
            throw new RuntimeException(ex.getMessage(), ex);
        }

        if (ex instanceof AccessDeniedException) {
            String message = "Vous n'êtes pas autorisé à appeler " + joinPoint.getSignature().toShortString();
            throw new RuntimeException(message, ex);
        }

        log.debug("No translation for exception in {}: {}", joinPoint.getSignature().toShortString(), ex.getClass().getName());
    }
}
