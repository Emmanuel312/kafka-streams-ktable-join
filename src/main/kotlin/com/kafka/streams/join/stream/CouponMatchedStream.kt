package com.kafka.streams.join.stream

import com.kafka.streams.join.CouponCreated
import com.kafka.streams.join.PurchaseCompleted
import com.kafka.streams.join.factory.CouponMatchedFactory
import com.kafka.streams.join.producer.GenericProducer
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.StreamsBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CouponMatchedStream(
    private val genericProducer: GenericProducer
) {

    @Autowired
    fun stream(streamsBuilder: StreamsBuilder) {
//        genericProducer.case2()
        val couponCreatedTable = streamsBuilder.stream<String, CouponCreated>("promotions.coupon-created")
            .map { _, value -> KeyValue.pair(value.id.toString(), value) }
            .toTable()

        val purchaseCompletedTable = streamsBuilder.stream<String, PurchaseCompleted>("purchases.completed")
            .map { _, value -> KeyValue.pair(value.couponId?.toString(), value) }
            .toTable()

        val couponMatchedTable = couponCreatedTable.join(purchaseCompletedTable) { couponCreated, purchaseCompleted ->
            CouponMatchedFactory.create(couponCreated, purchaseCompleted)
        }

        couponMatchedTable.toStream()
            .to("performance.coupon-matched")
    }
}