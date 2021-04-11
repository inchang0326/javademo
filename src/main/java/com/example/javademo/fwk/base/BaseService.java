package com.example.javademo.fwk.base;

import ch.qos.logback.classic.Logger;
import com.example.javademo.fwk.pojo.Commons;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseService {
    protected final Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected Commons ca;
}
