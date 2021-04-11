package com.example.javademo.util;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/*
    util 성 클래스지만, Bean으로 등록한 이유는 key 값을 properties/yml 파일에서 동적으로 가져오기 위해서다.
    그리고 해당 Bean을 static 인스턴스화 함으로써, util 성 클래스로 사용할 수 있게 해준다.
 */
@Component
public class EncryptUtil {

    @Autowired ApplicationContext ctx;
    @Value("${app.secret}") String key;
    static EncryptUtil instance;
    PooledPBEStringEncryptor encryptor;

    @PostConstruct // spring이 해당 클래스 bean이 등록 및 생성되면 초기화해주는 메소드를 명시한다.
    void init() {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(key);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(config);

        instance = this;
        this.encryptor = encryptor;
    }

    public static String encrypt(String input) {
        EncryptUtil serviceEncrypt = instance.ctx.getBean(EncryptUtil.class);
        return serviceEncrypt.encryptor.encrypt(input);
    }

    public static String decrypt(String input) {
        EncryptUtil serviceEncrypt = instance.ctx.getBean(EncryptUtil.class);
        return serviceEncrypt.encryptor.decrypt(input);
    }
}