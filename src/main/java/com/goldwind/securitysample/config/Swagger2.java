package com.goldwind.securitysample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 *<p>Title:	Swagger2</p>
 *<p>Description:	TODO-</p>
 *<p>Company:	com.goldwind</p>
 * @author	macbook37349
 * @date	2:51:34 PM,Oct 29, 2018
 */
@Configuration	//让Spring加载该配置类
@EnableSwagger2 //启用Swagger2
public class Swagger2 {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.goldwind.security")).paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Spring Boot中使用Swagger2构建RESTful APIs")
				.description("更多Spring Boot相关文章请关注：http://blog.didispace.com/")
				.termsOfServiceUrl("http://blog.didispace.com/").contact("李崧").version("1.0").build();
	}

}
