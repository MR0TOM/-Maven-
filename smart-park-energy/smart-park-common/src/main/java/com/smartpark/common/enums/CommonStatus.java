package com.smartpark.common.enums;

import lombok.Getter;

/**
 * 通用状态枚举
 */
@Getter
public enum CommonStatus {

    ENABLE(1, "启用"),
    DISABLE(0, "禁用");

    private final Integer code;
    private final String desc;

    CommonStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
