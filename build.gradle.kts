
val ktorVersion = "2.3.0"
val kotlinVersion = "1.8.21"
val logbackVersion = "1.4.7"
val exposedVersion = "0.41.1"
val flywayVersion = "9.17.0"
val postgresVersion = "42.6.0"

plugins {
	kotlin("jvm") version "1.8.0"
	id("io.ktor.plugin") version "2.2.2"
	id("org.jetbrains.kotlin.plugin.serialization") version "1.8.0"
}

group = "com.alvkoen"
version = "0.0.1"
application {
	mainClass.set("io.ktor.server.netty.EngineMain")

	val isDevelopment: Boolean = project.ext.has("development")
	applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
	implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
	implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
	implementation("ch.qos.logback:logback-classic:$logbackVersion")
	implementation("io.ktor:ktor-serialization-gson:$ktorVersion")
	implementation ("com.zaxxer:HikariCP:4.0.3")
	implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
	implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
	implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
	implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
	implementation("org.flywaydb:flyway-core:$flywayVersion")
	implementation("org.postgresql:postgresql:$postgresVersion")
	implementation("io.arrow-kt:arrow-core:1.2.0-RC")
	implementation("org.slf4j:slf4j-simple:1.7.32")

	testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
	testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
	testImplementation("org.testcontainers:testcontainers:1.16.3")
	testImplementation("org.testcontainers:postgresql:1.16.3")
	testImplementation("org.flywaydb:flyway-core:8.5.1")
}