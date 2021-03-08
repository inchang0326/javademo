package com.example.javademo.repo.jpa;

import com.example.javademo.entity.LogTest;
import org.springframework.data.repository.CrudRepository;

// DAO 객체
public interface LogTestRepo extends CrudRepository<LogTest, Integer> {

}
