package com.example.javademo.fwk.filter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class ControllerAdvice {

    @Before("PointCutList.oneController()")
    public void before() {
        System.out.println("allController() before");
    }

    @After("PointCutList.oneController()")
    public void after() {
        System.out.println("allController() after");
    }

    @Around("PointCutList.oneController()")
    public Object around(ProceedingJoinPoint pjp) {
        System.out.println("allController() around start");

        Object result = null;

        // 요청 사이드의 http 패킷을 Java EE 규격에 맞게 포맷팅 해주는 것
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // pjp를 통해 메소드명, 파라미터 값 등을 알 수 있음
        pjp.getSignature();
        String signatureName = pjp.getSignature().getDeclaringType().getSimpleName() + "." + pjp.getSignature().getName();

        System.out.println("Controller Start: " + signatureName + " By " + req.getRemoteAddr());

        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        System.out.println("allController() aorund end");
        return result;
    }
}
