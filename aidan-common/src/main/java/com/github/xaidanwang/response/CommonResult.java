package com.github.xaidanwang.response;


import java.io.Serializable;


public class CommonResult<T> implements Serializable {

    private boolean success;

    private int errCode;

    private String msg;

    private T data;

    public static <T> CommonResult<T> buildWithData(ConstantEnum constantEnum, T data) {
        return new CommonResult(constantEnum, data);
    }

    public static <T> CommonResult<T> build(ConstantEnum constantEnum) {
        return new CommonResult(constantEnum, null);
    }

    public static <T> CommonResult<T> build(boolean success, int errCode, String msg) {
        return new CommonResult(success, errCode, msg, null);
    }

    public static <T> CommonResult<T> buildWithData(boolean success, int errCode, String msg, T data) {
        return new CommonResult(success, errCode, msg, data);
    }

    public static <T> CommonResult<T> buildWithDatAndMessage(ConstantEnum constantEnum, T data, String msg) {
        return new CommonResult(constantEnum.success(), constantEnum.errCode(), msg, data);
    }

    public CommonResult() {
    }

    public CommonResult(ConstantEnum constantEnum, T data) {
        this.success = constantEnum.success();
        this.errCode = constantEnum.errCode();
        this.msg = constantEnum.msg();
        this.data = data;
    }

    public CommonResult(boolean success, int errCode, String msg, T data) {
        this.success = success;
        this.errCode = errCode;
        this.msg = msg;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return this.data;
    }


    public void setData(T data) {
        this.data = data;
    }



    @Override
    public String toString() {
        return "CommonResult{" +
                "success=" + success +
                ", errCode=" + errCode +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
