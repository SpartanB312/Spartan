package net.spartanb312.render.features.gui

import net.minecraft.client.gui.GuiScreen

/**
 * Wrapped screen renderer
 * Make it possible to migrate to another version
 */
class SpartanScreen(
    private val screenRenderer: ScreenRenderer,
    private val pause: Boolean = false
) : GuiScreen() {

    override fun doesGuiPauseGame(): Boolean = pause

    override fun initGui() =
        screenRenderer.onInit()

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) =
        screenRenderer.onRender(mouseX, mouseY, partialTicks)

    override fun onGuiClosed() =
        screenRenderer.onClosed()

    override fun keyTyped(typedChar: Char, keyCode: Int) =
        screenRenderer.onKeyTyped(typedChar, keyCode)

    override fun handleKeyboardInput() =
        screenRenderer.onKeyInput()

    override fun mouseClickMove(mouseX: Int, mouseY: Int, clickedMouseButton: Int, timeSinceLastClick: Long) =
        screenRenderer.onMouseMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick)

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) =
        screenRenderer.onMouseClicked(mouseX, mouseY, mouseButton)

    override fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) =
        screenRenderer.onMouseReleased(mouseX, mouseY, state)

    override fun handleMouseInput() =
        screenRenderer.onMouseInput()

}