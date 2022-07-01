import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("com.github.davidmc24.gradle.plugin.avro") version "1.2.0" apply false
    id("org.flywaydb.flyway") version "6.4.2" apply false
    id("org.springframework.boot") version "2.7.1" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

// Dependencies: Variables
val kotlinVersion: String by extra
val kotlinxCoroutinesVersion: String by extra
val kotlinTestVersion: String by extra
val kotlinLoggingVersion: String by extra
val log4jVersion: String by extra
val jarName: String by extra
val groupName: String by extra
val springKafkaVersion: String by extra
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

extra.set("log4j2.version", log4jVersion)
extra.set("spring-kafka.version", springKafkaVersion)
extra.set("kotlin.version", kotlinVersion)

// Project Configuration
group = groupName

allprojects {
    apply {
        plugin("org.springframework.boot")
        plugin("kotlin")
        plugin("io.spring.dependency-management")
        plugin("kotlin-spring")
    }

    version = projectVersion

    repositories {
        mavenCentral()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.named<BootJar>("bootJar") {
        val mainClassName = mainModule.plus(".").plus(mainClassName)
        mainClass.set(mainClassName)
        archiveClassifier.set(mainClassName)
    }

    tasks.named<Jar>("jar") {
        archiveClassifier.set(jarName)
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = javaVersion
        }
    }
}

// Configuration only for sub-projects
subprojects {
    repositories {
        mavenCentral()
        maven("https://packages.confluent.io/maven/")
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:$springVersion")
            mavenBom("org.springframework.boot:spring-boot-starter-parent:$springVersion")
        }

        dependencies {
            // Kotlin
            dependency("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
            dependency("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")

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
            dependency("org.apache.avro:avro:$apacheAvroVersion")
            dependency("io.confluent:kafka-schema-registry-client:$confluentVersion")
            dependency("io.confluent:kafka-avro-serializer:$confluentVersion")
            dependency("io.confluent:kafka-streams-avro-serde:$confluentVersion")

            // Others
            dependency("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
            dependency("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
            dependency("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:$jacksonVersion")
            dependency("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")

            // Test
            dependency("org.springframework.boot:spring-boot-starter-test:$springVersion")
            dependency("io.projectreactor:reactor-test:$projectReactorVersion")
            dependency("io.kotlintest:kotlintest-runner-junit5:$kotlinTestVersion")
            dependency("io.kotlintest:kotlintest-extensions-spring:$kotlinTestVersion")
            dependency("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
            dependency("io.mockk:mockk:$mockkVersion")
            dependency("com.github.tomakehurst:wiremock:$wiremockVersion")
        }
    }

    dependencies {
        // Spring
        implementation("org.springframework.boot:spring-boot-starter")

        // Kotlin
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        // Test
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("io.projectreactor:reactor-test")
    }
}
