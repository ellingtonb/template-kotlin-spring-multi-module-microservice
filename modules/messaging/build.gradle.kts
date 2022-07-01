plugins {
    id("com.github.davidmc24.gradle.plugin.avro")
}

dependencies {
    // Spring
    implementation("org.springframework.kafka:spring-kafka")

    // Kafka
    implementation("org.apache.avro:avro")
    implementation("io.confluent:kafka-schema-registry-client")
    implementation("io.confluent:kafka-avro-serializer")
    implementation("io.confluent:kafka-streams-avro-serde") {
        exclude(group = "org.slf4j", module = "slf4j-log4j12")
    }
}

avro {
    isCreateSetters.set(true)
    isCreateOptionalGetters.set(false)
    isGettersReturnOptional.set(false)
    outputCharacterEncoding.set("UTF-8")
    stringType.set("String")
    isEnableDecimalLogicalType.set(true)
}
