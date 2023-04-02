package com.kafka.streams.join.producer

import com.kafka.streams.join.CouponCreated
import com.kafka.streams.join.PurchaseCompleted
import com.kafka.streams.join.factory.CouponCreatedFactory
import com.kafka.streams.join.factory.PurchaseCompletedFactory
import org.apache.kafka.streams.StreamsConfig
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class GenericProducer(
    private val couponCreatedProducer: KafkaTemplate<String, CouponCreated>,
    private val purchaseCompletedProducer: KafkaTemplate<String, PurchaseCompleted>
) {

    fun case1() {
        val couponCreated = CouponCreatedFactory.create()
        couponCreatedProducer.send("promotions.coupon-created", couponCreated.id.toString(), couponCreated)
        val purchaseCompleted = PurchaseCompletedFactory.create(
            couponId = couponCreated.id,
            userId = couponCreated.userId
        )
        purchaseCompletedProducer.send("purchases.completed", purchaseCompleted.id.toString(), purchaseCompleted)
    }

    fun case2() {
        val couponCreated = CouponCreatedFactory.create()
        couponCreatedProducer.send("promotions.coupon-created", couponCreated.id.toString(), couponCreated)
        val purchaseCompleted = PurchaseCompletedFactory.create(
            couponId = null,
            userId = couponCreated.userId
        )
        purchaseCompletedProducer.send("purchases.completed", purchaseCompleted.id.toString(), purchaseCompleted)
    }

    fun case3() {
        repeat(10) {
            case1()
            case2()
            Thread.sleep(1000)
        }
    }
}