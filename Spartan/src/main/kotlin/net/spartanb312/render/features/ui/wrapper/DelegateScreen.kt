package net.spartanb312.render.features.ui.wrapper

import net.spartanb312.render.core.common.delegate.AsyncUpdateValue
import net.spartanb312.render.core.event.inner.listener
import net.spartanb312.render.features.event.client.GameLoopEvent
import kotlin.reflect.KProperty

class DelegateScreen(block: () -> ScreenRenderer) : SpartanScreen() {

    private val delegateRenderer = AsyncUpdateValue(block).also { value ->
        listener<GameLoopEvent.Pre>(true) {
            value.update()
        }
    }

    override val screenRenderer by delegateRenderer

    fun instantSet(screenRenderer: ScreenRenderer) = delegateRenderer.update(screenRenderer)

    operator fun getValue(thisRef: Any?, property: KProperty<*>): DelegateScreen = this

}