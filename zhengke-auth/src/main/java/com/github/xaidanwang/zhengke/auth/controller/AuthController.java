package com.github.xaidanwang.zhengke.auth.controller;

import com.github.xaidanwang.response.CommonResult;
import com.github.xaidanwang.response.ConstantEnum;
import com.github.xaidanwang.zhengke.auth.entity.AuthDo;
import com.github.xaidanwang.zhengke.auth.exception.ParamException;
import com.github.xaidanwang.zhengke.auth.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author wang yi fei
 * @date 2019/8/29 14:54
 */
@RestController
@Slf4j
public class AuthController {
	@Autowired
	private AuthService authService;

	@RequestMapping(value = "/expireTime/list",method = RequestMethod.GET)
	public CommonResult<List<AuthDo>> getExpireTime(@RequestParam(value = "phoneid",required = false) String phoneid,@RequestParam(value = "token",required = false) String token,@RequestParam(value = "remark",required = false)String remark){

		if (StringUtils.isEmpty(phoneid) && StringUtils.isEmpty(token)){
			throw new ParamException("机器码与秘钥必填一个");
		}
		List<AuthDo> result = authService.getExpireTime(phoneid,token,remark);
		log.info("验证机器码: [{}] 秘钥: [{}]， 结果: [{}]",phoneid,token,result);
		return CommonResult.buildWithData(ConstantEnum.GLOBAL_SUCCESS,result);
	}

	@ApiOperation(value = "给某个秘钥或者某个备注下的秘钥增加过期时间",httpMethod = "GET",notes = "给某个秘钥或者某个备注下的秘钥增加过期时间")
	@RequestMapping(value = "/expireTime",method = RequestMethod.GET)
	public CommonResult setTokenExpireTime(@RequestParam(value = "token",required = false) String token,@RequestParam(value = "remark",required = false)String remark, @RequestParam(value = "time")Long time){
		authService.setTokenExpireTime(token,time,remark);
		return CommonResult.build(ConstantEnum.GLOBAL_SUCCESS);
	}


	@ApiOperation(value = "批量生成token",httpMethod = "GET",notes = "批量生成token")
	@GetMapping(value = "/add")
	public CommonResult addToken(@RequestParam(value = "mum")Integer num, @RequestParam(value = "remark")String remark, @RequestParam(value = "time")Integer time){
		authService.addToken(num,remark,time);
		return CommonResult.build(ConstantEnum.GLOBAL_SUCCESS);
	}

	@GetMapping(value = "/bind")
	@ApiOperation(value = "机器绑定秘钥",httpMethod = "GET",notes = "机器绑定秘钥")
	public CommonResult bindToken(@RequestParam(value = "phoneid")String phoneid, @RequestParam(value = "token")String token){
		authService.bindToken(phoneid,token);
		return CommonResult.build(ConstantEnum.GLOBAL_SUCCESS);
	}

	@ApiOperation(value = "批量或者单个查询",httpMethod = "GET",notes = "批量或者单个查询")
	@GetMapping(value = "/get")
	public CommonResult<List<AuthDo>> getAccountList(@RequestParam(value = "phoneid",required = false)String phoneid, @RequestParam(value = "token",required = false)String token, @RequestParam(value = "remark",required = false)String remark){
		List<AuthDo> authDoList = authService.getAccountList(phoneid,token,remark);
		return CommonResult.buildWithData(ConstantEnum.GLOBAL_SUCCESS,authDoList);
	}

	@ApiOperation(value = "删除所有过期的秘钥",httpMethod = "GET",notes = "删除所有过期的秘钥")
	@RequestMapping(value = "/token",method = RequestMethod.GET)
	public CommonResult<String> clearExpireToken(){
		String result = authService.clearExpireToken();
		return CommonResult.buildWithData(ConstantEnum.GLOBAL_SUCCESS,result);
	}

	@ApiOperation(value = "账号秘钥检测",httpMethod = "GET",notes = "账号秘钥检测")
	@RequestMapping(value = "/verify",method = RequestMethod.GET)
	public CommonResult atuhVerify(@RequestParam(value = "phoneid") String phoneid,@RequestParam(value = "token") String token){
		if (StringUtils.isEmpty(token) || token.length() != 32){
			throw new ParamException("秘钥不能为空或者格式不对");
		}
		if (StringUtils.isEmpty(phoneid) || phoneid.length() >= 64){
			throw new ParamException("机器码不能为空或者格式不对");
		}
		authService.atuhVerify(phoneid,token);
		log.info("验证机器码: [{}] 秘钥: [{}]， 结果: [{}]",phoneid,token);
		return CommonResult.build(ConstantEnum.GLOBAL_SUCCESS);
	}

	@PostMapping(value = "/login")
	public CommonResult login(@RequestParam(value = "username")String username,@RequestParam(value = "password")String password){

		if (username.equalsIgnoreCase("zh") && password.equalsIgnoreCase("1234567")){
			return CommonResult.build(ConstantEnum.GLOBAL_SUCCESS);
		}else {
			return CommonResult.build(ConstantEnum.GLOBAL_FAIL);
		}
	}

}
