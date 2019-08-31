package com.github.xaidanwang.zhengke.auth.service.impl;

import com.github.xaidanwang.zhengke.auth.dao.AuthDoMapper;
import com.github.xaidanwang.zhengke.auth.entity.AuthDo;
import com.github.xaidanwang.zhengke.auth.exception.ServiceException;
import com.github.xaidanwang.zhengke.auth.service.AuthService;
import com.github.xaidanwang.zhengke.auth.util.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author wang yi fei
 * @date 2019/8/29 14:55
 */
@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private AuthDoMapper authDoMapper;

	@Override
	public String atuhVerify(String phoneId, String token) {
		String expireTime = (String) stringRedisTemplate.opsForHash().get(phoneId,token);
		if (StringUtils.isEmpty(expireTime)){
			AuthDo authDo = authDoMapper.selectByPhoneIdAndToken(phoneId,token);
			if (authDo == null){
				throw new ServiceException("账号不存在或者机器码与秘钥不匹配");
			}
			expireTime = authDo.getExpiretime();
			stringRedisTemplate.opsForHash().put(phoneId,token,expireTime);
			try {
				stringRedisTemplate.boundHashOps(phoneId).expireAt(DateFormat.getDateInstance().parse(expireTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (verfiyExpireTime(expireTime)){
			return "账号已过期";
		}
		return "账号正常";
	}


	@Override
	public String getExpireTime(String phoneId, String token) {
		AuthDo authDo = authDoMapper.selectSelectiveByPhoneIdAndToken(phoneId,token);
		if (authDo == null){
			return "未找到账号信息";
		}
		return authDo.getExpiretime();
	}

	@Override
	public String clearExpireToken() {
		int  num = authDoMapper.deleteExpireAuthDo(LocalDateTime.now().toString());
		return "总共删除 " + num + " 个过期记录";
	}

	@Override
	public String setTokenExpireTime(String token,String expireTime) {
		AuthDo authDo = authDoMapper.selectSelectiveByPhoneIdAndToken(null,token);
		if (authDo == null){
			return "未找到账号信息";
		}
		int n = authDoMapper.updateByToken(token,expireTime);
		stringRedisTemplate.delete(authDo.getPhoneid());
		return "更新成功最新时间为: "+ expireTime;
	}

	@Override
	public String addAuthDo(String phoneId, String expireTime) {
		AuthDo authDo = new AuthDo();
		authDo.setPhoneid(phoneId);
		String token= UuidUtils.getUUID();
		authDo.setToken(token);
		authDo.setExpiretime(expireTime);
		authDoMapper.insertSelective(authDo);
		stringRedisTemplate.opsForHash().put(phoneId,token,expireTime);
		try {
			stringRedisTemplate.boundHashOps(phoneId).expireAt(DateFormat.getDateInstance().parse(expireTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "添加成功 秘钥 :" + token;
	}

	private boolean verfiyExpireTime(String time){
		return LocalDateTime.now().isAfter(LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	}
}
