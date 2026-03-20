package com.smartpark.common.enums;

public enum DeleteFlagEnum {
    
    NOT_DELETED("0", "未删除"),
    DELETED("1", "已删除");

    private final String code;
    private final String desc;

    DeleteFlagEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
