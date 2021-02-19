package com.example.javademo.fwk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration // 나는 config 이므로, 먼저 읽어줘
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class BaseConfig {
}
