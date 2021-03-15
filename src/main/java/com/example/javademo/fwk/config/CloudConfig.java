package com.example.javademo.fwk.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class CloudConfig {
    @Value("${server.active.profile}")
    private String profile;
}
