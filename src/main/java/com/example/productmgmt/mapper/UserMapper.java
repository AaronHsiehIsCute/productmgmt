package com.example.productmgmt.mapper;

import com.example.productmgmt.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findByUsername(String username);
    User findByUsernameWithRoles(String username);
}
