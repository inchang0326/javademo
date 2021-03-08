package com.example.javademo.repo.jpa;

import com.example.javademo.entity.FwkTransactionHst;
import com.example.javademo.entity.FwkTransactionHstId;
import com.example.javademo.entity.Tmp;
import org.springframework.data.repository.CrudRepository;

// DAO 객체
// CrudRepository<객체 타입, PK 타입>
public interface TransactionRepo extends CrudRepository<FwkTransactionHst, FwkTransactionHstId> {

}
