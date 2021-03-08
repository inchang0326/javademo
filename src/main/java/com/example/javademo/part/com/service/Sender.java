package com.example.javademo.part.com.service;

// Ibi
public class Sender {
    private String msg = "";

    public Sender() {

    }

    public Sender(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void send() throws Exception {
        throw new Exception();
    }
}
