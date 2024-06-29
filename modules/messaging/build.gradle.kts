plugins {
    id("com.github.davidmc24.gradle.plugin.avro")
}

dependencies {
    // Internal
    implementation(project(":business"))
    implementation(project(":api"))

    // Spring
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // Jackson
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310") {
        exclude(group = "org.slf4j", module = "slf4j-log4j12")
    }

    // Kafka
    implementation("io.confluent:kafka-schema-registry-client")
    implementation("io.confluent:kafka-avro-serializer")
    implementation("io.confluent:kafka-streams-avro-serde") {
        exclude(group = "org.slf4j", module = "slf4j-log4j12")
    }

    // AWS
    implementation(platform("io.awspring.cloud:spring-cloud-aws-dependencies"))
    implementation("io.awspring.cloud:spring-cloud-starter-aws")
    implementation("io.awspring.cloud:spring-cloud-starter-aws-messaging")

    // Test
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.testcontainers:kafka")
}

avro {
    isCreateSetters.set(true)
    isCreateOptionalGetters.set(false)
    isGettersReturnOptional.set(false)
    outputCharacterEncoding.set("UTF-8")
    stringType.set("String")
    templateDirectory.set(null as String?)
    isEnableDecimalLogicalType.set(true)
}
