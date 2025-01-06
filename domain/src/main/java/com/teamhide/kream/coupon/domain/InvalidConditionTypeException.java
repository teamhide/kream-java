package com.teamhide.kream.coupon.domain;

import org.springframework.http.HttpStatus;

import com.teamhide.kream.config.exception.CustomException;

public class InvalidConditionTypeException extends CustomException {
    public InvalidConditionTypeException() {
        super(HttpStatus.BAD_REQUEST, "COUPON__INVALID_CONDITION_TYPE", "");
    }
}
