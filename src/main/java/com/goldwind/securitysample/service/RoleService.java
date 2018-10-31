package com.goldwind.securitysample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldwind.securitysample.domain.Role;
import com.goldwind.securitysample.mapper.RoleMapper;

@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;

    public Role selectById(Integer id){
        return roleMapper.selectByPrimaryKey(id);
    }
}
