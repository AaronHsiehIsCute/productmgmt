package com.example.productmgmt.mapper;

import com.example.productmgmt.entity.Product;
import com.example.productmgmt.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

public interface UserMapper {
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    User findByUsernameWithRoles(String username);

    @Insert("""
        INSERT INTO users (username, email, password, status, created_at, updated_at)
        VALUES (#{username}, #{email}, #{password}, 'ACTIVE', NOW(), NOW())
    """)
    void insertUser(User user);

    @Select("SELECT * FROM users WHERE email = #{email}")
    Optional<User> findByEmail(@Param("email") String email);

    @Insert("INSERT INTO users (username, email, password, status, created_at, updated_at) " +
            "VALUES (#{username}, #{email}, #{password}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

}
