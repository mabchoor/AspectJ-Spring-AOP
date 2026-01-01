package com.mabchour.spring_aop.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.AccessDeniedException;

import java.util.Arrays;

@Aspect
@Component
public class AuthorizationAspect {
    private static final Logger log = LoggerFactory.getLogger(AuthorizationAspect.class);

    @Around("@annotation(securedByAspect)")
    public Object authorize(ProceedingJoinPoint joinPoint, SecuredByAspect securedByAspect) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            throw new AuthenticationCredentialsNotFoundException(
                    "Vous devez vous authentifier avant d'appeler " + joinPoint.getSignature().toShortString()
            );
        }

        boolean hasAnyRole = Arrays.stream(securedByAspect.roles())
                .map(AuthorizationAspect::toRoleAuthority)
                .anyMatch(required -> hasAuthority(authentication, required));

        if (!hasAnyRole) {
            log.warn("Denied {} for user '{}' (required roles {})",
                    joinPoint.getSignature().toShortString(),
                    authentication.getName(),
                    Arrays.toString(securedByAspect.roles()));
            throw new AccessDeniedException("Vous n'êtes pas autorisé à appeler " + joinPoint.getSignature().toShortString());
        }

        log.info("Authorized {} for roles {}", joinPoint.getSignature().toShortString(), Arrays.toString(securedByAspect.roles()));
        return joinPoint.proceed();
    }

    private static boolean hasAuthority(Authentication authentication, String requiredAuthority) {
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (requiredAuthority.equals(authority.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    private static String toRoleAuthority(String role) {
        if (role == null || role.isBlank()) {
            return role;
        }
        return role.startsWith("ROLE_") ? role : "ROLE_" + role;
    }
}
