package com.example.javademo.part.com.controller;

import com.example.javademo.fwk.base.BaseController;
import com.example.javademo.fwk.config.CloudConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class CloudConfigController extends BaseController {

    @Autowired
    CloudConfig cloudConfig;

    @GetMapping
    public String getMessage() {
        return cloudConfig.getMessage();
    }
}
