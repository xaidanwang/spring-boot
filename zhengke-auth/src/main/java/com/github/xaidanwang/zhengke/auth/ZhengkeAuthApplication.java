package com.github.xaidanwang.zhengke.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan( basePackages = "com.github.xaidanwang.zhengke.auth.dao")
@SpringBootApplication
public class ZhengkeAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZhengkeAuthApplication.class, args);
	}

}
