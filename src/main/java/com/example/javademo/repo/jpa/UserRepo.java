package com.example.javademo.repo.jpa;

import com.example.javademo.entity.Tmp;
import com.example.javademo.entity.UserMst;
import org.springframework.data.repository.CrudRepository;

// DAO 객체
public interface UserRepo extends CrudRepository<UserMst, Integer> {
    
}
