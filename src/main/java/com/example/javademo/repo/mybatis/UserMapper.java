package com.example.javademo.repo.mybatis;

import com.example.javademo.model.ComUserMst;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    ComUserMst selectUserOne(@Param("userId") int userId);
}
