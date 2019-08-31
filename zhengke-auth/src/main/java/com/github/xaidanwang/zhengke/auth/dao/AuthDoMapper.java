package com.github.xaidanwang.zhengke.auth.dao;

import com.github.xaidanwang.zhengke.auth.entity.AuthDo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface AuthDoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AuthDo record);

    int insertSelective(AuthDo record);

    AuthDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AuthDo record);

    int updateByPrimaryKey(AuthDo record);


    AuthDo selectByPhoneIdAndToken(@Param(value = "phoneId") String phoneId,@Param(value = "token") String token);

    AuthDo selectSelectiveByPhoneIdAndToken(@Param(value = "phoneId") String phoneId,@Param(value = "token") String token);

    /**
     * 查出 expireTime <= locaTime 的记录
     * @param locaTime
     * @return
     */
    int deleteExpireAuthDo(@Param(value = "locaTime") String locaTime);

    int updateByToken(@Param(value = "token") String token,@Param(value = "expireTime")String expireTime);
}