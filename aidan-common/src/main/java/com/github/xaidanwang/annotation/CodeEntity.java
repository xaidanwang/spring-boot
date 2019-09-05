package com.github.xaidanwang.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CodeEntity {
    boolean success();
    int errCode() default 0;
    String msg();
}
