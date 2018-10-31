package com.goldwind.securitysample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldwind.securitysample.domain.RoleResource;
import com.goldwind.securitysample.mapper.RoleResourceMapper;

@Service
public class RoleResourceService {

	@Autowired
	private RoleResourceMapper roleResourceMapper;
	
	public List<RoleResource> listRoleResourceByRoleId(Integer roleId) {
        return roleResourceMapper.listResourceIdByRoleId(roleId);
    }
	
	public List<RoleResource> listResourceIdByResourceId(Integer ResourceId) {
        return roleResourceMapper.listRoleIdByResourceId(ResourceId);
    }
}
