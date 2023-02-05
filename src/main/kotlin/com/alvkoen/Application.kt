package com.alvkoen

import io.ktor.server.application.*
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation

import com.alvkoen.plugins.*
import io.ktor.serialization.gson.gson
import java.text.DateFormat

fun main(args: Array<String>): Unit =
	io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
	configureRouting()

	install(ContentNegotiation) {
		gson {
			setDateFormat(DateFormat.LONG)
			setPrettyPrinting()
		}
	}
}
