package com.github.xaidanwang.zhengke.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * @author wang yi fei
 * @date 2019/8/31 18:57
 */
@Configuration
public class SwaggerWebMvcConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()          // 定义当需要用户登录时候，转到的登录页面。
				.and()
				.authorizeRequests()    // 定义哪些URL需要被保护、哪些不需要被保护
				.mvcMatchers("/swagger-ui.html").authenticated()
				.anyRequest()
				.permitAll();       // 任何请求,登录后可以访问
	}
}

