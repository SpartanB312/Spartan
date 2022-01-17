package net.spartanb312.render.features.event.client

import net.spartanb312.render.core.event.Event

sealed class InitEvent : Event() {
    object Tweak : InitEvent()
    object Pre : InitEvent()
    object Init : InitEvent()
    object Post : InitEvent()
    object Ready : InitEvent()
}