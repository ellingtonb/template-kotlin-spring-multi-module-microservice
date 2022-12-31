dependencies {
    // Internal
    implementation(project(":api"))

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-configuration-processor")

    // Jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    // Faker
    implementation("io.github.serpro69:kotlin-faker")

    // Others
    implementation("org.apache.commons:commons-lang3")
}
