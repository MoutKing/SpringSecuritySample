package com.goldwind.securitysample.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.goldwind.securitysample.domain.RoleResource;

@Mapper
public interface RoleResourceMapper {

	/**
	 * 根据roleId查RoleResource
	 * 
	 * @param roleId
	 * @return RoleResource instance
	 */
	List<RoleResource> listResourceIdByRoleId(Integer roleId);

	/**
	 * 根据ResourceId查RoleResource
	 * 
	 * @param ResourceId
	 * @return RoleResource instance
	 */
	List<RoleResource> listRoleIdByResourceId(Integer ResourceId);

}
