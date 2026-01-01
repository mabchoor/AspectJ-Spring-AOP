package com.mabchour.spring_aop.demo;

import com.mabchour.spring_aop.aspects.HandleExceptions;
import com.mabchour.spring_aop.service.IMetier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DemoScenarios {

    @HandleExceptions(title = "Scenario: Not authenticated")
    public void notAuthenticated(IMetier metier) {
        SecurityContextHolder.clearContext();
        metier.process();
    }

    @HandleExceptions(title = "Scenario: Authenticated USER")
    public void authenticatedUser(IMetier metier) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        "root",
                        "1234",
                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
                )
        );
        metier.process();
        int result = metier.compute();
        System.out.println(ConsoleColors.green("Result=" + result));
        metier.adminTask();
    }

    @HandleExceptions(title = "Scenario: Authenticated ADMIN")
    public void authenticatedAdmin(IMetier metier) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        "admin",
                        "admin",
                        List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
                )
        );
        metier.adminTask();
    }
}

