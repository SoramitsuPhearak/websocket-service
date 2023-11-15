package websocket.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.time.Duration
import io.ktor.server.config.*
import websocket.Connection
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashSet


class SocketConnection {
    companion object {
        var connections: HashMap<String, Set<Connection>> = hashMapOf()
    }
}

fun Application.configureSockets() {

    val configPath = System.getenv("CONFIG_PATH")
    val config = ApplicationConfig(configPath)
    val ping = config.property("websocket.pingPeriod").getString().toLong()
    val time = config.property("websocket.timeout").getString().toLong()
    val mask = config.property("websocket.masking").getString().toBoolean()

    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(ping)
        timeout = Duration.ofSeconds(time)
        maxFrameSize = Long.MAX_VALUE
        masking = mask
    }
    routing {
        val path = System.getenv("CONFIG_PATH")
        val envConfig = ApplicationConfig(path)

        val channels = envConfig.tryGetStringList("channels")?.toTypedArray()

        channels?.forEach { it ->
            val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
            webSocket("/$it") {
                val thisConnection = Connection(this)
                connections += thisConnection
                SocketConnection.connections["$it"] = connections
                try {
                    send("You are connected! There are ${connections.count()} users here.")
                    for (frame in incoming) {
                        frame as? Frame.Text ?: continue
                        val receivedText = frame.readText()
                        val textWithUsername = "[${thisConnection.name}]: $receivedText"
                        connections.forEach {
                            it.session.send(textWithUsername)
                        }
                    }
                } catch (e: Exception) {
                    println(e.localizedMessage)
                } finally {
                    println("Removing $thisConnection!")
                    connections -= thisConnection
                }
            }

        }

    }
}