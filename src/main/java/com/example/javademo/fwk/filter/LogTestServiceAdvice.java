package com.example.javademo.fwk.filter;

import ch.qos.logback.classic.Logger;
import com.example.javademo.entity.LogTest;
import com.example.javademo.repo.jpa.LogTestRepo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Aspect
@Component
public class LogTestServiceAdvice {

    @Autowired
    LogTestRepo logTestRepo;

    protected final Logger logAdvice = (Logger) LoggerFactory.getLogger(LogTestServiceAdvice.class);

    @Around("PointCutList.oneControllerForLogTest()")
    public Object logTestServiceAround(ProceedingJoinPoint pjp) {
        logAdvice.info("logTestServiceAround() around start");
        Object result = null;

        SimpleDateFormat sdf = new SimpleDateFormat ( "yyyyMMddHHmmss");
        String servName = pjp.getSignature().getDeclaringType().getSimpleName();

        Date date = new Date();
        String fdt = sdf.format(date);
        String dt = fdt.substring(0, 8);
        String tm = fdt.substring(8, 14);

        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            HashMap<String, String> errMap = (HashMap<String, String>) request.getAttribute("errMap");
            String errCode = errMap.get("errCode");
            String errMsg = errMap.get("errMsg");

            LogTest logTest = new LogTest(servName, errCode, errMsg, dt, tm);
            logTestRepo.save(logTest);
        }

        logAdvice.info("logTestServiceAround() around end");
        return result;
    }
}
