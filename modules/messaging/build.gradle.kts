plugins {
    id("com.github.davidmc24.gradle.plugin.avro")
}

dependencies {
    // Kafka
    implementation("org.springframework.kafka:spring-kafka")
    implementation("io.confluent:kafka-schema-registry-client")
    implementation("io.confluent:kafka-avro-serializer")
    implementation("io.confluent:kafka-streams-avro-serde")
}

avro {
    outputCharacterEncoding.set("UTF-8")
    stringType.set("String")
}
