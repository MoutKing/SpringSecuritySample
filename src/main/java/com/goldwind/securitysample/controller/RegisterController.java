package com.goldwind.securitysample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;

import com.goldwind.securitysample.domain.ResponseData;
import com.goldwind.securitysample.domain.User;
import com.goldwind.securitysample.exception.UsernameIsExitedException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 *<p>Title:	RegisterController</p>
 *<p>Description:	TODO-注册新用户</p>
 *<p>Company:	com.goldwind</p>
 * @author	macbook37349
 * @date	2:35:24 PM,Oct 29, 2018
 */
@RestController
@RequestMapping("/register")
@Api(value = "注册管理", description = "注册管理")
public class RegisterController extends BaseController {
//	@Autowired
//	private ResponseData<?> responseData;

	/**
	 * 
	 *<p>Title:	</p>
	 *<p>Description:	TODO-</p>
	 * @param
	 * @param	
	 * @return	int
	 * @author	macbook37349 
	 * @date	2:37:15 PM,Oct 29, 2018
	 */
	@ApiOperation(value = "注册用户，即是新增一个用户",httpMethod = "POST", notes ="")
	@ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
	@PostMapping("/add")
	public ResponseData<User> signup(@RequestBody User user) {
		//查询将要注册的用户是否为已注册用户
		User newuser = userRepository.selectByName(user.getUsername());
		if (null != newuser) {
			throw new UsernameIsExitedException("用户已经存在");
		}
		//对Http请求的传入的密码进行加密处理
		String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encodePassword);
		ResponseData responseData = new ResponseData();
		int returnValue = userRepository.insert(user);
		if (returnValue == -1) {//
			responseData.setCode(10001);
			responseData.setData(null);
			responseData.setMessage("用户信息插入失败");
		} else if (returnValue == 0){
			responseData.setCode(10002);
			responseData.setData(null);
			responseData.setMessage("用户信息插入失败");
		} else {
			responseData.setCode(10003);
			responseData.setData(null);
			responseData.setMessage("用户信息插入成功，新用户已经注册");
		}
		return (ResponseData<User>) responseData;
	}

}
