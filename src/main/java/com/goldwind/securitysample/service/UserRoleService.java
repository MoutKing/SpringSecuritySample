package com.goldwind.securitysample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldwind.securitysample.domain.UserRole;
import com.goldwind.securitysample.mapper.UserRoleMapper;

@Service
public class UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;

    public List<UserRole> listByUserId(Integer userId) {
        return userRoleMapper.listByUserId(userId);
    }
}
