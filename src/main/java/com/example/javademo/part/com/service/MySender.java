package com.example.javademo.part.com.service;

import com.example.javademo.exception.ServiceException;
import org.springframework.stereotype.Service;

// Mock Obj
@Service
public class MySender {
    private Sender sender;
    private boolean isSent = false;

    public MySender() {

    }

    public MySender(Sender sender) {
        this.sender = sender;
    }

    public void send() throws ServiceException {
        try {
            this.sender.send();
            isSent = true;
        } catch(Exception e) {
            throw new ServiceException("000000", "예외발생!");
        }
    }

    public boolean isSent() {
        return isSent;
    }
}
