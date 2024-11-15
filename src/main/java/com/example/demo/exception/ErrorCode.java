package com.example.demo.exception;

public enum ErrorCode {
    INVALID_KEY(1000, "Invalid message key"),
    USER_EXISTED(1001,"User existed"),
    USER_NOT_FOUND(1002, "User not found"),
    USERNAME_INVALID(1003, "UserName must be at least 3 character"),
    PASSWORD_INVALID(1004, "Password must be at least 8 characters"),
    USER_NOT_EXISTED(1005,"User not existed"),
    UNAUTHENTICATED(1006,"Unauthenticated")
    ;

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
