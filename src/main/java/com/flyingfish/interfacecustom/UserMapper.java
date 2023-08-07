package com.flyingfish.interfacecustom;

import com.flyingfish.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@MapperScan("com.flyingfish.interfacecustom")
@Repository
public interface UserMapper {

    List<User> queryUserList();

    User queryUserById(String id);

    int addUser(User user);

    int updateUser(User user);

    int deleteUser(String id);
}
