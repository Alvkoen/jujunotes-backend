package com.alvkoen.routes

import com.alvkoen.model.Template
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

fun Route.templateRouting() {
	route("/templates") {
		get {
//	todo will take care of user id later when there's authorisation in place
			call.respond(HttpStatusCode.OK)
		}
		get("{id?}") {
			val id = call.parameters["id"] ?: return@get call.respondText(
				"Missing id",
				status = HttpStatusCode.BadRequest
			)
			call.respond(HttpStatusCode.OK)
		}
		post {
			val template = Gson().fromJson(call.receiveText(), Template::class.java)

			call.respond(HttpStatusCode.OK)
		}
		delete("{id?}") {
			val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)

			call.respond(HttpStatusCode.OK)
		}
	}
}