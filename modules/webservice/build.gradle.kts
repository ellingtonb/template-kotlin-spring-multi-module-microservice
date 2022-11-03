import org.springframework.boot.gradle.tasks.bundling.BootJar

val jarName: String by extra
val projectVersion: String by extra
val mainClassName: String by extra
val mainModule: String by extra

plugins {
    id("org.springframework.boot")
}

dependencies {
    // Internal
    implementation(project(":business"))
    implementation(project(":api"))
    implementation(project(":database"))
    implementation(project(":messaging"))
    implementation(project(":third-party"))

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // Jackson
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
}

tasks {
    jar {
        enabled = true
        archiveFileName.set("$jarName-$projectVersion.${archiveExtension.get()}")
    }

    named<BootJar>("bootJar") {
        enabled = true
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        archiveFileName.set("$jarName-$projectVersion-bootJar.${archiveExtension.get()}")
    }
}
