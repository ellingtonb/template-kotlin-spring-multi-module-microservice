import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Dependencies: Variables
val kotlinVersion: String by extra
val kotlinxCoroutinesVersion: String by extra
val kotlinTestVersion: String by extra
val kotlinLoggingVersion: String by extra
val ktLintVersion: String by ext
val log4jVersion: String by extra
val jarName: String by extra
val groupName: String by extra
val springKafkaVersion: String by extra
val kafkaVersion: String by extra
val kafkaReactorVersion: String by extra
val javaVersion: String by extra
val springVersion: String by extra
val projectVersion: String by extra
val mainClassName: String by extra
val mainModule: String by extra
val projectReactorVersion: String by extra
val postgresDriverVersion: String by extra
val postgresR2dbcDriverVersion: String by extra
val flywayVersion: String by extra
val confluentVersion: String by extra
val apacheAvroVersion: String by extra
val mockkVersion: String by extra
val wiremockVersion: String by extra
val jacksonVersion: String by extra
val h2Version: String by extra
val kotlinFakerVersion: String by extra
val apacheCommonsLangVersion: String by extra
val springCloudVersion: String by extra
val springCloudSleuthOtelVersion: String by extra
val kotlinReactorExtensionsVersion: String by extra
val kotlinReactorTestVersion: String by extra
val disruptorVersion: String by extra
val kotestVersion: String by extra
val embeddedPostgresVersion: String by extra

extra.set("log4j2.version", log4jVersion)
extra.set("spring-kafka.version", springKafkaVersion)
extra.set("kotlin.version", kotlinVersion)
extra.set("kafka.version", kafkaVersion)

plugins {
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"

    id("org.springframework.boot") version "2.7.1" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

    id("com.github.davidmc24.gradle.plugin.avro") version "1.2.0" apply false

    id("org.flywaydb.flyway") version "6.4.2" apply false

    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
}

allprojects {
    // Project Configuration
    group = groupName
    version = projectVersion

    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://packages.confluent.io/maven/")
        maven("https://repo.spring.io/milestone")
    }
}

// Configuration only for sub-projects
subprojects {
    apply {
        plugin("kotlin")
        plugin("kotlin-spring")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("io.spring.dependency-management")
        plugin("org.jlleitschuh.gradle.ktlint")
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:$springVersion")
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
            mavenBom("org.springframework.cloud:spring-cloud-sleuth-otel-dependencies:$springCloudSleuthOtelVersion")
        }

        configurations {
            all {
                exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
                exclude(group = "org.apache.logging.log4j", module = "log4j-to-slf4j")
                exclude(group = "io.projectreactor.netty", module = "reactor-netty-http-brave")
            }
        }

        dependencies {
            // Kotlin
            dependency("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
            dependency("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
            dependency("io.projectreactor.kotlin:reactor-kotlin-extensions:$kotlinReactorExtensionsVersion")
            dependency("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")

            // Kotlin: Coroutines
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$kotlinxCoroutinesVersion")
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:$kotlinxCoroutinesVersion")

            // Webservice
            dependency("org.springframework.boot:spring-boot-starter-webflux:$springVersion")
            dependency("org.springframework.boot:spring-boot-starter-actuator:$springVersion")

            // Database
            dependency("org.springframework.boot:spring-boot-starter-data-r2dbc:$springVersion")
            dependency("org.postgresql:postgresql:$postgresDriverVersion")
            dependency("io.r2dbc:r2dbc-postgresql:$postgresR2dbcDriverVersion")
            dependency("org.flywaydb:flyway-core:$flywayVersion")
            dependency("io.r2dbc:r2dbc-h2:$h2Version")

            // Kafka
            dependency("org.springframework.kafka:spring-kafka:$springKafkaVersion")
            dependency("io.projectreactor.kafka:reactor-kafka:$kafkaReactorVersion")
            dependency("org.apache.avro:avro:$apacheAvroVersion")
            dependency("io.confluent:kafka-schema-registry-client:$confluentVersion")
            dependency("io.confluent:kafka-avro-serializer:$confluentVersion")
            dependency("io.confluent:kafka-streams-avro-serde:$confluentVersion")

            // Others
            dependency("com.lmax:disruptor:$disruptorVersion")
            dependency("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
            dependency("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
            dependency("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:$jacksonVersion")
            dependency("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
            dependency("io.github.serpro69:kotlin-faker:$kotlinFakerVersion")
            dependency("org.apache.commons:commons-lang3:$apacheCommonsLangVersion")

            // Test
            dependency("org.springframework.boot:spring-boot-starter-test:$springVersion")
            dependency("io.projectreactor:reactor-test:$projectReactorVersion")
            dependency("io.kotlintest:kotlintest-runner-junit5:$kotlinTestVersion")
            dependency("io.kotlintest:kotlintest-extensions-spring:$kotlinTestVersion")
            dependency("io.kotest:kotest-runner-junit5:$kotestVersion")
            dependency("io.kotest:kotest-assertions-core:$kotestVersion")
            dependency("io.kotest:kotest-property:$kotestVersion")
            dependency("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
            dependency("io.mockk:mockk:$mockkVersion")
            dependency("com.github.tomakehurst:wiremock:$wiremockVersion")
            dependency("io.zonky.test:embedded-database-spring-test:$embeddedPostgresVersion")
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinxCoroutinesVersion")
        }
    }

    dependencies {
        // Spring
        implementation("org.springframework.boot:spring-boot-configuration-processor")
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("org.springframework.boot:spring-boot-starter-log4j2")
        implementation("org.springframework.boot:spring-boot-starter-aop")

        // Jackson
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        // Kotlin
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("io.github.microutils:kotlin-logging")

        // Kotlin: Coroutines
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

        // Monitoring
        implementation("org.springframework.cloud:spring-cloud-starter-sleuth") {
            exclude(group = "org.springframework.cloud", module = "spring-cloud-sleuth-brave")
        }
        implementation("org.springframework.cloud:spring-cloud-sleuth-otel-autoconfigure")
        implementation("io.opentelemetry:opentelemetry-exporter-otlp-trace")

        // Others
        implementation("com.lmax:disruptor")
        implementation("org.apache.commons:commons-lang3")

        // Jackson
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        // Test
        testImplementation("io.kotest:kotest-runner-junit5")
        testImplementation("io.kotest:kotest-assertions-core")
        testImplementation("io.kotest:kotest-property")
        testImplementation("io.projectreactor:reactor-test")
        testImplementation("io.mockk:mockk")
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
        testImplementation("io.kotlintest:kotlintest-runner-junit5")
        testImplementation("io.kotlintest:kotlintest-extensions-spring")
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-debug")
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        }
    }

    tasks {
        withType<Test> {
            useJUnitPlatform()
        }

        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = javaVersion
            }
        }

        compileTestKotlin {
            kotlinOptions {
                jvmTarget = java.sourceCompatibility.toString()
                freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=all")
            }
        }

        ktlint {
            debug.set(false)
        }
    }
}
