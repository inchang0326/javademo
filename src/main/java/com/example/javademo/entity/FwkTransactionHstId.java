package com.example.javademo.entity;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDate;

// FwkTransactionHst의 Primary Key 객체
public class FwkTransactionHstId implements Serializable {
    @Column LocalDate transactionDate;
    @Column(length = 50) String appName;
    @Column(length = 20) String appVersion;
    @Column(length = 36) String gid;

    public void setTransactionDate(LocalDate transactionDate) { this.transactionDate = transactionDate; }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }
}