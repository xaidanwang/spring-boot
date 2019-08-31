package com.github.xaidanwang.zhengke.auth;

import com.github.xaidanwang.zhengke.auth.dao.AuthDoMapper;
import com.github.xaidanwang.zhengke.auth.entity.AuthDo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZhengkeAuthApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private AuthDoMapper authDoMapper;

	@Test
	public void testDao(){
		AuthDo authDo = new AuthDo();
		authDo.setExpiretime(LocalDateTime.now().toString());
		authDo.setId(10);
		authDo.setToken("11111111");
		authDo.setPhoneid("2222222");
		authDoMapper.insertSelective(authDo);

		AuthDo resultAuthDo= authDoMapper.selectByPrimaryKey(10);
		Assert.assertNotNull(resultAuthDo);
		System.out.println(resultAuthDo);


		//  删除测试数据
		authDoMapper.deleteByPrimaryKey(10);

	}

}
