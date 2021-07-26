package org.directivexiii.beautiful.plugins

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.metrics.micrometer.MicrometerMetrics
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics
import io.micrometer.core.instrument.binder.jvm.JvmHeapPressureMetrics
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics
import io.micrometer.core.instrument.binder.system.ProcessorMetrics
import io.micrometer.core.instrument.binder.system.UptimeMetrics
import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry

/**
 * This is an application global container for this cloud server's Prometheus micrometer registry.
 * Anything that needs to access the instance's [PrometheusMeterRegistry] should do so by calling
 * [PrometheusRegistry.appRegistry] directly.
 */
object PrometheusRegistry {
    /**
     * The final [PrometheusMeterRegistry] for this instance.  Used by [configureMonitoring].
     */
    val appRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)
}

fun Application.configureMonitoring() {
    install(MicrometerMetrics) {
        registry = PrometheusRegistry.appRegistry
        meterBinders = listOf(
            JvmMemoryMetrics(),
            JvmGcMetrics(),
            JvmHeapPressureMetrics(),
            ProcessorMetrics(),
            UptimeMetrics()
        )
    }
}
