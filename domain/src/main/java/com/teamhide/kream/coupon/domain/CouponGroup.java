package com.teamhide.kream.coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coupon_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identifier", nullable = false, length = 32)
    private String identifier;

    @Embedded private CouponDiscountInfo discountInfo;

    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private CouponGroupStatus status;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "remain_quantity", nullable = false)
    private Integer remainQuantity;

    @Column(name = "period_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponPeriodType periodType;

    @Column(name = "period", nullable = false)
    private Integer period;

    public boolean isAvailable() {
        return this.status == CouponGroupStatus.ACTIVATED;
    }

    public void decreaseRemainQuantity() {
        this.remainQuantity--;
    }

    @Builder
    public CouponGroup(
            final String identifier,
            final CouponDiscountType discountType,
            final Integer discountValue,
            final Integer quantity,
            final CouponPeriodType periodType,
            final Integer period) {
        this.identifier = identifier;
        this.discountInfo =
                CouponDiscountInfo.builder()
                        .discountType(discountType)
                        .discountValue(discountValue)
                        .build();
        this.status = CouponGroupStatus.ACTIVATED;
        this.quantity = quantity;
        this.remainQuantity = quantity;
        this.periodType = periodType;
        this.period = period;
    }

    public Integer getPeriod() {
        return period;
    }

    public CouponPeriodType getPeriodType() {
        return periodType;
    }
}
