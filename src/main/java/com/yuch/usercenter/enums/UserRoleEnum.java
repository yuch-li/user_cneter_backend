package com.yuch.usercenter.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum UserRoleEnum {

    NORMAL(0, "普通用户"),
    ADMIN(1, "管理员");

    @EnumValue
    private final Integer userRole;
    private final String desc;

    UserRoleEnum(Integer userRole, String desc) {
        this.userRole = userRole;
        this.desc = desc;
    }
}
