package com.github.xaidanwang.zhengke.auth.service.impl;

import com.github.xaidanwang.zhengke.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author wang yi fei
 * @date 2019/8/29 14:55
 */
@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public String atuhVerify(String phoneId, String token) {
		
		return null;
	}
}
