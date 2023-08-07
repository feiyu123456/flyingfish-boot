package com.flyingfish;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.flyingfish.interfacecustom")
@EnableCaching
public class FlyingfishBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlyingfishBootApplication.class, args);
	}

}
