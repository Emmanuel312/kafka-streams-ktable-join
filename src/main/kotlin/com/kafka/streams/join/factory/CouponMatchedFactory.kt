package com.kafka.streams.join.factory

import com.kafka.streams.join.CouponCreated
import com.kafka.streams.join.CouponMatched
import com.kafka.streams.join.PurchaseCompleted
import java.util.*

object CouponMatchedFactory {

    fun create(couponCreated: CouponCreated, purchaseCompleted: PurchaseCompleted): CouponMatched =
        CouponMatched.newBuilder()
            .setId(UUID.randomUUID())
            .setUserId(couponCreated.userId)
            .setCouponId(couponCreated.id)
            .setPurchaseId(purchaseCompleted.id)
            .setCouponValue(couponCreated.value)
            .setCouponExpiredAt(couponCreated.expiredAt)
            .build()
}
