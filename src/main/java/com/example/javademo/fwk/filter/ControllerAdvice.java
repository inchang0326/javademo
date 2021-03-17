package com.example.javademo.fwk.filter;

import com.example.javademo.entity.FwkTransactionHst;
import com.example.javademo.fwk.base.BaseController;
import com.example.javademo.fwk.component.TransactionService;
import com.example.javademo.fwk.pojo.CommonArea;
import com.example.javademo.repo.jpa.TransactionRepo;
import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.concurrent.CompletableFuture;

@Aspect
@Component
@Log // lombok : protected final Logger logAdvice = (Logger) LoggerFactory.getLogger(ControllerAdvice.class)
public class ControllerAdvice {

/*
    @Before("PointCutList.oneController()")
    public void before() {
        log.info("allController() before");
    }

    @After("PointCutList.oneController()")
    public void after() {
        log.info("allController() after");
    }
*/

    @Autowired
    ApplicationContext ctx;

    @Autowired
    TransactionService ts;

    static boolean bSetStatic = false;
    static String appName = "";
    static String appVersion = "";
    static String hostName = "";
    static boolean bLocal = false;
    static boolean bDev = false;
    static boolean bPrd = false;

    @Autowired
    TransactionRepo transactionRepo;

    @Around("PointCutList.oneController()")
    public Object around(ProceedingJoinPoint pjp) {
        log.info("allController() around start");
        Object result = null;

        // 요청 사이드의 http 패킷을 Java EE 규격에 맞게 포맷팅 해주는 것
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // pjp를 통해 메소드명, 파라미터 값 등을 알 수 있음
        String signatureName = pjp.getSignature().getDeclaringType().getSimpleName() + "." + pjp.getSignature().getName();
        log.info("Controller Start: " + signatureName + " By " + req.getRemoteAddr());

        /* FwkTransactionHst init start */
        CommonArea ca = new CommonArea();
        RequestContextHolder.getRequestAttributes().setAttribute("ca", ca, RequestAttributes.SCOPE_REQUEST);
        // setStatic
        if(!bSetStatic) {
            setStaticVariable();
        }
        // setCommonArea
        setCommonArea(req, ca);
        /* FwkTransactionHst init end */

        CompletableFuture<FwkTransactionHst> futureTr = ts.saveTr(ca);
        try {
            Object bc = pjp.getThis();
            if(bc instanceof BaseController) {
                BaseController base = (BaseController) bc;
                base.setCa(); // Request 범위에 있는 ca를 BaseController 멤버 ca에 등록
            }

            result = pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
            ca.setErrorMessage(e.getMessage());
            ca.setStatusCode("500");
        } finally {
            ca.setEndTime(OffsetDateTime.now(ZoneId.of("+9")));
            ts.updateTr(ca, futureTr);
        }

        log.info("allController() around end");
        return result;
    }

    private void setStaticVariable() {
        appName = "javademo";
        appVersion = "0.1";
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.info("err occurred when get hostname" + e.getMessage());
            hostName = "unknown host";
        }

        Environment environment = (Environment) ctx.getBean("environment");
        for(String profile : environment.getActiveProfiles()) {
            if( "local".equals(profile) ){
                bLocal = true;
            } else if( "dev".equals(profile) ) {
                bDev = true;
            } else if( "prd".equals(profile) ) {
                bPrd = true;
            }
        }

        bSetStatic = true;
    }

    private void setCommonArea(HttpServletRequest req, CommonArea ca) {
        // init
        OffsetDateTime now = OffsetDateTime.now(ZoneId.of("+9"));

        // main
        ca.setTransactionDate(LocalDate.now());
        ca.setAppName(appName);
        ca.setAppVersion(appVersion);
        ca.setGid(MDC.get("GUID"));
        ca.setMethod(req.getMethod());
        ca.setPath(req.getRequestURI());
        ca.setStartTime(now);
        ca.setRemoteIp(req.getRemoteAddr());
        ca.setHostName(hostName);
        ca.setCreateDt(now);
        ca.setMobYn("N");
        ca.setBLocal(bLocal);
        ca.setBDev(bDev);
        ca.setBPrd(bPrd);

        try {
            if(req.getHeader("referer") != null) {
                URI referer = new URI(req.getHeader("referer"));
                ca.setReferrer(referer.getPath());
            }
        } catch (URISyntaxException e) {
            ca.setReferrer("parse URI exception occurred..");
        }
    }
}
