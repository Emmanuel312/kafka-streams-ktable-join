package com.kafka.streams.join.factory

import com.kafka.streams.join.PurchaseCompleted
import java.util.*

object PurchaseCompletedFactory {

    fun create(couponId: UUID?, userId: UUID): PurchaseCompleted = PurchaseCompleted.newBuilder()
        .setId(UUID.randomUUID())
        .setUserId(userId)
        .setCouponId(couponId)
        .build()
}