package com.github.xaidanwang.zhengke.auth.dao;

import com.github.xaidanwang.zhengke.auth.entity.AuthDo;

public interface AuthDoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AuthDo record);

    int insertSelective(AuthDo record);

    AuthDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AuthDo record);

    int updateByPrimaryKey(AuthDo record);
}