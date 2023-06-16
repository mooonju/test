package com.test1.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    /* UserException */
    DUPLICATED_USER_ID(HttpStatus.CONFLICT, "UserId가 중복됩니다"),
    USERID_NOT_FOUND(HttpStatus.NOT_FOUND, "UserId가 존재하지 않습니다"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "패스워드가 잘못되었습니다");

    private final HttpStatus status;
    private final String message;

}
