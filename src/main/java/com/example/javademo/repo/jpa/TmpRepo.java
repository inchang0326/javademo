package com.example.javademo.repo.jpa;

import com.example.javademo.entity.Tmp;
import org.springframework.data.repository.CrudRepository;

// DAO 객체
public interface TmpRepo extends CrudRepository<Tmp, Integer> {

}
