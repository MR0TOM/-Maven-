package com.smartpark.common.enums;

import lombok.Getter;

/**
 * 响应码枚举
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(200, "操作成功"),
    FAIL(500, "操作失败"),
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权访问"),
    FORBIDDEN(403, "没有权限"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),
    CONFLICT(409, "数据冲突"),
    TOO_MANY_REQUESTS(429, "请求过于频繁"),
    SERVER_ERROR(500, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),

    // 业务异常码 1000-1999
    USER_NOT_FOUND(1001, "用户不存在"),
    USERNAME_PASSWORD_ERROR(1002, "用户名或密码错误"),
    USER_DISABLED(1003, "用户已禁用"),
    TOKEN_INVALID(1004, "Token无效"),
    TOKEN_EXPIRED(1005, "Token已过期"),
    CAPTCHA_ERROR(1006, "验证码错误"),
    CAPTCHA_EXPIRED(1007, "验证码已过期"),

    // 数据异常码 2000-2999
    DATA_NOT_EXIST(2001, "数据不存在"),
    DATA_ALREADY_EXIST(2002, "数据已存在"),
    DATA_IMPORT_ERROR(2003, "数据导入失败"),
    DATA_EXPORT_ERROR(2004, "数据导出失败"),
    DATA_VALID_ERROR(2005, "数据校验失败"),

    // 能耗相关异常 3000-3999
    ENERGY_DATA_COLLECT_ERROR(3001, "能耗数据采集失败"),
    ENERGY_STATISTICS_ERROR(3002, "能耗统计计算失败"),
    ENERGY_REPORT_GENERATE_ERROR(3003, "能耗报表生成失败"),
    DEVICE_OFFLINE(3004, "设备离线"),
    DEVICE_COMMUNICATION_ERROR(3005, "设备通信异常"),

    // 告警相关异常 4000-4999
    ALARM_PROCESS_ERROR(4001, "告警处理失败"),
    ALARM_RULE_CONFIG_ERROR(4002, "告警规则配置错误");

    private final Integer code;
    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
