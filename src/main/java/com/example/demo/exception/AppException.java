package com.example.demo.exception;

import com.example.demo.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppException extends RuntimeException {
    private final ErrorCode errorCode;

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
