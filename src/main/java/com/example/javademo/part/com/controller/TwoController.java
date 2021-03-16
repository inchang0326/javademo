package com.example.javademo.part.com.controller;

import com.example.javademo.part.com.service.TwoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/two")
public class TwoController {

    @Autowired
    private TwoService twoService;

    @GetMapping
    public String two() {
        return twoService.two();
    }
}
