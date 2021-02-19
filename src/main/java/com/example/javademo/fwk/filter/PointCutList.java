package com.example.javademo.fwk.filter;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PointCutList {

    @Pointcut("execution(* com.example.javademo..controller..*.*(..))")
    public void oneController() {
    }

    @Pointcut("execution(* com.example.javademo..service..*.*(..))")
    public void oneService() {
    }
}
