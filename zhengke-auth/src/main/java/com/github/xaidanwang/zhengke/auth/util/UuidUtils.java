package com.github.xaidanwang.zhengke.auth.util;


import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * Created by Administrator on 2018/7/24.
 */
public class UuidUtils {
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String checkEmptyAndGetUUID(String uuid) {
        if (StringUtils.isEmpty(uuid)) {
            return getUUID();
        }
        return uuid;
    }
}
