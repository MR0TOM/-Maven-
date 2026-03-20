package com.smartpark.common.exception;

import lombok.Getter;

/**
 * 错误码枚举
 */
@Getter
public enum ErrorCode {

    SUCCESS(200, "操作成功"),
    FAIL(500, "操作失败"),
    PARAM_ERROR(400, "参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    SYSTEM_ERROR(500, "系统错误"),
    
    // 业务错误码 1000-1999
    ENERGY_DATA_NOT_FOUND(1001, "能耗数据不存在"),
    DEVICE_NOT_FOUND(1002, "设备不存在"),
    DEVICE_OFFLINE(1003, "设备离线"),
    
    // 数据统计错误码 2000-2999
    STATISTICS_ERROR(2001, "统计计算错误");

    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
