package com.alvkoen

import com.alvkoen.db.TemplateRepository
import com.alvkoen.db.WorkoutRepository
import io.ktor.server.application.*
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation

import com.alvkoen.plugins.*
import com.alvkoen.service.ActivitiesService
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.serialization.gson.gson
import java.text.DateFormat
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>): Unit =
	io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {

//	install(ContentNegotiation) {
//		json()
//	}

	// Configure Hikari connection pool
	val hikariConfig = HikariConfig().apply {
		jdbcUrl = "jdbc:postgresql://localhost:5432/jujudb"
		driverClassName = "org.postgresql.Driver"
		username = "postgres"
		password = "postgres"
		maximumPoolSize = 3
	}
	val dataSource = HikariDataSource(hikariConfig)

	// Configure Flyway
	val flyway = Flyway.configure()
		.dataSource(dataSource)
		.locations("classpath:db/migration")
		.load()

	// Run migrations
	flyway.migrate()

	// Set up Exposed
	Database.connect(dataSource)



	val workoutsService = ActivitiesService(WorkoutRepository(), TemplateRepository())



	configureRouting(workoutsService)

	install(ContentNegotiation) {
		gson {
			setDateFormat(DateFormat.LONG)
			setPrettyPrinting()
		}
	}
}
