package net.spartanb312.render.features.ui.wrapper

import kotlin.reflect.KProperty

class WrappedScreen(rendererIn: ScreenRenderer) : SpartanScreen() {

    var renderer = rendererIn; private set
    override val screenRenderer get() = renderer

    operator fun getValue(thisRef: Any?, property: KProperty<*>): WrappedScreen = this

    fun setAndUse(screenRenderer: ScreenRenderer): WrappedScreen {
        renderer = screenRenderer
        mc.displayGuiScreen(this)
        return this
    }

    fun set(screenRenderer: ScreenRenderer): WrappedScreen {
        renderer = screenRenderer
        return this
    }

}