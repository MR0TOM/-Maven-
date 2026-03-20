package com.smartpark.entity.enums;

import lombok.Getter;

/**
 * 能耗类型枚举
 */
@Getter
public enum EnergyType {

    ELECTRICITY(1, "电", "kWh"),
    WATER(2, "水", "m³"),
    GAS(3, "燃气", "m³"),
    STEAM(4, "蒸汽", "t"),
    HEAT(5, "热力", "GJ");

    private final Integer code;
    private final String name;
    private final String unit;

    EnergyType(Integer code, String name, String unit) {
        this.code = code;
        this.name = name;
        this.unit = unit;
    }

    public static EnergyType getByCode(Integer code) {
        for (EnergyType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
