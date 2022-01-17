package net.spartanb312.render.features.event.client

import net.spartanb312.render.core.event.Event

sealed class InputEvent(val state: Boolean) : Event() {
    class Keyboard(val key: Int, state: Boolean) : InputEvent(state)

    class Mouse(val button: Int, state: Boolean) : InputEvent(state)
}
