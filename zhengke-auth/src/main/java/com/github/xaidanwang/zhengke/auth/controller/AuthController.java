package com.github.xaidanwang.zhengke.auth.controller;

import com.github.xaidanwang.zhengke.auth.exception.ParamException;
import com.github.xaidanwang.zhengke.auth.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@ApiOperation(value = "账号秘钥检测",httpMethod = "POST",notes = "账号秘钥检测")
	@RequestMapping(value = "/verify",method = RequestMethod.POST)
	public String atuhVerify(@RequestParam(value = "phoneId") String phoneId,@RequestParam(value = "token") String token){
		if (StringUtils.isEmpty(token) || token.length() != 32){
			throw new ParamException("秘钥不能为空或者格式不对");
		}
		if (StringUtils.isEmpty(phoneId) || phoneId.length() >= 64){
			throw new ParamException("机器码不能为空或者格式不对");
		}
		String result = authService.atuhVerify(phoneId,token);
		log.info("验证机器码: [{}] 秘钥: [{}]， 结果: [{}]",phoneId,token,result);
		return result;
	}
	@ApiOperation(value = "查询秘钥或者机器码的过期时间",httpMethod = "GET",notes = "查询秘钥或者机器码的过期时间")
	@RequestMapping(value = "/expireTime",method = RequestMethod.GET)
	public String getExpireTime(@RequestParam(value = "phoneId",required = false) String phoneId,@RequestParam(value = "token",required = false) String token){

		if (StringUtils.isEmpty(phoneId) && StringUtils.isEmpty(token)){
			throw new ParamException("机器码与秘钥必填一个");
		}
		String result = authService.getExpireTime(phoneId,token);
		log.info("验证机器码: [{}] 秘钥: [{}]， 结果: [{}]",phoneId,token,result);
		return result;
	}

	@ApiOperation(value = "删除所有过期的秘钥",httpMethod = "DELETE",notes = "删除所有过期的秘钥")
	@RequestMapping(value = "/expireTime",method = RequestMethod.DELETE)
	public String clearExpireToken(){
		String result = authService.clearExpireToken();
		return result;
	}


	@ApiOperation(value = "给某个秘钥重新设定过期时间",httpMethod = "POST",notes = "给某个秘钥重新设定过期时间")
	@RequestMapping(value = "/expireTime",method = RequestMethod.POST)
	public String setTokenExpireTime(@RequestParam(value = "token") String token,@RequestParam(value = "expireTime") String expireTime){
		if (StringUtils.isEmpty(token) || token.length() != 32){
			throw new ParamException("秘钥不能为空或者格式不对");
		}
		String result = authService.setTokenExpireTime(token,expireTime);
		return result;
	}

	@ApiOperation(value = "添加机器码设置秘钥",httpMethod = "POST",notes = "添加机器码设置秘钥")
	@RequestMapping(value = "/add/phoneId",method = RequestMethod.POST)
	public String addAuthDo(@RequestParam(value = "phoneId") String phoneId,@RequestParam(value = "expireTime") String expireTime){
		if (StringUtils.isEmpty(expireTime)){
			throw new ParamException("过期时间不能为空或者格式不对");
		}
		if (StringUtils.isEmpty(phoneId) || phoneId.length() >= 64){
			throw new ParamException("机器码不能为空或者格式不对");
		}
		String result = authService.addAuthDo(phoneId,expireTime);
		return result;
	}
}
