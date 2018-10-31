package com.goldwind.securitysample.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.goldwind.securitysample.constant.ConstantKey;
import com.goldwind.securitysample.domain.User;
import com.goldwind.securitysample.mapper.UserMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
//@PreAuthorize("hasRole('ROLE_ADMIN')")
@Api(value = "登录管理", description = "登录管理")
public class LoginController {
	
	private Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserMapper userMapper;
	
    @RequestMapping("/")
    public String showHome() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("当前登陆用户：" + name);
        return "home.html";
    }

//    @RequestMapping("/login")
    @ApiOperation(value = "自定义登录")
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public void login(String username, String password, HttpServletResponse response) {
       User userVo = userMapper.selectByName(username);
        if (userVo != null) {
            /**
             * 自定义生成Token，因为使用了自定义登录，就不会执行JWTLoginFilter了，所以需要字段调用生成token并返给前端
             */
            // 这里可以根据用户信息查询对应的角色信息，这里为了简单，我直接设置个空list
            List roleList = new ArrayList<>();
            String subject = userVo.getUsername() + "-" + roleList;
                    String token = Jwts.builder()
                    .setSubject(subject)
                    .setExpiration(new Date(System.currentTimeMillis() + 365 * 24 * 60 * 60 * 1000)) // 设置过期时间 365 * 24 * 60 * 60秒(这里为了方便测试，所以设置了1年的过期时间，实际项目需要根据自己的情况修改)
                    .signWith(SignatureAlgorithm.HS512, ConstantKey.SIGNING_KEY) //采用什么算法是可以自己选择的，不一定非要采用HS512
                    .compact();
            // 登录成功后，返回token到header里面
            response.addHeader("Authorization", "Bearer " + token);
        }
    }
//    public String showLogin() {
//        return "login.html";
//    }

    @RequestMapping("/admin")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String printAdmin() {
        return "如果你看见这句话，说明你有ROLE_ADMIN角色";
    }

    @RequestMapping("/user")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    public String printUser() {
        return "如果你看见这句话，说明你有ROLE_USER角色";
    }
}
