package com.alvkoen.routes

import com.alvkoen.model.ActivityListResult
import com.alvkoen.model.ActivityResult
import com.alvkoen.model.Workout
import com.alvkoen.service.ActivitiesService
import com.google.gson.Gson
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receiveText
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import java.util.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun Route.workoutRouting(activitiesService: ActivitiesService) {
	val logger: Logger = LoggerFactory.getLogger("WorkoutRouting")

	//	todo will take care of user id later when there's authorisation in place
	route("/workouts") {
		get {
			when (val result = activitiesService.getWorkouts()) {
				is ActivityListResult.Success<Workout> -> {
					call.respond(HttpStatusCode.OK, result.values)
				}
				is ActivityListResult.Failure -> {
					logger.error(result.reason, result.exception)
					call.respond(HttpStatusCode.InternalServerError, result.reason)
				}
			}
		}
		get("{id?}") {
			val id = call.parameters["id"] ?: return@get call.respondText(
				"Missing id",
				status = HttpStatusCode.BadRequest
			)

			when (val result = activitiesService.getWorkoutById(UUID.fromString(id))) {
				is  ActivityResult.Success<*> -> {
					val workout = result.value
					if (workout != null) {
						call.respond(HttpStatusCode.OK, workout)
					} else {
						call.respond(HttpStatusCode.NotFound, "Workout not found")
					}
				}
				is ActivityResult.Failure -> {
					logger.error(result.reason, result.exception)
					call.respond(HttpStatusCode.InternalServerError, result.reason)
				}
			}
		}
		post {
			val workout = Gson().fromJson(call.receiveText(), Workout::class.java)

			when (val result = activitiesService.addWorkout(workout)) {
				is ActivityResult.Success<UUID> -> {
					call.respond(HttpStatusCode.Created, result.value)
				}
				is ActivityResult.Failure -> {
					logger.error(result.reason, result.exception)
					call.respond(HttpStatusCode.InternalServerError, result.reason)
				}
			}
		}
		delete("{id?}") {
			val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)

			when (val result = activitiesService.deleteWorkout(UUID.fromString(id))) {
				is ActivityResult.Success<Boolean> -> {
					call.respond(HttpStatusCode.Created, result.value)
				}
				is ActivityResult.Failure -> {
					logger.error(result.reason, result.exception)
					call.respond(HttpStatusCode.InternalServerError, result.reason)
				}
			}
		}
	}
}