package com.alvkoen.plugins

import com.alvkoen.routes.templateRouting
import com.alvkoen.routes.workoutRouting
import com.alvkoen.service.ActivitiesService
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting(workoutsService: ActivitiesService) {
	routing {
		workoutRouting(workoutsService)
		templateRouting(workoutsService)
	}
}
