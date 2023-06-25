package com.flyingfish.flyingfishboot;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class FlyingfishBootApplicationTests {


	@Test
	void contextLoads() {

		Runnable r = () -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				log.error(e.getMessage());
			}
		};
		Thread thread = new Thread(r, "t1");
		System.out.println("t1 state:" + thread.getState());
		thread.start();
		System.out.println("t1 state:" + thread.getState());

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("t1 state:" + thread.getState());
	}

}
