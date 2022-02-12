package net.spartanb312.render.features.ui.wrapper

import net.minecraft.client.gui.GuiScreen

/**
 * Wrapped screen renderer
 * Make it possible to migrate to another version
 */
abstract class SpartanScreen : GuiScreen() {

    abstract val screenRenderer: ScreenRenderer

    final override fun doesGuiPauseGame(): Boolean = screenRenderer.doPause()

    final override fun initGui() =
        screenRenderer.onInit()

    final override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) =
        screenRenderer.onRender(mouseX, mouseY, partialTicks)

    final override fun onGuiClosed() =
        screenRenderer.onClosed()

    final override fun keyTyped(typedChar: Char, keyCode: Int) =
        screenRenderer.onKeyTyped(typedChar, keyCode)

    final override fun handleKeyboardInput() {
        if (screenRenderer.overrideKeyInput()) with(screenRenderer) {
            onKeyInput()
        } else super.handleKeyboardInput()
    }

    final override fun mouseClickMove(mouseX: Int, mouseY: Int, clickedMouseButton: Int, timeSinceLastClick: Long) =
        screenRenderer.onMouseMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick)

    final override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) =
        screenRenderer.onMouseClicked(mouseX, mouseY, mouseButton)

    final override fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) =
        screenRenderer.onMouseReleased(mouseX, mouseY, state)

    final override fun handleMouseInput() {
        if (screenRenderer.overrideMouseInput()) with(screenRenderer) {
            onMouseInput()
        } else super.handleMouseInput()
    }

}