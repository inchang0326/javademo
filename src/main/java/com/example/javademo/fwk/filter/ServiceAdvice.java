package com.example.javademo.fwk.filter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceAdvice {

    @Around("PointCutList.oneService()")
    public Object around(ProceedingJoinPoint pjp) {
        System.out.println("allService() around start");
        Object result = null;

        // pjp를 통해 메소드명, 파라미터 값 등을 알 수 있음
        pjp.getSignature();
        String signatureName = pjp.getSignature().getDeclaringType().getSimpleName() + "." + pjp.getSignature().getName();
        String args = "";

        for(Object arg : pjp.getArgs()) {
            args += arg.toString() + ".";
        }

        System.out.println("Service Start: " + signatureName + "() with " + args);

        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        System.out.println("allService() around end");
        return result;
    }
}
