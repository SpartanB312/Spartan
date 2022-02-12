package net.spartanb312.render.features.ui.wrapper

import net.minecraft.client.gui.GuiScreen

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

    fun GuiScreen.onKeyInput() {
    }

    fun onMouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
    }

    fun onMouseReleased(mouseX: Int, mouseY: Int, state: Int) {
    }

    fun onMouseMove(mouseX: Int, mouseY: Int, clickedMouseButton: Int, timeSinceLastClick: Long) {
    }

    fun GuiScreen.onMouseInput() {
    }

}