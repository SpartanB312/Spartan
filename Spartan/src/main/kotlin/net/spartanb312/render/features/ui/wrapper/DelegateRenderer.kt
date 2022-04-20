package net.spartanb312.render.features.ui.wrapper

import net.spartanb312.render.features.ui.DisplayManager.displayRenderer
import kotlin.reflect.KProperty

class DelegateRenderer(val renderer: ScreenRenderer, private val lastRenderer: DelegateRenderer? = null) {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): ScreenRenderer = renderer

    fun displayLast(): Boolean {
        if (lastRenderer != null) {
            renderer.onClosed()
            lastRenderer.displayRenderer()
            return true
        }
        return false
    }

}