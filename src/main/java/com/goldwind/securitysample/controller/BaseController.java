package com.goldwind.securitysample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.goldwind.securitysample.mapper.UserMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 描述：
 *
 * @author: lisong
 * @date: 2018/10/25 18:35
 */
public abstract class BaseController {

    protected Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    protected UserMapper userRepository;

    @Autowired
    protected BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 获取用户所拥有的权限列表
     * @return
     */
    public List<String> getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> list = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : authorities) {
            logger.info("权限列表：{}", grantedAuthority.getAuthority());
            list.add(grantedAuthority.getAuthority());
        }
        return list;
    }
}
