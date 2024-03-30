package com.flyingfish.controller;

import com.flyingfish.interfacecustom.UserMapper;
import com.flyingfish.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.applet.AppletContext;
import java.util.List;

@CacheConfig(cacheNames = {"userCache"})
@RestController
public class UserController {

    @Autowired
    @Qualifier(value = "userMapper")
    private UserMapper userMapper;

    @GetMapping("/queryUserList")
    public List<User> queryUserList() {
        List<User> userList = userMapper.queryUserList();
        return userList;
    }

    @Cacheable(value = "userCache", key = "#id", unless = "#result == null")
    @GetMapping("/queryUserById/{id}")
    public User queryUserById(@PathVariable String id){
        User user = userMapper.queryUserById(id);
        System.out.println("==========================>" + user.toString());
        return user;
    }

    @CachePut(value = "userCache", key = "#user.id")
    @PostMapping(value = "/addUser", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public User addUser(@RequestBody User user){
        userMapper.addUser(user);
        return user;
    }

    @CachePut(value = "userCache", key = "#user.id")
    @PostMapping("/updateUser")
    public User updateUser(@RequestBody User user){
        userMapper.updateUser(user);
        return user;
    }

    @CacheEvict(value = "userCache", key = "#id")
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable String id){
        int flag = userMapper.deleteUser(id);
        return flag > 0 ? "删除成功" : "删除失败";

    }

}
