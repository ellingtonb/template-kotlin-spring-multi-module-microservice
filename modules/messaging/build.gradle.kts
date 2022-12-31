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
    implementation("io.projectreactor.kafka:reactor-kafka")

    // Jackson
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310") {
        exclude(group = "org.slf4j", module = "slf4j-log4j12")
    }

    // Kafka
    implementation("org.apache.avro:avro")
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
    testImplementation("org.springframework.cloud:spring-cloud-stream-test-support")
    testImplementation("org.springframework.kafka:spring-kafka-test")
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
