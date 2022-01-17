package net.spartanb312.render.features.event.client

import net.spartanb312.render.core.event.Event
import net.spartanb312.render.core.event.Stage

sealed class GameLoopEvent : Event() {
    object Pre : GameLoopEvent() {
        override var stage = Stage.PRE
    }

    object Post : GameLoopEvent() {
        override var stage = Stage.POST
    }
}
