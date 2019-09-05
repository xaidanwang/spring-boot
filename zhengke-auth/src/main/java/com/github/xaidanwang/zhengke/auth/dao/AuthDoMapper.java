package com.github.xaidanwang.zhengke.auth.dao;

import com.github.xaidanwang.zhengke.auth.entity.AuthDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthDoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AuthDo record);

    int insertSelective(AuthDo record);

    AuthDo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AuthDo record);

    int updateByPrimaryKey(AuthDo record);

    AuthDo getAuthDoByToken(@Param(value = "token") String token);

    List<AuthDo> getAuthDoList(@Param(value = "phoneid") String phoneid,@Param(value = "token") String token,@Param(value = "remark") String remark);

    AuthDo getAuthDo(@Param(value = "phoneid") String phoneid,@Param(value = "token") String token);

    int deleteByErpireDate();


    List<AuthDo> getAuthDoByRemark(@Param(value = "remark") String remark);
}