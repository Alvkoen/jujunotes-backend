package com.alvkoen.plugins

import com.alvkoen.routes.templateRouting
import com.alvkoen.routes.workoutRouting
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.configureRouting() {
	routing {
		workoutRouting()
		templateRouting()
	}
}
