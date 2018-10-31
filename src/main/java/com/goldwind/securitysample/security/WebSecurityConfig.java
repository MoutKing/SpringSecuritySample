package com.goldwind.securitysample.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.goldwind.securitysample.filter.JWTAuthenticationFilter;
import com.goldwind.securitysample.filter.JWTLoginFilter;
import com.goldwind.securitysample.handler.EntryPointUnauthorizedHandler;
import com.goldwind.securitysample.handler.RestAccessDeniedHandler;
import com.goldwind.securitysample.interceptor.MyFilterSecurityInterceptor;
import com.goldwind.securitysample.service.CustomUserDetailsService;

/**
 * SpringSecurity的配置
 * 通过SpringSecurity的配置，将JWTLoginFilter，JWTAuthenticationFilter组合在一起
 * 
 * @author lisong on 2018/10/13.
 */
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	/**
	 * 需要放行的URL
	 */
	private static final String[] AUTH_WHITELIST = {
			// -- register url
			// -- swagger ui
			"/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
			"/configuration/security", "/swagger-ui.html", "/webjars/**"
			// other public endpoints of your API may be appended to this array
	};

	private CustomUserDetailsService userDetailsService;

	@Autowired
	private JWTAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
	
    private EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;

    private RestAccessDeniedHandler restAccessDeniedHandler;

	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public WebSecurityConfig(CustomUserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder,
			JWTAuthenticationFilter jwtAuthenticationFilter, EntryPointUnauthorizedHandler entryPointUnauthorizedHandler,
			RestAccessDeniedHandler restAccessDeniedHandler) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
	
	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder
				// 设置UserDetailsService
				.userDetailsService(userDetailsService)
				// 使用BCrypt进行密码的hash
				.passwordEncoder(bCryptPasswordEncoder);
	}

	/**
	 * 设置 HTTP 验证规则
	 * @author macbook37349
	 */
	@Override
    protected void configure(HttpSecurity http) throws Exception {
    	//设置过滤器
        http
        	.cors().and().csrf().disable()
        	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        	.authorizeRequests()
        	.antMatchers(AUTH_WHITELIST).permitAll()//放行无需验证的url
//        	.antMatchers(HttpMethod.POST, "/register/add").hasRole("ADMIN")
//        	.antMatchers(HttpMethod.PUT, "/user/*").hasAnyRole("ADMIN","USER")
//        	.antMatchers(HttpMethod.GET, "/user/*").hasAnyRole("ADMIN","USER")
//        	.antMatchers(HttpMethod.DELETE, "/user/*").hasRole("ADMIN")
            .anyRequest().authenticated()
        	.and().headers().cacheControl();
        http.addFilter(new JWTLoginFilter(authenticationManager()))
        .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
        http.exceptionHandling().authenticationEntryPoint(entryPointUnauthorizedHandler).accessDeniedHandler(restAccessDeniedHandler);
//      .addFilter(new JWTLoginFilter(authenticationManager()));
//      .addFilter(new JWTAuthenticationFilter(authenticationManager()));
        
    }

//	 该方法是登录的时候会进入
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 使用自定义身份验证组件
		auth.userDetailsService(this.userDetailsService);
	}

}
