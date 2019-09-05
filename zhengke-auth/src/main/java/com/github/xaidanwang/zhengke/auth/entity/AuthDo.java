package com.github.xaidanwang.zhengke.auth.entity;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class AuthDo {
    private Long id;

    private String phoneid;

    private String token;

    private String updatetime;

    private String createtime;

    private Integer validityday;

    private String erpiredate;

    private String remark;

}