package com.example.javademo.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;

// DTO(Data Transfer Object) 객체 - Layer 간 데이터를 전달하기 위한 객체라는 의미를 갖는다.
@Entity
@Table(name = "fwk_transaction_hst")
@IdClass(FwkTransactionHstId.class) // Primary Key 집합 설정
@Data // lombok : getter() / setter() 자동생성
public class FwkTransactionHst {
    // Primary Key
    @Id LocalDate transactionDate;
    @Id String appName;
    @Id String appVersion;
    @Id String gid;

    String method;
    String path;
    String startTime;
    String endTime;
    String elapsed;
    String apiId;
    String referrer; // 현재 오픈되어 있는 URL로, 브라우저 XHR(API 송수신 명세?)에서 확인할 수 있다.
    String hostName;
    String remoteIp;
    String statusCode;
    String queryString;
    String body;
    String mobYn;
    String errorMessage;
    String createUserId;
    OffsetDateTime createDt;
    String updateUserId;
    OffsetDateTime updateDt;
}