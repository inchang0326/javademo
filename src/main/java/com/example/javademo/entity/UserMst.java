package com.example.javademo.entity;

import lombok.Data;

import javax.persistence.*;

// DTO(Data Transfer Object) 객체 - Layer 간 데이터를 전달하기 위한 객체라는 의미를 갖는다.
@Entity
@Table(name = "com_user_mst")
@Data
public class UserMst {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer userId;
    String name;
    String sex;
    Integer age;

    /*
    save() 전 select 수행을 막고자 한다면,
    1. Persistable<T>를 구현하고 메소드를 오버라이딩한다.
       단, 추후 insert / update 구분을 위한 로직 처리가 요구된다.

       private boolean isInsert = true;

       @Override
       public Integer getId() {
           return userId;
       }
       @Override
       public boolean isNew() {
           return isInsert;
       }

    2. ~
     */
}