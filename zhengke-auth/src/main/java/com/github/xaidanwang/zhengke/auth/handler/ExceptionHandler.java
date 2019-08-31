package com.github.xaidanwang.zhengke.auth.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wang yi fei
 * @date 2019/4/6 14:47
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandler {


    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String dealParamException(Exception e) {
        return e.getMessage();
    }

}
