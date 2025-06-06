package com.example.productmgmt.mapper;

import com.example.productmgmt.entity.Product;
import com.example.productmgmt.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    User findByUsernameWithRoles(String username);

    @Insert("""
        INSERT INTO users (username, email, password, status, created_at, updated_at)
        VALUES (#{username}, #{email}, #{password}, 'ACTIVE', NOW(), NOW())
    """)
    void insertUser(User user);
}
