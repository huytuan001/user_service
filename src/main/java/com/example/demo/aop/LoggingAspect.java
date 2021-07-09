package com.example.demo.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.example.demo.controller.*.*.*(..))")
    public void beforeMethodExecution(JoinPoint point) {
        log.info(" before called " + point.toString());
    }

    @AfterReturning(pointcut = "execution(* com.example.demo.controller.*.*(..))", returning = "retVal")
    public void afterReturningMethodExecution(JoinPoint point, Object retVal) throws JsonProcessingException {
        String returnVal = mapper.writeValueAsString(retVal);
        log.info("Finish {}.{}() and return : {}", point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), returnVal);
    }

    @AfterThrowing(pointcut = "execution(* com.example.demo.controller.*.*(..))", throwing = "e")
    public void afterThrowingMethodExecution(JoinPoint point, Throwable e) {
        log.error("Exception in {}.{}() with cause = '{}' and exception = '{}'", point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL", e.getMessage(), e);
    }
}
