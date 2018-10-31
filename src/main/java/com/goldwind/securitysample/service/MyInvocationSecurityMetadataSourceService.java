package com.goldwind.securitysample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import com.goldwind.securitysample.domain.Resource;
import com.goldwind.securitysample.domain.Role;
import com.goldwind.securitysample.domain.RoleResource;
import com.goldwind.securitysample.mapper.ResourceMapper;
import com.goldwind.securitysample.mapper.RoleMapper;
import com.goldwind.securitysample.mapper.RoleResourceMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 
 *<p>Title:	MyInvocationSecurityMetadataSourceService</p>
 *<p>Description:	TODO-</p>
 *<p>Company:	com.goldwind</p>
 * @author	macbook37349
 * @date	4:34:14 PM,Oct 30, 2018
 */
@Service
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

	@Autowired
	private ResourceMapper resourceMapper;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private RoleResourceMapper roleResourceMapper;
	
	private static final int METHOD_INDEX = 0;
	
	private static final int URL_INDEX = 1;

	private HashMap<String, Collection<ConfigAttribute>> resourceMap = null;

	/**
	 * 加载权限表中所有权限
	 */
	public void loadResourceDefine() {
		if (resourceMap == null) {
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			//从数据库中查找所有的资源
			List<Resource> resources = resourceMapper.selectAll();
			for (Resource resource : resources) {
				//查找当前资源对应的角色列表
				int resource_id = resource.getId();
				List<RoleResource> roleResourceList = roleResourceMapper.listRoleIdByResourceId(resource_id);
	            Collection<ConfigAttribute> auths = new ArrayList<ConfigAttribute>();
				for (int i=0;i<roleResourceList.size();i++) {
					//查询返回Role的实例
					Role role =roleMapper.selectByPrimaryKey(roleResourceList.get(i).getRoleId());
					//Role的实例中有角色名称
	                ConfigAttribute auth = new SecurityConfig(role.getName());
	                auths.add(auth);
				}
                System.out.println("权限=" + auths+" 资源:"+resource.getResourceName());
                resourceMap.put(resource.getMethod()+","+resource.getResourceUrl(), auths);
			}
		}
	}

	//此方法是为了判定用户请求的url 是否在权限表中，
	//如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		if (resourceMap == null)
			loadResourceDefine();
		// object 中包含用户请求的request 信息
		HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
		AntPathRequestMatcher matcher;
		String requestInfo;
		for (Iterator<String> iter = resourceMap.keySet().iterator(); iter.hasNext();) {
			requestInfo = iter.next();
			String method = requestInfo.split(",")[METHOD_INDEX];
			String url = requestInfo.split(",")[URL_INDEX];
			matcher = new AntPathRequestMatcher(url);
			//若请求方式和请求地址与数据库中的匹配成功
			if (matcher.matches(request)&&method.equals(request.getMethod())) {
				return resourceMap.get(requestInfo);
			}
		}
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}