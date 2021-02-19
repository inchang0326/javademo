package com.example.javademo.part.com.controller;

import ch.qos.logback.classic.Logger;
import com.example.javademo.part.com.service.OneService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/one")
public class OneController {

    // 기본적으로 com.example.javademo.part.com.controller 패키지 내 controller 로깅
    private static Logger log = (Logger) LoggerFactory.getLogger(OneController.class);

    @Autowired
    OneService oneService;

    @GetMapping
    public String one(@RequestParam(value = "bkNm", required = false, defaultValue="") String reqBkNm) {
        log.info("test info logger");
        log.debug("test debug logger");

        return oneService.one(reqBkNm);
    }
}
// plugins >> restfultool 플러그인
// restfultool
// ctrl + alt + /       >> uri 바로찾기
// option + command + / >> uri 바로찾기