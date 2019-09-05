package com.github.xaidanwang.annotation;

import java.util.Arrays;

public interface CodeEnumInterface {

    default CodeEntity codeEntity() {
        try {
            return (CodeEntity) this.getClass().getField(this.toString()).getAnnotation(CodeEntity.class);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    default boolean success() {
        return this.codeEntity().success();
    }

    default int errCode() {
        return this.codeEntity().errCode();
    }

    default String msg() {
        return codeEntity().msg();
    }

    default String fullMsg(Object... args){
        return String.format("code:[%d] msg:[%s]",this.errCode(),this.msg());
    }

    static String toJsonStr(Class<? extends CodeEnumInterface> codeEnum){
        StringBuffer buffer = new StringBuffer();
        Arrays.asList(codeEnum.getEnumConstants()).forEach((every)->{
            buffer.append(every.errCode()).append(":").append(every.msg()).append(";\r\n");
        });
        return buffer.toString();
    }

}
