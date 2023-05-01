package com.alvkoen.routes

import com.alvkoen.model.ActivityListResult
import com.alvkoen.model.ActivityResult
import com.alvkoen.model.Template
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

fun Route.templateRouting(activitiesService: ActivitiesService) {
	val logger: Logger = LoggerFactory.getLogger("TemplateRouting")

	route("/templates") {
		//	todo will take care of user id later when there's authorisation in place
		get {
			when (val result = activitiesService.getTemplates()) {
				is ActivityListResult.Success<Template> -> {
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

			when (val result = activitiesService.getTemplateById(UUID.fromString(id))) {
				is  ActivityResult.Success<*> -> {
					val template = result.value
					if (template != null) {
						call.respond(HttpStatusCode.OK, template)
					} else {
						call.respond(HttpStatusCode.NotFound, "Template not found")
					}
				}
				is ActivityResult.Failure -> {
					logger.error(result.reason, result.exception)
					call.respond(HttpStatusCode.InternalServerError, result.reason)
				}
			}
		}
		post {
			val template = Gson().fromJson(call.receiveText(), Template::class.java)

			when (val result = activitiesService.addTemplate(template)) {
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

			when (val result = activitiesService.deleteTemplate(UUID.fromString(id))) {
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