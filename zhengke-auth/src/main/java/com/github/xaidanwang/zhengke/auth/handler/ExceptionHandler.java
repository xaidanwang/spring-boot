package com.github.xaidanwang.zhengke.auth.handler;

import com.github.xaidanwang.response.CommonResult;
import com.github.xaidanwang.response.ConstantEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author wang yi fei
 * @date 2019/4/6 14:47
 */
@ControllerAdvice
@RestControllerAdvice
@Slf4j
public class ExceptionHandler {


    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResult<String> dealParamException(Exception e) {
        return CommonResult.buildWithDatAndMessage(ConstantEnum.GLOBAL_FAIL,null,e.getMessage());
    }

}
