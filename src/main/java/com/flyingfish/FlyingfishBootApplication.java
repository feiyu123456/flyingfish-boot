package com.flyingfish;

import com.flyingfish.service.PersonService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@SpringBootApplication
@MapperScan("com.flyingfish.interfacecustom")
@EnableCaching
public class FlyingfishBootApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(FlyingfishBootApplication.class, args);
		RedisTemplate redisTemplate = context.getBean("redisTemplate", RedisTemplate.class);
		System.out.println(redisTemplate);
	}

}
