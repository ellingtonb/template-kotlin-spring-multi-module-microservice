plugins {
    id("com.github.davidmc24.gradle.plugin.avro")
}

val awsSpringVersion: String by ext

dependencies {
    // Internal
    implementation(project(":business"))
    implementation(project(":api"))

    // Spring
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.projectreactor.kafka:reactor-kafka")

    // Kafka
    implementation("org.apache.avro:avro")
    implementation("io.confluent:kafka-schema-registry-client")
    implementation("io.confluent:kafka-avro-serializer")
    implementation("io.confluent:kafka-streams-avro-serde") {
        exclude(group = "org.slf4j", module = "slf4j-log4j12")
    }

    // AWS
    implementation(platform("io.awspring.cloud:spring-cloud-aws-dependencies:$awsSpringVersion"))
    implementation("io.awspring.cloud:spring-cloud-starter-aws")
    implementation("io.awspring.cloud:spring-cloud-starter-aws-messaging")
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
