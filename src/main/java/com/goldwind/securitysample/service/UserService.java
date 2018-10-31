package com.goldwind.securitysample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldwind.securitysample.domain.User;
import com.goldwind.securitysample.mapper.UserMapper;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User selectById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public User selectByName(String name) {
        return userMapper.selectByName(name);
    }
}