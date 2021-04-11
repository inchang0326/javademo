package com.example.javademo.fwk.filter;

import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.UUID;

@WebFilter(urlPatterns= "/*") // Filter를 설정하는 Java Config 방법. 단 @Component와 같이 사용해선 안됨
public class LoggingFilter implements Filter {

        private final String defaultPropertiesPath = "bootstrap.properties";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        String guid = UUID.randomUUID().toString();

        MDC.put("GUID", guid);

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.putProperty("GUID", MDC.get("GUID"));

        chain.doFilter(req, response);
    }


}
