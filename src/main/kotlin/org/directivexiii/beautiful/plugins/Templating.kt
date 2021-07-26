package org.directivexiii.beautiful.plugins

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.thymeleaf.Thymeleaf
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

fun Application.configureTemplating() {
    install(Thymeleaf) {
        setTemplateResolver(ClassLoaderTemplateResolver().apply {
            prefix = "templates"
            suffix = ".html"
            characterEncoding = "utf-8"
        })
    }
}
