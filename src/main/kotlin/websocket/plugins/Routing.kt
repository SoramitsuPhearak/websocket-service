package websocket.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.websocket.*

fun Application.configureRouting() {
    routing {
        route("/notify") {
            post ("/{channel}") {
                val data = call.receive<Map<String,String>>()
                val channel = call.parameters["channel"]

                SocketConnection.connections[channel]?.forEach {
                    it.session.send(data.toString())
                }

                return@post call.respond(HttpStatusCode.OK, data)
            }
        }
    }
}
