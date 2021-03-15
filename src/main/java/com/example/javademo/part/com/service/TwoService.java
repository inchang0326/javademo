package com.example.javademo.part.com.service;

import com.example.javademo.fwk.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class TwoService extends BaseService {

    public String two() {
        return "two";
    }
}
