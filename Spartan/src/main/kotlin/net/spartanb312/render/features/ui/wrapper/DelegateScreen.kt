package net.spartanb312.render.features.ui.wrapper

import net.spartanb312.render.core.common.delegate.AsyncUpdateValue
import net.spartanb312.render.core.event.inner.listener
import net.spartanb312.render.features.event.client.GameLoopEvent

class DelegateScreen(block: () -> ScreenRenderer) : SpartanScreen() {

    override val screenRenderer: ScreenRenderer by AsyncUpdateValue(block).also { value ->
        listener<GameLoopEvent>(true) {
            value.update()
        }
    }

}