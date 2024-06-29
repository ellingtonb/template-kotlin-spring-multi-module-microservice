import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Dependencies: Variables
val jarName: String by extra
val groupName: String by extra
val projectVersion: String by extra

val kotlinVersion: String by extra
val kotlinxCoroutinesVersion: String by extra
val kotlinLoggingVersion: String by extra
val springKafkaVersion: String by extra
val springVersion: String by extra
val postgresDriverVersion: String by extra
val postgresR2dbcDriverVersion: String by extra
val flywayVersion: String by extra
val confluentVersion: String by extra
val mockkVersion: String by extra
val wiremockVersion: String by extra
val jacksonVersion: String by extra
val h2Version: String by extra
val apacheCommonsLangVersion: String by extra
val springCloudVersion: String by extra
val disruptorVersion: String by extra
val kotestVersion: String by extra
val testContainersVersion: String by extra
val flywayTestVersion: String by extra
val kotlinOpenApiVersion: String by extra
val awsSdkVersion: String by extra
val resilience4jVersion: String by extra
val kotlinFakerVersion: String by extra
val snappyJavaVersion: String by extra
val scalaVersion: String by extra
val zookeeperVersion: String by extra
val bcprovVersion: String by extra
val apacheCompressVersion: String by extra

val javaVersion = JavaVersion.VERSION_17
java.sourceCompatibility = JavaVersion.VERSION_17

java {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

plugins {
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"

    id("org.springframework.boot") version "2.7.18" apply false
    id("io.spring.dependency-management") version "1.1.4"

    id("com.github.davidmc24.gradle.plugin.avro") version "1.9.1" apply false

    id("org.flywaydb.flyway") version "6.4.2" apply false

    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"

    id("jacoco")

    idea
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

    tasks {
        register<JacocoReport>("jacocoAggregateReport") {
            dependsOn(subprojects.map { it.tasks.withType<Test>() })
            dependsOn(subprojects.map { it.tasks.withType<JacocoReport>() })
            additionalSourceDirs.setFrom(subprojects.map { it.the<SourceSetContainer>()["main"].allSource.srcDirs })
            sourceDirectories.setFrom(subprojects.map { it.the<SourceSetContainer>()["main"].allSource.srcDirs })
            classDirectories.setFrom(subprojects.map { it.the<SourceSetContainer>()["main"].output })
            executionData.setFrom(project.fileTree(".") { include("**/build/jacoco/*.exec") })

            reports {
                xml.required.set(true)
                html.required.set(true)
            }
        }
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
        plugin("jacoco")
    }

    configurations {
        all {
            exclude(group = "ch.qos.logback", module = "logback-classic")
            exclude(group = "org.apache.logging.log4j", module = "log4j-to-slf4j")
            exclude(module = "spring-boot-starter-logging")
            exclude(group = "ch.qos.logback", module = "logback-core")
            exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
            exclude(group = "io.projectreactor.netty", module = "reactor-netty-http-brave")
        }
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:$springVersion")
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
            mavenBom("com.amazonaws:aws-java-sdk-bom:$awsSdkVersion")
            mavenBom("io.github.resilience4j:resilience4j-bom:$resilience4jVersion")
        }

        dependencies {
            // Spring Dependencies
            dependency("org.springframework.kafka:spring-kafka:$springKafkaVersion")

            // Kotlin
            dependency("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
            dependency("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$kotlinxCoroutinesVersion")
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:$kotlinxCoroutinesVersion")
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:$kotlinxCoroutinesVersion")
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinxCoroutinesVersion")

            // Kotlin: Coroutines
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$kotlinxCoroutinesVersion")
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:$kotlinxCoroutinesVersion")

            // Webservice
            dependency("org.springframework.boot:spring-boot-starter-webflux:$springVersion")
            dependency("org.springframework.boot:spring-boot-starter-actuator:$springVersion")
            dependency("org.springframework.boot:spring-boot-starter-validation:$springVersion")

            // Database
            dependency("org.springframework.boot:spring-boot-starter-data-r2dbc:$springVersion")
            dependency("org.postgresql:postgresql:$postgresDriverVersion")
            dependency("io.r2dbc:r2dbc-postgresql:$postgresR2dbcDriverVersion")
            dependency("org.flywaydb:flyway-core:$flywayVersion")
            dependency("io.r2dbc:r2dbc-h2:$h2Version")

            // Kafka
            dependency("io.confluent:kafka-schema-registry-client:$confluentVersion")
            dependency("io.confluent:kafka-avro-serializer:$confluentVersion")
            dependency("io.confluent:kafka-streams-avro-serde:$confluentVersion")

            // Open API
            dependency("org.springdoc:springdoc-openapi-kotlin:$kotlinOpenApiVersion")
            dependency("org.springdoc:springdoc-openapi-webflux-ui:$kotlinOpenApiVersion")

            // Jackson
            dependency("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
            dependency("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
            dependency("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:$jacksonVersion")
            dependency("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")

            // Others
            dependency("io.github.serpro69:kotlin-faker:$kotlinFakerVersion")
            dependency("org.apache.commons:commons-lang3:$apacheCommonsLangVersion")

            // Logs
            dependency("com.lmax:disruptor:$disruptorVersion")
            dependency("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")

            // Test
            dependency("org.springframework.boot:spring-boot-starter-test:$springVersion")
            dependency("io.kotest:kotest-runner-junit5:$kotestVersion")
            dependency("io.kotest:kotest-assertions-core:$kotestVersion")
            dependency("io.kotest:kotest-assertions-json:$kotestVersion")
            dependency("io.kotest:kotest-extensions-jvm:$kotestVersion")
            dependency("io.kotest:kotest-property:$kotestVersion")
            dependency("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinxCoroutinesVersion")

            // Test: Mock
            dependency("io.mockk:mockk:$mockkVersion")
            dependency("com.github.tomakehurst:wiremock:$wiremockVersion")

            // Test: Test Containers
            dependency("org.testcontainers:testcontainers-bom:$testContainersVersion")
            dependency("org.testcontainers:testcontainers:$testContainersVersion")
            dependency("org.testcontainers:r2dbc:$testContainersVersion")
            dependency("org.testcontainers:postgresql:$testContainersVersion")
            dependency("org.testcontainers:junit-jupiter:$testContainersVersion")
            dependency("org.testcontainers:localstack:$testContainersVersion")
            dependency("org.testcontainers:kafka:$testContainersVersion")
            dependency("org.testcontainers:mockserver:$testContainersVersion")

            // Test: Database
            dependency("org.flywaydb.flyway-test-extensions:flyway-test:$flywayTestVersion")
            dependency("org.flywaydb.flyway-test-extensions:flyway-spring-test:$flywayTestVersion")
        }
    }

    dependencies {
        // Spring
        implementation("org.springframework.boot:spring-boot-starter-aop")
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("org.springframework.boot:spring-boot-configuration-processor")
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("org.springframework.boot:spring-boot-starter-log4j2")

        // Jackson
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        // Kotlin
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm")

        // Logs
        implementation("com.lmax:disruptor")
        implementation("io.github.microutils:kotlin-logging")

        // Kotlin: Coroutines
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

        // Resilience4J
        implementation("io.github.resilience4j:resilience4j-spring-boot2")
        implementation("io.github.resilience4j:resilience4j-kotlin")
        implementation("io.github.resilience4j:resilience4j-reactor")

        // Monitoring
        implementation("io.micrometer:micrometer-registry-prometheus")

        // Others
        implementation("org.apache.commons:commons-lang3")

        // Jackson
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        // Test
        testImplementation("io.mockk:mockk")
        testImplementation("io.kotest:kotest-runner-junit5")
        testImplementation("io.kotest:kotest-assertions-core")
        testImplementation("io.kotest:kotest-property")
        testImplementation("io.kotest:kotest-assertions-core-jvm")
        testImplementation("io.kotest:kotest-assertions-json")
        testImplementation("io.kotest:kotest-extensions-jvm")
        testImplementation("io.kotlintest:kotlintest-runner-junit5")
        testImplementation("io.kotlintest:kotlintest-extensions-spring")
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-debug")
        testImplementation("org.testcontainers:testcontainers")
        testImplementation("org.testcontainers:junit-jupiter")

        constraints {
            implementation("org.xerial.snappy:snappy-java") {
                version {
                    strictly(snappyJavaVersion)
                }
            }
            implementation("org.scala-lang:scala-library") {
                version {
                    strictly(scalaVersion)
                }
            }
            implementation("org.apache.zookeeper:zookeeper") {
                version {
                    strictly(zookeeperVersion)
                }
            }
            implementation("org.bouncycastle:bcprov-jdk18on") {
                version {
                    strictly(bcprovVersion)
                }
            }
            implementation("org.bouncycastle:bcpkix-jdk18on") {
                version {
                    strictly(bcprovVersion)
                }
            }
            implementation("org.apache.commons:commons-compress") {
                version {
                    strictly(apacheCompressVersion)
                }
            }
        }
    }

    tasks {
        withType<Test> {
            useJUnitPlatform()
        }

        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = java.sourceCompatibility.toString()
            }
        }

        compileTestKotlin {
            kotlinOptions {
                jvmTarget = java.sourceCompatibility.toString()
                freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=all")
            }
        }

        jacocoTestReport {
            reports {
                xml.required.set(true)
                html.required.set(true)
            }

            // Excluir arquivos do relatório de cobertura
            classDirectories.setFrom(
                files(
                    classDirectories.files.map {
                        fileTree(it) {
                            exclude("**/config/**/*", "**/exception/**/*", "**/config/kafka/*", "**/request/webhook/mova/hub/*")
                        }
                    }
                )
            )
        }

        jacocoTestCoverageVerification {
            dependsOn(jacocoTestReport)
        }

        check {
            dependsOn(jacocoTestCoverageVerification)
        }

        ktlint {
            debug.set(false)
        }
    }
}
