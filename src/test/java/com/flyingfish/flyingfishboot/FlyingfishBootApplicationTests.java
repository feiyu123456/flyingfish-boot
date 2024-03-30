package com.flyingfish.flyingfishboot;


import com.flyingfish.interfacecustom.UserMapper;
import com.flyingfish.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Stack;

@Slf4j
@MapperScan("com.flyingfish.interfacecustom")
@SpringBootTest
@RunWith(SpringRunner.class) //自动装配类才能实例化到spring容器中，自动注入才能生效
public class FlyingfishBootApplicationTests {

//	@RunWith
//	@RunWith就是一个运行器
//	@RunWith(JUnit4.class)就是指用JUnit4来运行
//	@RunWith(SpringJUnit4ClassRunner.class),让测试运行于Spring测试环境
//	@RunWith(Suite.class)的话就是一套测试集合


	@Autowired
	UserMapper userMapper;

//	@Autowired
//	private StringRedisTemplate stringRedisTemplate;

	@Qualifier("redisTemplate")
	@Autowired
	private RedisTemplate template;

	@Test
	public void contextLoads() {

		Stack s = new Stack();

//		Runnable r = () -> {
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				log.error(e.getMessage());
//			}
//		};
//		Thread thread = new Thread(r, "t1");
//		System.out.println("t1 state:" + thread.getState());
//		thread.start();
//		System.out.println("t1 state:" + thread.getState());
//
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		System.out.println("t1 state:" + thread.getState());

//		User user = userMapper.queryUserById("3");
//		System.out.println(user);
		System.out.println(template);
	}

}
