package com.example.javademo.fwk.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@Data
public class CloudConfig {
    @Value("${server.message}")
    private String message;
}
