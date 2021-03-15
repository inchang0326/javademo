package com.example.javademo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication // Bean 생성 및 자동 등록 등등
@ServletComponentScan // Filter 클래스 사용을 위해 선언해야 함
@EnableAsync // @Async 메소드 사용을 위해 선언해야 함
public class JavademoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavademoApplication.class, args);
    }
}
