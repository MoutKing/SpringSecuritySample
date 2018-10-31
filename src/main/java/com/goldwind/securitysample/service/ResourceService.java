package com.goldwind.securitysample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldwind.securitysample.domain.Resource;
import com.goldwind.securitysample.mapper.ResourceMapper;

@Service
public class ResourceService {

	@Autowired
	private ResourceMapper resourceMapper;
	
	/**
	 * 根据ID返Resource对象
	 * @param id
	 * @return Resource instance
	 */
	public Resource selectById(Integer id) {
		return resourceMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 根据resource_url返回Resource对象
	 * @param resource
	 * @return Resource instance
	 */
	public Resource selectByResource(String resource) {
		return resourceMapper.selectByResourceContent(resource);
	}
}
