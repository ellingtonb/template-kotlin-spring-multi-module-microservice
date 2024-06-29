import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot")
}

dependencies {
    // Internal
    implementation(project(":business"))
    implementation(project(":api"))

    // Spring
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
}

tasks {
    getByName<BootJar>("bootJar") {
        enabled = true
        archiveFileName.set("${rootProject.name}.${archiveExtension.get()}")
    }

    jar {
        enabled = false
    }
}