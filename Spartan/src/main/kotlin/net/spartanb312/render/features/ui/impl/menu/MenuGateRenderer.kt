package net.spartanb312.render.features.ui.impl.menu

import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.core.common.timming.TickTimer
import net.spartanb312.render.core.common.timming.passedAndRun
import net.spartanb312.render.core.graphics.ConvergeUtil.converge
import net.spartanb312.render.features.manager.FontManager
import net.spartanb312.render.features.manager.ResolutionHelper
import net.spartanb312.render.features.setting.ui.Background
import net.spartanb312.render.features.ui.DisplayManager
import net.spartanb312.render.features.ui.DisplayManager.displayRenderer
import net.spartanb312.render.features.ui.wrapper.ScreenRenderer
import net.spartanb312.render.graphics.impl.scissor
import kotlin.math.min

/**
 * 1st screen
 */
object MenuGateRenderer : ScreenRenderer {

    //TODO:make an account system
    var isLoggedIn = false

    override fun onInit() {
        if (isLoggedIn) DisplayManager.Renderers.MAIN_MENU.displayRenderer()
        Background.resetBackground()
        resetAnimation()
    }

    private val updateTimer = TickTimer()
    private var start = 0F
    private var end = 0F

    private fun resetAnimation() {
        start = 0F
        end = 0F
    }

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        Background.drawBackground()
        val scale = min(ResolutionHelper.scaledWidth / 2560F, ResolutionHelper.scaledHeight / 1440F) * 1.3f
        FontManager.haloFont.drawCenteredString(
            "HALO",
            ResolutionHelper.scaledWidth / 2f,
            ResolutionHelper.scaledHeight * 0.8f / 2f,
            ColorRGB(80, 230, 255),
            scale
        )
        val width = FontManager.haloFont.getWidth("HALO", scale) + 2
        start = start.converge(width, 0.045F)
        end = end.converge(width, 0.065F)
        updateTimer.passedAndRun(3000) {
            resetAnimation()
        }
        val startX = ResolutionHelper.scaledWidth / 2f - width / 2
        scissor(startX + start, 0f, startX + end, ResolutionHelper.scaledHeight.toFloat()) {
            FontManager.haloFont.drawCenteredString(
                "HALO",
                ResolutionHelper.scaledWidth / 2f,
                ResolutionHelper.scaledHeight * 0.8f / 2f,
                ColorRGB(255, 255, 255, 200),
                scale
            )
        }
    }

    override fun onMouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {

    }

    override fun onMouseReleased(mouseX: Int, mouseY: Int, state: Int) {

    }

}