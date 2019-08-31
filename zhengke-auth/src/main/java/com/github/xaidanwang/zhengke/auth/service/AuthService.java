package com.github.xaidanwang.zhengke.auth.service;

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
	String atuhVerify(String phoneId,String token);

	/**
	 * 获取 机器码与 秘钥 的过期时间
	 * @param phoneId
	 * @param token
	 * @return
	 */
	String getExpireTime(String phoneId,String token);

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
	String setTokenExpireTime(String token,String expireTime);


	/**
	 * 添加机器码与过期时间
	 * @param phoneId
	 * @param expireTime
	 * @return
	 */
	String addAuthDo(String phoneId,String expireTime);


}
