package com.teamhide.kream.coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponDiscountInfo {
    @Column(name = "discount_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private CouponDiscountType discountType;

    @Column(name = "discount_value", nullable = false)
    private Integer discountValue;

    @Builder
    public CouponDiscountInfo(final CouponDiscountType discountType, final Integer discountValue) {
        this.discountType = discountType;
        this.discountValue = discountValue;
    }
}
