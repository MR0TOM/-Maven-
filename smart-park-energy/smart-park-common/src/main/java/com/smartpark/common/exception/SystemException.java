package com.smartpark.common.exception;

import com.smartpark.common.enums.ResultCode;
import lombok.Getter;

import java.io.Serial;

@Getter
public class SystemException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Integer code;
    private final String message;

    public SystemException(String message) {
        super(message);
        this.code = ResultCode.INTERNAL_SERVER_ERROR.getCode();
        this.message = message;
    }

    public SystemException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public SystemException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }
}
