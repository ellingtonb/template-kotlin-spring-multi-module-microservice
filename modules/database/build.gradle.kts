plugins {
    id("org.flywaydb.flyway")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.postgresql:postgresql")
    implementation("io.r2dbc:r2dbc-postgresql")
    implementation("org.flywaydb:flyway-core")
}

flyway {
    connectRetries = 1
    initSql = """SELECT 1;"""
    baselineOnMigrate = true
    baselineVersion = "3.0"
}
