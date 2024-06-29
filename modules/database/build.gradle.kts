plugins {
    id("org.flywaydb.flyway") version "6.4.2"
}

dependencies {
    // Internal
    implementation(project(":business"))

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")

    // Postgres
    implementation("org.postgresql:postgresql")
    implementation("io.r2dbc:r2dbc-postgresql")

    // Flyway
    implementation("org.flywaydb:flyway-core")

    // Database
    implementation("io.r2dbc:r2dbc-h2")

    // Test
    implementation("org.testcontainers:junit-jupiter")
    implementation("org.testcontainers:r2dbc")
    implementation("org.testcontainers:postgresql")

    testImplementation("org.flywaydb:flyway-core")
    testImplementation("org.flywaydb.flyway-test-extensions:flyway-spring-test")
}

flyway {
    connectRetries = 1
    initSql = """SELECT 1;"""
    baselineOnMigrate = true
    baselineVersion = "3.0"
}
