package com.github.xaidanwang.zhengke.auth.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

/**
 * @author wang yi fei
 * @date 2019/3/8 10:30
 */
@Component(value = "redisDao")
@Slf4j
public class RedisDao {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private HashOperations hashOperations;
    @Autowired
    private BoundListOperations<String, String> nameListCache;

    public void pushName(String name){
        nameListCache.leftPush(name);
    }

    public String popName(){
       return nameListCache.rightPop();
    }

}
