package net.spartanb312.render.features.ui.impl.menu

import net.minecraft.client.gui.GuiMultiplayer
import net.minecraft.client.gui.GuiWorldSelection
import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.features.manager.FontManager
import net.spartanb312.render.features.manager.ResolutionHelper
import net.spartanb312.render.features.setting.ui.Background
import net.spartanb312.render.features.ui.DisplayManager
import net.spartanb312.render.features.ui.item.SpecialItemContentDefImpl
import net.spartanb312.render.features.ui.item.font.EffectFont
import net.spartanb312.render.features.ui.item.withButton
import net.spartanb312.render.features.ui.wrapper.ScreenRenderer
import net.spartanb312.render.util.mc
import kotlin.math.min

object MainMenuRenderer : ScreenRenderer, SpecialItemContentDefImpl() {

    private val logo = EffectFont.Shining(FontManager.haloFont).register()
    private val singlePlayer = EffectFont.Hoover(FontManager.infoFont).register()
        .withButton { mc.displayGuiScreen(GuiWorldSelection(DisplayManager.screen)) }
    private val multiPlayer = EffectFont.Hoover(FontManager.infoFont).register()
        .withButton { mc.displayGuiScreen(GuiMultiplayer(DisplayManager.screen)) }

    override fun onInit() {
        resetAllItems()
    }

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        Background.drawBackground()
        val scale = min(ResolutionHelper.scaledWidth / 2560F, ResolutionHelper.scaledHeight / 1440F) * 1.3f
        logo.draw(
            word = "HALO",
            x = ResolutionHelper.scaledWidth * 0.05f,
            y = ResolutionHelper.scaledHeight * 0.05f,
            scale = scale * 0.7f,
            shadow = false,
            baseColor = ColorRGB(80, 160, 220),
            layerColor = ColorRGB(255, 255, 255, 200),
            minSpeed = 0.045f,
            maxSpeed = 0.065f,
            updateTime = 3000
        )
        val space = FontManager.infoFont.getHeight(scale * 0.5f)
        val startX = ResolutionHelper.scaledWidth * 0.05f
        var startY = ResolutionHelper.scaledHeight * 0.9f - space
        multiPlayer.draw(
            word = "Multi Player",
            x = startX,
            y = startY,
            offset = 2f,
            mouseX = mouseX,
            mouseY = mouseY,
            scale = scale * 0.5f,
            shadow = true,
            baseColor = ColorRGB(220, 220, 220, 220),
            nextColor = ColorRGB(255, 255, 255, 255),
            speed = 0.045f,
            updateTime = 16
        )
        startY -= space
        singlePlayer.draw(
            word = "Single Player",
            x = startX,
            y = startY,
            offset = 2f,
            mouseX = mouseX,
            mouseY = mouseY,
            scale = scale * 0.5f,
            shadow = true,
            baseColor = ColorRGB(220, 220, 220, 220),
            nextColor = ColorRGB(255, 255, 255, 255),
            speed = 0.045f,
            updateTime = 16
        )
        startY -= space

    }

    override fun onMouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        if (onItemsMouseClicked(mouseX, mouseY, mouseButton)) return
    }

    override fun onMouseReleased(mouseX: Int, mouseY: Int, state: Int) {

    }

}