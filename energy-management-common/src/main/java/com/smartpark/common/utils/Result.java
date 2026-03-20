package com.smartpark.common.utils;

import com.smartpark.common.constant.CommonConstants;
import com.smartpark.common.enums.ResultCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应结果
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    private T data;

    private Long timestamp;

    public Result() {
        this.timestamp = System.currentTimeMillis();
    }

    public Result(Integer code, String message) {
        this();
        this.code = code;
        this.message = message;
    }

    public Result(Integer code, String message, T data) {
        this(code, message);
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<>(CommonConstants.SUCCESS, "操作成功");
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>(CommonConstants.SUCCESS, "操作成功");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(CommonConstants.SUCCESS, message, data);
    }

    public static <T> Result<T> fail() {
        return new Result<>(CommonConstants.FAIL, "操作失败");
    }

    public static <T> Result<T> fail(String message) {
        return new Result<>(CommonConstants.FAIL, message);
    }

    public static <T> Result<T> fail(Integer code, String message) {
        return new Result<>(code, message);
    }

    public static <T> Result<T> fail(ResultCodeEnum resultCode) {
        return new Result<>(resultCode.getCode(), resultCode.getMessage());
    }

    public static <T> Result<T> fail(ResultCodeEnum resultCode, String message) {
        return new Result<>(resultCode.getCode(), message);
    }

    public static <T> Result<T> status(boolean flag) {
        return flag ? success() : fail();
    }
}
