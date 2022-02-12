package net.spartanb312.render.features.ui.impl.menu

import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.features.manager.FontManager
import net.spartanb312.render.features.manager.ResolutionHelper
import net.spartanb312.render.features.setting.ui.Background
import net.spartanb312.render.features.ui.DisplayManager
import net.spartanb312.render.features.ui.DisplayManager.displayRenderer
import net.spartanb312.render.features.ui.item.font.EffectFont
import net.spartanb312.render.features.ui.wrapper.ScreenRenderer
import kotlin.math.min

/**
 * 1st screen
 */
object MenuGateRenderer : ScreenRenderer {

    //TODO:make an account system
    var isLoggedIn = false

    private val logo = EffectFont.Shining(FontManager.haloFont)
    private val pressKey = EffectFont.Shining(FontManager.infoFont)

    override fun onInit() {
        if (isLoggedIn) DisplayManager.Renderers.MAIN_MENU.displayRenderer()
        logo.reset()
        pressKey.reset()
    }

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        Background.drawBackground()
        val scale = min(ResolutionHelper.scaledWidth / 2560F, ResolutionHelper.scaledHeight / 1440F) * 1.3f
        logo.draw(
            word = "HALO",
            x = ResolutionHelper.scaledWidth / 2f,
            y = ResolutionHelper.scaledHeight * 0.8f / 2f,
            scale = scale,
            shadow = false,
            centered = true,
            baseColor = ColorRGB(80, 160, 220),
            layerColor = ColorRGB(255, 255, 255, 200),
            minSpeed = 0.045f,
            maxSpeed = 0.065f,
            updateTime = 3000
        )
        pressKey.draw(
            word = "Press any key to start",
            x = ResolutionHelper.scaledWidth / 2f,
            y = ResolutionHelper.scaledHeight * 0.65f,
            scale = scale * 0.5f,
            shadow = true,
            centered = true,
            baseColor = ColorRGB(200, 200, 200, 200),
            layerColor = ColorRGB(255, 255, 255, 255),
            minSpeed = 0.045f,
            maxSpeed = 0.065f,
            updateTime = 2000
        )
    }

    override fun onMouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        isLoggedIn = true
        DisplayManager.Renderers.MAIN_MENU.displayRenderer()
    }

    override fun onKeyTyped(typedChar: Char, keyCode: Int) {
        isLoggedIn = true
        DisplayManager.Renderers.MAIN_MENU.displayRenderer()
    }

}