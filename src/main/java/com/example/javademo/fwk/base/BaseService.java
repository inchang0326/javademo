package com.example.javademo.fwk.base;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

public class BaseService {
    protected final Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}
