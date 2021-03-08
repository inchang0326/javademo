package com.example.javademo.exception;

public class ServiceException extends Exception {
    private String rtCd;
    private String rtMsg;

    public ServiceException() {}

    public ServiceException(String rtCd, String rtMsg) {
        this.rtCd = rtCd;
        this.rtMsg = rtMsg;
    }

    public String getRtCd() {
        return rtCd;
    }
    public String getRtMsg() {
        return rtMsg;
    }
}
