package com.github.xaidanwang.zhengke.auth.service.impl;

import com.github.xaidanwang.zhengke.auth.dao.AuthDoMapper;
import com.github.xaidanwang.zhengke.auth.entity.AuthDo;
import com.github.xaidanwang.zhengke.auth.exception.ServiceException;
import com.github.xaidanwang.zhengke.auth.service.AuthService;
import com.github.xaidanwang.zhengke.auth.util.UuidUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

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
	public void atuhVerify(String phoneId, String token) {

		AuthDo authDo = authDoMapper.getAuthDo(phoneId,token);
		if (authDo == null){
			throw new ServiceException("机器码与秘钥不匹配");
		}
		String erpiredate = authDo.getErpiredate();
		if (LocalDateTime.now().isAfter(LocalDateTime.parse(erpiredate,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))){
			throw new ServiceException("秘钥过期");
		}

	}


	@Override
	public List<AuthDo> getExpireTime(String phoneId, String token,String remark) {
		return authDoMapper.getAuthDoList(phoneId,token,remark);
	}

	@Override
	public String clearExpireToken() {
		int num = authDoMapper.deleteByErpireDate();
		return "总共删除 " + num + " 个过期记录";
	}
	@Override
	public void setTokenExpireTime(String token, Long time, String remark) {
		if (!StringUtils.isEmpty(token)){
			AuthDo authDo = authDoMapper.getAuthDoByToken(token);
			if (authDo == null){
				throw new ServiceException("秘钥不存在");
			}
			addTime(authDo,time);
			return;
		}else {
			List<AuthDo> list = authDoMapper.getAuthDoByRemark(remark);
			if (list != null && !list.isEmpty()){
				list.forEach(authDo -> {
					addTime(authDo,time);
				});
			}
		}
	}

	@Override
	public String addAuthDo(String phoneId, String expireTime) {


		return "添加成功 秘钥 :" + 1;
	}

	@Override
	public void addToken(int num, String remark, int time) {
		for (int i = 0 ;i < num; i++){
			AuthDo authDo = new AuthDo();
			authDo.setToken(UuidUtils.getUUID());
			authDo.setRemark(remark);
			authDo.setValidityday(time);
			authDoMapper.insertSelective(authDo);
		}
	}

	@Override
	public void bindToken(String phoneid, String token) {

		AuthDo authDo = authDoMapper.getAuthDoByToken(token);
		if (authDo == null){
			throw new ServiceException("秘钥不存在");
		}
		if (StringUtils.isEmpty(authDo.getErpiredate())){
			Long validityDay = authDo.getValidityday().longValue();
			authDo.setErpiredate(LocalDateTime.now().plusDays(validityDay).toString());
			authDo.setPhoneid(phoneid);
			authDoMapper.updateByPrimaryKeySelective(authDo);
		}else {
			throw new ServiceException("秘钥已经使用过了");
		}
	}

	@Override
	public List<AuthDo> getAccountList(String phoneid, String token, String remark) {

		List<AuthDo> authDo = authDoMapper.getAuthDoList(phoneid,token,remark);
		return authDo;
	}

	private boolean verfiyExpireTime(String time){
		return LocalDateTime.now().isAfter(LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	}

	private void addTime(AuthDo authDo,Long time){
		String erpiredate = authDo.getErpiredate();
		// 未绑定
		if (StringUtils.isEmpty(erpiredate)){
			Long time2 = authDo.getValidityday()+time;
			authDo.setValidityday(time2.intValue());
			authDoMapper.updateByPrimaryKeySelective(authDo);
			return;
		}else {
			Long time2 = authDo.getValidityday()+time;
			authDo.setValidityday(time2.intValue());

			String newerpiredate = LocalDateTime.parse(authDo.getErpiredate(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).plusDays(time).toString();
			authDo.setErpiredate(newerpiredate);
			authDoMapper.updateByPrimaryKeySelective(authDo);
			return;
		}
	}
}
