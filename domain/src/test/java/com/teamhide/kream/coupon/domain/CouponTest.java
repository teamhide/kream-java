package com.teamhide.kream.coupon.domain;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.teamhide.kream.coupon.CouponFixture;

class CouponTest {

    @Test
    void testIssueCouponWithPeriodTypeMonth() {
        // Given
        final int period = 2;
        final CouponGroup couponGroup = CouponFixture.makeCouponGroup(CouponPeriodType.MONTH, period);

        // When
        final Coupon sut = Coupon.issue().couponGroup(couponGroup).userId(1L).build();

        // Then
        final LocalDateTime expectedMonth = LocalDateTime.now().plusMonths(period);
        assertThat(sut.getExpiredAt().getMonth()).isEqualTo(expectedMonth.getMonth());
    }

    @Test
    void testIssueCouponWithPeriodTypeDay() {
        // Given
        int period = 2;
        final CouponGroup couponGroup = CouponFixture.makeCouponGroup(CouponPeriodType.DAY, period);

        // When
        final Coupon sut = Coupon.issue().couponGroup(couponGroup).userId(1L).build();

        // Then
        final LocalDateTime expectedDay = LocalDateTime.now().plusDays(period);
        assertThat(sut.getExpiredAt().getDayOfMonth()).isEqualTo(expectedDay.getDayOfMonth());
    }
}
