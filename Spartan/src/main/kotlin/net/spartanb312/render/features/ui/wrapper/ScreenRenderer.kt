package net.spartanb312.render.features.ui.wrapper

import net.spartanb312.render.graphics.impl.interfaces.*

interface ScreenRenderer : Closable, Initializer, KeyTypeListener, MouseClickListener, MouseMoveListener,
    MouseReleaseListener, Renderer {

    fun doPause(): Boolean = false

    fun overrideMouseInput(): Boolean = false

    fun overrideKeyInput(): Boolean = false

    override fun onInit() {
    }

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
    }

    override fun onClosed() {
    }

    override fun onKeyTyped(typedChar: Char, keyCode: Int) {
    }

    override fun onMouseReleased(mouseX: Int, mouseY: Int, state: Int) {
    }

    override fun onMouseMove(mouseX: Int, mouseY: Int, clickedMouseButton: Int, timeSinceLastClick: Long) {
    }

    override fun onMouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int): Boolean = false

    fun onKeyInput() {
    }

    fun onMouseInput() {
    }

}