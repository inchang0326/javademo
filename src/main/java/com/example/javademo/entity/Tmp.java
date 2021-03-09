package com.example.javademo.entity;

import lombok.Data;

import javax.persistence.*;

// DTO(Data Transfer Object) 객체 - Layer 간 데이터를 전달하기 위한 객체라는 의미를 갖는다.
@Entity
@Table(name = "tmp")
@Data // lombok : getter() / setter() 자동생성
public class Tmp {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Integer seq;
    String name;
}