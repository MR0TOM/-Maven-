package com.smartpark.model.enums;

import lombok.Getter;

@Getter
public enum StatisticsTypeEnum {

    HOURLY("HOURLY", "小时统计"),
    DAILY("DAILY", "日统计"),
    WEEKLY("WEEKLY", "周统计"),
    MONTHLY("MONTHLY", "月统计"),
    YEARLY("YEARLY", "年统计");

    private final String code;
    private final String desc;

    StatisticsTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static StatisticsTypeEnum getByCode(String code) {
        for (StatisticsTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
