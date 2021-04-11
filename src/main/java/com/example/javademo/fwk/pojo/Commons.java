package com.example.javademo.fwk.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Component
@Data
public class Commons {
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
