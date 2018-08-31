package ui.controllers

import kotlinx.coroutines.experimental.launch
import org.apache.camel.CamelContext
import org.apache.camel.builder.RouteBuilder

fun toggleRoute(routeId: String, camelContext: CamelContext) {
    launch {
        if (camelContext.getRouteStatus(routeId).isStarted) {
            camelContext.stopRoute(routeId)
        } else {
            camelContext.startRoute(routeId)
        }
    }
}

fun addRoutes(builder: RouteBuilder, camelContext: CamelContext) {
    launch {
        camelContext.addRoutes(builder)
    }
}