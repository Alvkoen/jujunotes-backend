import dev.monosoul.jooq.RecommendedVersions

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
	kotlin("jvm") version "1.8.0"
	id("io.ktor.plugin") version "2.2.2"
	id("org.jetbrains.kotlin.plugin.serialization") version "1.8.0"
	id("dev.monosoul.jooq-docker") version "3.0.0"
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

jooq {
	withContainer {
		db {
			username = "postgres"
			password = "postgres"
			name = "postgres"
			port = 5432
		}
	}
}

dependencies {
	implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
	implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
	implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
	implementation("ch.qos.logback:logback-classic:$logback_version")
	implementation("io.ktor:ktor-serialization-gson:$ktor_version")
	jooqCodegen("org.postgresql:postgresql:42.3.8")
	implementation ("com.zaxxer:HikariCP:4.0.3")
	implementation("io.arrow-kt:arrow-core:1.2.0-RC")
	implementation("org.slf4j:slf4j-simple:1.7.32")
	implementation("org.jooq:jooq:${RecommendedVersions.JOOQ_VERSION}")
	implementation("org.flywaydb:flyway-core:${RecommendedVersions.FLYWAY_VERSION}")
	testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}