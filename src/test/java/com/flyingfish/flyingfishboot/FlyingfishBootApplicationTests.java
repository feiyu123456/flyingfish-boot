package com.flyingfish.flyingfishboot;


import com.flyingfish.interfacecustom.UserMapper;
import com.flyingfish.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@MapperScan("com.flyingfish.interfacecustom")
@SpringBootTest
public class FlyingfishBootApplicationTests {


	@Autowired
	UserMapper userMapper;

	@Test
	public void contextLoads() {

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

		User user = userMapper.queryUserById("3");
		System.out.println(user);
	}

}
