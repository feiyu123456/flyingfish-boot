package com.flyingfish.service.impl;

import com.flyingfish.interfacecustom.UserMapper;
import com.flyingfish.pojo.User;
import com.flyingfish.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final UserMapper userMapper;

    @Override
    public User queryUserById(String id) {
        User user = userMapper.queryUserById(id);
        System.out.println("==========================>" + user.toString());
        return user;
    }
}
