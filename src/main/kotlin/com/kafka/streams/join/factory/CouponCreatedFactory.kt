package com.kafka.streams.join.factory

import com.kafka.streams.join.CouponCreated
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

object CouponCreatedFactory {

    fun create(): CouponCreated = CouponCreated.newBuilder()
        .setId(UUID.randomUUID())
        .setUserId(UUID.randomUUID())
        .setExpiredAt(Instant.now().plus(10, ChronoUnit.MINUTES).toString())
        .setValue(10.0)
        .build()
}