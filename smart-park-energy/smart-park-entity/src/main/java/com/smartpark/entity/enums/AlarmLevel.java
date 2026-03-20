package com.smartpark.entity.enums;

import lombok.Getter;

/**
 * 告警级别枚举
 */
@Getter
public enum AlarmLevel {

    INFO(1, "提示", "#909399"),
    WARNING(2, "警告", "#E6A23C"),
    ERROR(3, "严重", "#F56C6C"),
    EMERGENCY(4, "紧急", "#FF0000");

    private final Integer code;
    private final String desc;
    private final String color;

    AlarmLevel(Integer code, String desc, String color) {
        this.code = code;
        this.desc = desc;
        this.color = color;
    }

    public static AlarmLevel getByCode(Integer code) {
        for (AlarmLevel level : values()) {
            if (level.getCode().equals(code)) {
                return level;
            }
        }
        return null;
    }
}
