package com.example.demo.enums;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    INVALID_KEY(1000, "Invalid message key"),
    USER_EXISTED(1001,"User existed"),
    USER_NOT_FOUND(1002, "User not found"),
    USERNAME_INVALID(1003, "UserName must be at least 3 character"),
    PASSWORD_INVALID(1004, "Password must be at least 8 characters"),
    USER_NOT_EXISTED(1005,"User not existed"),
    UNAUTHENTICATED(1006,"Unauthenticated")
    ;

    int code;
    String message;


}
