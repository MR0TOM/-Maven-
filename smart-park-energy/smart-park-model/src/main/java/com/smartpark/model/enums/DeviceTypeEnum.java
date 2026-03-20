package com.smartpark.model.enums;

import lombok.Getter;

@Getter
public enum DeviceTypeEnum {

    METER("METER", "计量表"),
    SENSOR("SENSOR", "传感器"),
    CONTROLLER("CONTROLLER", "控制器"),
    GATEWAY("GATEWAY", "网关"),
    ACTUATOR("ACTUATOR", "执行器");

    private final String code;
    private final String desc;

    DeviceTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static DeviceTypeEnum getByCode(String code) {
        for (DeviceTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
