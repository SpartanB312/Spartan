package net.spartanb312.render.features.ui.impl.menu

import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.features.manager.FontManager
import net.spartanb312.render.features.manager.ResolutionHelper
import net.spartanb312.render.features.setting.ui.Background
import net.spartanb312.render.features.ui.DisplayManager
import net.spartanb312.render.features.ui.DisplayManager.displayRenderer
import net.spartanb312.render.features.ui.item.EffectFont
import net.spartanb312.render.features.ui.wrapper.ScreenRenderer
import kotlin.math.min

/**
 * 1st screen
 */
object MenuGateRenderer : ScreenRenderer {

    //TODO:make an account system
    var isLoggedIn = false

    private val effectFont = EffectFont(FontManager.haloFont)

    override fun onInit() {
        if (isLoggedIn) DisplayManager.Renderers.MAIN_MENU.displayRenderer()
        Background.resetBackground()
        effectFont.reset()
    }

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        Background.drawBackground()
        val scale = min(ResolutionHelper.scaledWidth / 2560F, ResolutionHelper.scaledHeight / 1440F) * 1.3f
        effectFont.drawShining(
            word = "HALO",
            x = ResolutionHelper.scaledWidth / 2f,
            y = ResolutionHelper.scaledHeight * 0.8f / 2f,
            scale = scale,
            shadow = false,
            centered = true,
            baseColor = ColorRGB(80, 230, 255),
            layerColor = ColorRGB(255, 255, 255, 200),
            minSpeed = 0.045f,
            maxSpeed = 0.065f,
            updateTime = 3000
        )
    }

    override fun onMouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {

    }

    override fun onMouseReleased(mouseX: Int, mouseY: Int, state: Int) {

    }

}