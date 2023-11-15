package websocket

import io.ktor.server.application.*
//import io.ktor.server.config.*
import websocket.plugins.*


fun main(args: Array<String>) {
//    val config = ApplicationConfig("application.yaml")
//    val test = config.property("ktor.test").getString()
//    println(test)
    io.ktor.server.cio.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureSockets()
    configureRouting()
}
