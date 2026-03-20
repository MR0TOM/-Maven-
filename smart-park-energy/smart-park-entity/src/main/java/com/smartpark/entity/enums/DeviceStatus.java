package com.smartpark.entity.enums;

import lombok.Getter;

/**
 * 设备状态枚举
 */
@Getter
public enum DeviceStatus {

    NORMAL(1, "正常"),
    MAINTENANCE(2, "维护中"),
    FAULT(3, "故障"),
    DISABLED(4, "停用");

    private final Integer code;
    private final String desc;

    DeviceStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static DeviceStatus getByCode(Integer code) {
        for (DeviceStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
