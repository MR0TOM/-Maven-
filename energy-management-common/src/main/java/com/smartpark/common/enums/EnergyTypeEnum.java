package com.smartpark.common.enums;

import lombok.Getter;

/**
 * 能源类型枚举
 */
@Getter
public enum EnergyTypeEnum {

    ELECTRICITY(1, "电力", "kWh"),
    WATER(2, "自来水", "m³"),
    GAS(3, "燃气", "m³"),
    HEAT(4, "热力", "kWh"),
    COLD(5, "冷气", "kWh");

    private final Integer code;
    private final String name;
    private final String unit;

    EnergyTypeEnum(Integer code, String name, String unit) {
        this.code = code;
        this.name = name;
        this.unit = unit;
    }

    public static EnergyTypeEnum getByCode(Integer code) {
        for (EnergyTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
