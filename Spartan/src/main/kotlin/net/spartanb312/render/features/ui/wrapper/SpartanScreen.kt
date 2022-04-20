package net.spartanb312.render.features.ui.wrapper

import net.minecraft.client.gui.GuiScreen

/**
 * Wrapped screen renderer
 * Make it possible to migrate to another version
 */
abstract class SpartanScreen : GuiScreen() {

    abstract val screenRenderer: ScreenRenderer

    override fun doesGuiPauseGame(): Boolean = screenRenderer.doPause()

    override fun initGui() =
        screenRenderer.onInit()

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) =
        screenRenderer.onRender(mouseX, mouseY, partialTicks)

    override fun onGuiClosed() =
        screenRenderer.onClosed()

    override fun keyTyped(typedChar: Char, keyCode: Int) =
        screenRenderer.onKeyTyped(typedChar, keyCode)

    override fun handleKeyboardInput() {
        if (screenRenderer.overrideKeyInput()) with(screenRenderer) {
            onKeyInput()
        } else super.handleKeyboardInput()
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        screenRenderer.onMouseClicked(mouseX, mouseY, mouseButton)
    }

    override fun mouseClickMove(mouseX: Int, mouseY: Int, clickedMouseButton: Int, timeSinceLastClick: Long) =
        screenRenderer.onMouseMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick)

    override fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) =
        screenRenderer.onMouseReleased(mouseX, mouseY, state)

    override fun handleMouseInput() {
        if (screenRenderer.overrideMouseInput()) with(screenRenderer) {
            onMouseInput()
        } else super.handleMouseInput()
    }

}