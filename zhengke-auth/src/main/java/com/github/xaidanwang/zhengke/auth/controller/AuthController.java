package com.github.xaidanwang.zhengke.auth.controller;

import com.github.xaidanwang.zhengke.auth.exception.ParamException;
import com.github.xaidanwang.zhengke.auth.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wang yi fei
 * @date 2019/8/29 14:54
 */
@RestController
@Slf4j
public class AuthController {
	@Autowired
	private AuthService authService;

	@RequestMapping(value = "/verify",method = RequestMethod.POST)
	public String atuhVerify(String phoneId,String token){

		if (StringUtils.isEmpty(token) || token.length() != 32){
			throw new ParamException("秘钥不能为空或者格式不对");
		}

		if (StringUtils.isEmpty(phoneId) || token.length() != 64){
			throw new ParamException("机器码不能为空或者格式不对");
		}

		return authService.atuhVerify(phoneId,token);
	}
}
