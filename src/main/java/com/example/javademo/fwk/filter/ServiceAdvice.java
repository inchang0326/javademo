package com.example.javademo.fwk.filter;

import ch.qos.logback.classic.Logger;
import com.example.javademo.util.EncryptUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceAdvice {

    private static final String logServiceFileName = "logServiceFileName";
    protected final Logger logAdvice = (Logger) LoggerFactory.getLogger(ServiceAdvice.class);

    @Around("PointCutList.oneService()")
    public Object around(ProceedingJoinPoint pjp) {
        logAdvice.info("allService() around start");
        Object result = null;

        Logger logService = (Logger) LoggerFactory.getLogger(pjp.getThis().getClass());
        String className = pjp.getSignature().getDeclaringType().getSimpleName();

        String bf = MDC.get(logServiceFileName);
        MDC.put(logServiceFileName, className);

        // pjp를 통해 메소드명, 파라미터 값 등을 알 수 있음
        String signatureName = pjp.getSignature().getDeclaringType().getSimpleName() + "." + pjp.getSignature().getName();
        String args = "";

        for(Object arg : pjp.getArgs()) {
            args += arg.toString() + ".";
        }

        logService.debug("Service Start: " + signatureName + "() with " + args);

        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            MDC.put(logServiceFileName, bf);
        }

        logAdvice.info("allService() around end");
        return result;
    }
}
