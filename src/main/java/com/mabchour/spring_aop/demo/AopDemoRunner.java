package com.mabchour.spring_aop.demo;

import com.mabchour.spring_aop.service.IMetier;
import com.mabchour.spring_aop.service.PlainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AopDemoRunner implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(AopDemoRunner.class);

    private final IMetier metier;
    private final PlainService plainService;
    private final DemoScenarios demoScenarios;

    public AopDemoRunner(IMetier metier, PlainService plainService, DemoScenarios demoScenarios) {
        this.metier = metier;
        this.plainService = plainService;
        this.demoScenarios = demoScenarios;
    }

    @Override
    public void run(String... args) {
        log.info("Plain bean class  : {}", plainService.getClass().getName());
        log.info("Metier bean class : {}", metier.getClass().getName());

        demoScenarios.notAuthenticated(metier);
        demoScenarios.authenticatedUser(metier);
        demoScenarios.authenticatedAdmin(metier);
    }
}

