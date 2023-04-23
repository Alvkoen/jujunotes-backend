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
import javax.sql.DataSource
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL

fun main(args: Array<String>): Unit =
	io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {

	val dslContext = configureDslContext()
	val workoutsService = ActivitiesService(WorkoutRepository(dslContext), TemplateRepository(dslContext))

	configureRouting(workoutsService)

	install(ContentNegotiation) {
		gson {
			setDateFormat(DateFormat.LONG)
			setPrettyPrinting()
		}
	}
}

fun configureDslContext(): DSLContext {
	val config = HikariConfig()
	config.jdbcUrl = "jdbc:postgres://localhost:3306/juju"
	config.username = "test"
	config.password = "test"
	val dataSource = HikariDataSource(config)

	// Create the JooqConfig object and DSLContext
	val jooqConfig = JooqConfig(dataSource)
	return jooqConfig.dslContext()
}

class JooqConfig(private val dataSource: DataSource) {
	fun dslContext(): DSLContext {
		return DSL.using(dataSource, SQLDialect.POSTGRES)
	}
}