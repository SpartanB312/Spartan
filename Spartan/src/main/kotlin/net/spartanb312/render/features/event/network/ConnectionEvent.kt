package net.spartanb312.render.features.event.network

import net.spartanb312.render.core.event.Event

sealed class ConnectionEvent : Event() {
    object Connect : ConnectionEvent()
    object Disconnect : ConnectionEvent()
}