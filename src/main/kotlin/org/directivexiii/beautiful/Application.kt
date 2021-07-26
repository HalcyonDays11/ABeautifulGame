package org.directivexiii.beautiful

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.thymeleaf.ThymeleafContent
import org.directivexiii.beautiful.plugins.PrometheusRegistry
import org.directivexiii.beautiful.plugins.configureMonitoring
import org.directivexiii.beautiful.plugins.configureSerialization
import org.directivexiii.beautiful.plugins.configureTemplating

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)


@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    configureTemplating()
    configureMonitoring()
    configureSerialization()

    routing {
        static("css") { resources("static/css") }
        static("img") { resources("static/img") }
        static("js") { resources("static/js") }

        get("/") {
            call.respond(ThymeleafContent("index", mapOf()))
        }

        get("/prometheus/metrics") {
            call.respond(PrometheusRegistry.appRegistry.scrape())

        }
    }
}
