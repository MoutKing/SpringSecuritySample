package com.goldwind.securitysample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PostAuthorize;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.goldwind.securitysample.domain.User;
import com.goldwind.securitysample.mapper.UserMapper;

import io.swagger.annotations.ApiOperation;

/**
 * 在 @PreAuthorize 中我们可以利用内建的 SPEL 表达式：比如 'hasRole()' 来决定哪些用户有权访问。
 * 需注意的一点是 hasRole 表达式认为每个角色名字前都有一个前缀 'ROLE_'。所以这里的 'ADMIN' 其实在
 * 数据库中存储的是 'ROLE_ADMIN' 。这个 @PreAuthorize 可以修饰Controller也可修饰Controller中的方法。
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper repository;

//    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "查询所有用户信息")
    @RequestMapping(method = RequestMethod.GET)
    public List<User> getUsers() {
        return repository.selectAll();
    }

//    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "新增用户")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    int addUser(@RequestBody User addedUser) {
        return repository.insert(addedUser);
    }

//    @PostAuthorize("returnObject.username == principal.username or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "根据userid查询特定用户信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable Integer id) {
        return repository.selectByPrimaryKey(id);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "根据userid更改特定用户信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    int updateUser(@PathVariable Integer id, @RequestBody User updatedUser) {
        updatedUser.setId(id);
        return repository.updateByPrimaryKey(updatedUser);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "根据userid删除特定用户")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    int removeUser(@PathVariable Integer id) {
    	return repository.deleteByPrimaryKey(id);
    }

//    @PostAuthorize("returnObject.username == principal.username or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public User getUserByUsername(@RequestParam(value="username") String username) {
        return repository.selectByName(username);
    }
    
}
