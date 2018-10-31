package com.goldwind.securitysample.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.goldwind.securitysample.domain.UserRole;

@Mapper
public interface UserRoleMapper {
	List<UserRole> listByUserId(Integer userId);
}
