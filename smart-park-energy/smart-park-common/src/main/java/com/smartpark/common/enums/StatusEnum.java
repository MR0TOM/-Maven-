package com.smartpark.common.enums;

public enum StatusEnum {
    
    ENABLE("1", "启用"),
    DISABLE("0", "禁用");

    private final String code;
    private final String desc;

    StatusEnum(String code, String desc) {
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
