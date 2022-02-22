package net.spartanb312.render.features.ui.wrapper

interface ScreenRenderer {

    fun doPause(): Boolean = false

    fun overrideMouseInput(): Boolean = false

    fun overrideKeyInput(): Boolean = false

    fun onInit() {
    }

    fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
    }

    fun onClosed() {
    }

    fun onKeyTyped(typedChar: Char, keyCode: Int) {
    }

    fun onKeyInput() {
    }

    fun onMouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
    }

    fun onMouseReleased(mouseX: Int, mouseY: Int, state: Int) {
    }

    fun onMouseMove(mouseX: Int, mouseY: Int, clickedMouseButton: Int, timeSinceLastClick: Long) {
    }

    fun onMouseInput() {
    }

}