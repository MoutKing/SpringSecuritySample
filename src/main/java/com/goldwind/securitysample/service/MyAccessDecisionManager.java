package com.goldwind.securitysample.service;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

/**
 * 
 *<p>Title:	MyAccessDecisionManager</p>
 *<p>Description:	TODO-</p>
 *<p>Company:	com.goldwind</p>
 * @author	macbook37349
 * @date	4:05:57 PM,Oct 30, 2018
 */
@Service
public class MyAccessDecisionManager implements AccessDecisionManager {

	/**
	 * 
	 *<p>Title:	</p>
	 *<p>Description:TODO-decide 方法是判定是否拥有权限的决策方法</p>
	 * @param	Authentication-CustomUserService中循环添加到GrantedAuthority对象中的权限信息集合
	 * @param	Object-包含客户端发起的请求的requset信息，
	 * 			可转换为 HttpServletRequest request =((FilterInvocation) object).getHttpRequest()
	 * @param	Collection<ConfigAttribute>-MyInvocationSecurityMetadataSource类的
	 * 			getAttributes(Objectobject)这个方法返回的结果，此方法是为了判定用户请求的url是否在权限表中。
	 * 			如果在权限表中，则将URL返回给decide方法来判定用户是否拥有访问此URL的权限。如果不在权限表中则放行。
	 * @return	@see org.springframework.security.access.AccessDecisionManager#decide(org.springframework.security.core.Authentication, java.lang.Object, java.util.Collection)
	 * @author	macbook37349 
	 * @date	3:47:58 PM,Oct 31, 2018
	 */
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		//configAttributes是否为空
		if (null == configAttributes || configAttributes.size() <= 0) {
			return;
		}
		ConfigAttribute configAttribute;
		String needRole;
		for (Iterator<ConfigAttribute> iter = configAttributes.iterator(); iter.hasNext();) {
			configAttribute = iter.next();
			needRole = configAttribute.getAttribute();
			for (GrantedAuthority ga : authentication.getAuthorities()) {// authentication 为在注释1中循环添加到 GrantedAuthority
																			// 对象中的权限信息集合
				if (needRole.trim().equals(ga.getAuthority())) {
					return;
				}
			}
		}
		throw new AccessDeniedException("permission denied");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}