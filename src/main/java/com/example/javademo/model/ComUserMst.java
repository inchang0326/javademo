package com.example.javademo.model;

import lombok.Data;

import javax.persistence.*;

@Data
public class ComUserMst {
    Integer userId;
    String userName;
}