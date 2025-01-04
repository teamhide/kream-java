package com.teamhide.kream.coupon;

import com.teamhide.kream.coupon.domain.CouponGroup;
import com.teamhide.kream.coupon.domain.CouponPeriodType;

public class CouponFixture {
    public static CouponGroup makeCouponGroup(
            final CouponPeriodType periodType, final Integer period) {
        return CouponGroup.builder().periodType(periodType).period(period).build();
    }
}
