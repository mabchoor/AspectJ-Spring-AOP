package com.mabchour.spring_aop.service;

import com.mabchour.spring_aop.aspects.Log;
import com.mabchour.spring_aop.aspects.SecuredByAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MetierImpl implements IMetier {
    private static final Logger log = LoggerFactory.getLogger(MetierImpl.class);

    @Override
    @Log
    @SecuredByAspect(roles = {"USER", "ADMIN"})
    public void process() {
        log.info("process() business logic executed");
    }

    @Override
    @Log
    public int compute() {
        log.info("compute() business logic executed");
        return 123;
    }

    @Override
    @Log
    @SecuredByAspect(roles = {"ADMIN"})
    public void adminTask() {
        log.info("adminTask() business logic executed");
    }
}

