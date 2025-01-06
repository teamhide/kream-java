package com.teamhide.kream.coupon.domain;

import lombok.Getter;

@Getter
public enum ConditionType {
    FIRST_DOWNLOAD(ConditionValueType.BOOLEAN, "쿠폰을 처음 다운로드 받은 유저"),
    ONLY_FOR_SPECIFIC_USERS(ConditionValueType.LIST_LONG, "특정 유저 목록"),
    ;

    private final ConditionValueType valueType;
    private final String description;

    ConditionType(final ConditionValueType valueType, final String description) {
        this.valueType = valueType;
        this.description = description;
    }
}
