package com.alvkoen

import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.junit.After
import org.junit.Before
import org.testcontainers.containers.PostgreSQLContainer

open class BaseTest {

	val postgreSQLContainer = PostgreSQLContainer<Nothing>("postgres:13.6")

	@Before
	fun setUp() {
		postgreSQLContainer.start()

		val flyway = Flyway.configure()
			.dataSource(postgreSQLContainer.jdbcUrl, postgreSQLContainer.username, postgreSQLContainer.password)
			.load()

		flyway.migrate()

		Database.connect(
			url = postgreSQLContainer.jdbcUrl,
			driver = "org.postgresql.Driver",
			user = postgreSQLContainer.username,
			password = postgreSQLContainer.password
		)
	}

	@After
	fun tearDown() {
		postgreSQLContainer.stop()
	}
}