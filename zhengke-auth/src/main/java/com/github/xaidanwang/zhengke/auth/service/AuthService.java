package com.github.xaidanwang.zhengke.auth.service;

import com.github.xaidanwang.zhengke.auth.entity.AuthDo;

import java.util.List;

/**
 * @author wang yi fei
 * @date 2019/8/29 14:54
 */
public interface AuthService {


	/**
	 * 验证 机器码与 秘钥 是否正确
	 * @param phoneId
	 * @param token
	 * @return
	 */
	void atuhVerify(String phoneId,String token);

	String updateToken(String phoneId,String token);

	/**
	 * 获取 机器码与 秘钥 的过期时间
	 * @param phoneId
	 * @param token
	 * @return
	 */
	List<AuthDo> getExpireTime(String phoneId,String token,String remark);

	/**
	 * 清楚所有过去的信息
	 * @return
	 */
	String clearExpireToken();

	/**
	 * 给某个秘钥重新设置过期时间
	 * @param token
	 * @return
	 */
	void setTokenExpireTime(String token,Long time,String remark);


	/**
	 * 添加机器码与过期时间
	 * @param phoneId
	 * @param expireTime
	 * @return
	 */
	String addAuthDo(String phoneId,String expireTime);

	void bindToken(String phoneid,String token);
	void addToken(int num,String remark,int time);

	List<AuthDo> getAccountList(String phoneid,String token,String remark);
}
