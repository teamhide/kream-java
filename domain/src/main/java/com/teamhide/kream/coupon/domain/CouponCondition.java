package com.teamhide.kream.coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "coupon_condition")
public class CouponCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "coupon_group_id", nullable = false)
    private CouponGroup couponGroup;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition_type", length = 50, nullable = false)
    private ConditionType conditionType;

    @Column(name = "condition_value", length = 30, nullable = false)
    private String conditionValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition_value_type", length = 10, nullable = false)
    private ConditionValueType conditionValueType;
}
