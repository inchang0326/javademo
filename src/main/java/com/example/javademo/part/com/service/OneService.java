package com.example.javademo.part.com.service;

import com.example.javademo.fwk.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class OneService extends BaseService {

    public String one(String reqBkNm) {
        return reqBkNm;
    }
}
