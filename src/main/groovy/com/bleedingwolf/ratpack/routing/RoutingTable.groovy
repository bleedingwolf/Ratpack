package com.bleedingwolf.ratpack.routing

import com.bleedingwolf.ratpack.RatpackRequestDelegate


class RoutingTable {

    def routeHandlers = []
    
    def attachRoute(route, handler) {
        routeHandlers << [route: route, handler: handler]
    }
    
    def route(subject) {
        def found = routeHandlers.find { null != it.route.match(subject) }
        def newHandler = found.handler.clone()

        if (found) {
            def urlparams = found.route.match(subject)
            def foundHandler = { ->            
                newHandler.delegate = delegate
                newHandler()
            }
            foundHandler.delegate = new RatpackRequestDelegate()
            foundHandler.delegate.urlparams = urlparams
            return foundHandler
        }
        return null
    }
}
