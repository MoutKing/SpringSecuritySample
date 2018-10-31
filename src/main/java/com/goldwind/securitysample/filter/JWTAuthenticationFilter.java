package com.goldwind.securitysample.filter;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.goldwind.securitysample.service.CustomUserDetailsService;
import com.goldwind.securitysample.util.JwtTokenUtil;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义JWT认证过滤器
 * 用户一旦登录成功就会在发出Http请求的时候带着token
 * 服务端会验证token的合法性
 * 该类继承自BasicAuthenticationFilter，
 * 在doFilterInternal方法中，从http头的Authorization项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 * @author lisong on 2018/10/25.
 */
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

//    private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
	private JwtTokenUtil jwtTokenUtil;
	
	private CustomUserDetailsService userDetailsService;
    
    public JWTAuthenticationFilter(CustomUserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
    	this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authHeader = request.getHeader("Authorization");
        String tokenHead = "Bearer ";
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
//        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        
//        String authToken = authHeader.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(authHeader.replace(tokenHead, ""));
        
//        String username = Jwts.parser()
//                .setSigningKey("MyJwtSecret")
//                .parseClaimsJws(authHeader.replace("Bearer ", ""))
//                .getBody()
//                .getSubject();
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(authHeader, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        
        chain.doFilter(request, response);
    }

}

