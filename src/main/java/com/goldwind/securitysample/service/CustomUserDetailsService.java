package com.goldwind.securitysample.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.goldwind.securitysample.domain.Role;
import com.goldwind.securitysample.domain.User;
import com.goldwind.securitysample.domain.UserRole;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *<p>Title:	CustomUserDetailsService</p>
 *<p>Description: TODO-用户登录时候将用户传入的参数与数据库中的信息比对验证</p>
 *<p>Company:	com.goldwind</p>
 * @author	macbook37349
 * @date	11:20:42 AM,Oct 30, 2018
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userroleService;
    
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("权限框架-加载用户");
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        User user = new User();
        user.setUsername(username);
        user = userService.selectByName(user.getUsername());

        if (user == null) {
            logger.debug("找不到该用户 用户名:{}", username);
            throw new UsernameNotFoundException("找不到该用户！");
        }
//        if(baseUser.getStatus()==2)
//        {
//            logger.debug("用户被禁用，无法登陆 用户名:{}", username);
//            throw new UsernameNotFoundException("用户被禁用！");
//        }
        List<UserRole> userRoles = userroleService.listByUserId(user.getId());
        if (userRoles != null) {
            //设置角色名称
            for (UserRole userRole : userRoles) {
            	Role role = roleService.selectById(userRole.getRoleId());
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
                grantedAuthorityList.add(authority);
            }
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, true, true, grantedAuthorityList);
    }
}