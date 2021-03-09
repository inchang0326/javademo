package com.example.javademo.entity;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDate;

// FwkTransactionHst의 Primary Key 객체
@Data // lombok : getter() / setter() 자동생성
public class FwkTransactionHstId implements Serializable {
    @Column LocalDate transactionDate;
    @Column(length = 50) String appName;
    @Column(length = 20) String appVersion;
    @Column(length = 36) String gid;
}