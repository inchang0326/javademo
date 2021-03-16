package com.example.javademo.part.com.controller;

import com.example.javademo.fwk.base.BaseController;
import com.example.javademo.fwk.config.CloudConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class ConfigController extends BaseController {

    @Autowired
    CloudConfig cloudConfig;

    @GetMapping
    public String getProfile() {
        return cloudConfig.getProfile();
    }
}
