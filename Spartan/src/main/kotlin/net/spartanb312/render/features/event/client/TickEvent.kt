package net.spartanb312.render.features.event.client

import net.spartanb312.render.core.event.Event
import net.spartanb312.render.core.event.Stage

sealed class TickEvent : Event() {

    sealed class Client : TickEvent() {
        object Pre : Client() {
            override var stage = Stage.PRE
        }

        object Post : Client() {
            override var stage = Stage.POST
        }
    }

    sealed class Render : TickEvent() {
        object Pre : Render() {
            override var stage = Stage.PRE
        }

        object Post : Render() {
            override var stage = Stage.POST
        }
    }

    sealed class Async : TickEvent() {
        object Pre : Async() {
            override var stage = Stage.PRE
        }

        object Post : Async() {
            override var stage = Stage.POST
        }
    }

}

typealias ClientTickEvent = TickEvent.Client.Pre

typealias RenderTickEvent = TickEvent.Render.Pre

typealias AsyncTickEvent = TickEvent.Async.Pre