package net.spartanb312.render.features.ui.wrapper

import org.lwjgl.input.Keyboard
import kotlin.reflect.KProperty

class WrappedScreen(delegate: DelegateRenderer) : SpartanScreen() {

    var delegate = delegate; private set
    override val screenRenderer get() = delegate.renderer

    operator fun getValue(thisRef: Any?, property: KProperty<*>): WrappedScreen = this

    fun setAndUse(delegateIn: DelegateRenderer): WrappedScreen {
        delegate = delegateIn
        mc.displayGuiScreen(this)
        return this
    }

    fun set(delegateIn: DelegateRenderer): WrappedScreen {
        delegate = delegateIn
        return this
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        if (keyCode == Keyboard.KEY_ESCAPE && delegate.displayLast()) return
        super.keyTyped(typedChar, keyCode)
    }

}