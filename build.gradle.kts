import com.github.davidmc24.gradle.plugin.avro.GenerateAvroJavaTask
import com.github.davidmc24.gradle.plugin.avro.GenerateAvroSchemaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.10"
    id("io.spring.dependency-management") version "1.1.0"
    id("com.github.davidmc24.gradle.plugin.avro") version "1.6.0"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"

}

group = "com.kafka.streams"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
    maven { url = uri("https://packages.confluent.io/maven/") }
}

extra["testcontainersVersion"] = "1.17.6"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.apache.kafka:kafka-streams")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.apache.avro:avro:1.11.1")
    implementation("io.confluent:kafka-avro-serializer:7.3.2")
    implementation("io.confluent:kafka-schema-registry-client:7.3.2")
    implementation("io.confluent:kafka-streams-avro-serde:7.3.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-avro:2.14.2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:kafka")
}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<GenerateAvroJavaTask> {
    setSource(file("src/main/resources/avro"))
}