package com.smartpark.model.enums;

import lombok.Getter;

@Getter
public enum EnergyTypeEnum {

    ELECTRICITY("ELECTRICITY", "电力"),
    WATER("WATER", "水"),
    GAS("GAS", "天然气"),
    HEAT("HEAT", "热力"),
    COOLING("COOLING", "冷源");

    private final String code;
    private final String desc;

    EnergyTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static EnergyTypeEnum getByCode(String code) {
        for (EnergyTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
