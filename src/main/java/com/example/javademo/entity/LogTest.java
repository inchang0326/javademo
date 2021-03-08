package com.example.javademo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "logTest")
@Data
public class LogTest {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Integer seq;
    String servName;
    String errCode;
    String errMsg;
    String dt;
    String tm;

    public LogTest() {}

    public LogTest(String servName, String errCode, String errMsg, String dt, String tm){
        this.servName = servName;
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.dt = dt;
        this.tm = tm;
    }
}
