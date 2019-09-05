package com.github.xaidanwang.response;


import com.github.xaidanwang.annotation.CodeEntity;
import com.github.xaidanwang.annotation.CodeEnumInterface;

public enum ConstantEnum implements CodeEnumInterface {
    @CodeEntity(
            success = true,
            errCode = 0,
            msg = "操作成功"
    )
    GLOBAL_SUCCESS,
    @CodeEntity(
            success = false,
            errCode = 1,
            msg = "服务器开小差了，请稍后再试!"
    )
    GLOBAL_FAIL,
    @CodeEntity(
            success = false,
            errCode = 2,
            msg = "用户未登录!"
    )
    GLOBAL_NO_LOGIN,
    @CodeEntity(
            success = false,
            errCode = 3,
            msg = "操作太频繁，请稍后再试!"
    )
    GLOBAL_POST_FAST,
    @CodeEntity(
            success = false,
            errCode = 4,
            msg = "服务繁忙，请稍后再试！"
    )
    GLOBAL_FALL_BACK,
    @CodeEntity(
            success = false,
            errCode = 5,
            msg = "自定义错误信息！"
    )
    GLOBAL_FALL_CUSTOM,
    @CodeEntity(
            success = false,
            errCode = 100,
            msg = "参数不正确"
    )
    GLOBAL_PARAM_FAIL,

    @CodeEntity(
            success = false,
            errCode = 101,
            msg = "数据库查询失败"
    )
    GLOBAL_DATASOURCE_FAIL,

    @CodeEntity(
            success = false,
            errCode = 102,
            msg = "数据中心断开连接"
    )
    DATASOURCE_LOST,

    @CodeEntity(
            success = false,
            errCode = 7000,
            msg = "令牌无效"
    )
    INVALID_TOKEN;

    private ConstantEnum() {
    }

}
