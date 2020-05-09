package com.wen.common.exception;

import com.wen.common.result.ResultCode;
import lombok.Getter;

@Getter
public class TenException extends RuntimeException {

    int code;

    public TenException(int code) {
        this.code = code;
    }

    public TenException(String message, int code) {
        super(message);
        this.code = code;
    }

    public TenException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

}
