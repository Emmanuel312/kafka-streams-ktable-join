package com.kafka.streams.join

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.EnableKafkaStreams

@SpringBootApplication
@EnableKafka
@EnableKafkaStreams
class JoinApplication

fun main(args: Array<String>) {
	runApplication<JoinApplication>(*args)
}
