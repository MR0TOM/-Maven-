package com.smartpark.model.enums;

import lombok.Getter;

@Getter
public enum AlarmLevelEnum {

    CRITICAL("CRITICAL", "严重", 1),
    MAJOR("MAJOR", "主要", 2),
    MINOR("MINOR", "次要", 3),
    WARNING("WARNING", "警告", 4),
    INFO("INFO", "提示", 5);

    private final String code;
    private final String desc;
    private final Integer level;

    AlarmLevelEnum(String code, String desc, Integer level) {
        this.code = code;
        this.desc = desc;
        this.level = level;
    }

    public static AlarmLevelEnum getByCode(String code) {
        for (AlarmLevelEnum level : values()) {
            if (level.getCode().equals(code)) {
                return level;
            }
        }
        return null;
    }
}
