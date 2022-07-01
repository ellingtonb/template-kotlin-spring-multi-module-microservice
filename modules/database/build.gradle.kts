plugins {
    id("org.flywaydb.flyway")
}

dependencies {
    // Internal
    implementation(project(":business"))
    implementation(project(":api"))

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")

    // Postgres
    implementation("org.postgresql:postgresql")
    implementation("io.r2dbc:r2dbc-postgresql")

    // Flyway
    implementation("org.flywaydb:flyway-core")

    // Database
    implementation("io.r2dbc:r2dbc-h2")
}

flyway {
    connectRetries = 1
    initSql = """SELECT 1;"""
    baselineOnMigrate = true
    baselineVersion = "3.0"
}
