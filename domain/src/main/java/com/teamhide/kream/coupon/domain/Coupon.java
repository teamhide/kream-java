package com.teamhide.kream.coupon.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import com.teamhide.kream.config.database.BaseEntity;

@Entity
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_group_id")
    private CouponGroup couponGroup;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private CouponStatus status;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @Builder(builderMethodName = "issue")
    public Coupon(final CouponGroup couponGroup, final Long userId) {
        final LocalDateTime now = LocalDateTime.now();
        final Integer period = couponGroup.getPeriod();
        final LocalDateTime expiredAt =
                switch (couponGroup.getPeriodType()) {
                    case DAY -> now.plusDays(period);
                    case MONTH -> now.plusMonths(period);
                };

        this.couponGroup = couponGroup;
        this.userId = userId;
        this.status = CouponStatus.ISSUED;
        this.expiredAt = expiredAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }
}
