package com.example.javademo.fwk.pojo;

import lombok.Data;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
public class CommonArea {
    LocalDate transactionDate;
    String appName;
    String appVersion;
    String gid;
    String method;
    String path;
    OffsetDateTime startTime;
    OffsetDateTime endTime;
    String elapsed;
    String apiId;
    String referrer;
    String hostName;
    String remoteIp;
    String statusCode;
    String queryString;
    String body;
    String mobYn = "N";
    String errorMessage;
    String createUserId;
    OffsetDateTime createDt;
    String updateUserId;
    OffsetDateTime updateDt;
    boolean bLocal;
    boolean bDev;
    boolean bPrd;
}
