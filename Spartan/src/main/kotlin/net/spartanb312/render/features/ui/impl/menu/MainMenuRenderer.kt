package net.spartanb312.render.features.ui.impl.menu

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiMultiplayer
import net.minecraft.client.gui.GuiOptions
import net.minecraft.client.gui.GuiWorldSelection
import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.features.manager.FontManager
import net.spartanb312.render.features.manager.ResolutionHelper
import net.spartanb312.render.features.manager.TextureManager
import net.spartanb312.render.features.setting.ui.Background
import net.spartanb312.render.features.ui.DisplayManager
import net.spartanb312.render.features.ui.item.SpecialItemContentDefImpl
import net.spartanb312.render.features.ui.item.button.EffectButton
import net.spartanb312.render.features.ui.item.font.EffectFont
import net.spartanb312.render.features.ui.item.other.PlayerID
import net.spartanb312.render.features.ui.item.withButton
import net.spartanb312.render.features.ui.wrapper.ScreenRenderer
import net.spartanb312.render.graphics.impl.GLStateManager
import net.spartanb312.render.util.mc
import kotlin.math.min

object MainMenuRenderer : ScreenRenderer, SpecialItemContentDefImpl() {

    private val logo = EffectFont.Shining(FontManager.haloFont).register()

    //Buttons
    private val singlePlayer = EffectButton.MouseFollowOffsetButton("Single Player").register()
        .withButton { mc.displayGuiScreen(GuiWorldSelection(DisplayManager.screen)) }
    private val multiPlayer = EffectButton.MouseFollowOffsetButton("Multi Player").register()
        .withButton { mc.displayGuiScreen(GuiMultiplayer(DisplayManager.screen)) }
    private val settings = EffectButton.MouseFollowOffsetButton("Settings").register()
        .withButton { mc.displayGuiScreen(GuiOptions(DisplayManager.screen, mc.gameSettings)) }

    //Player ID
    private val playerID = PlayerID(
        name = Minecraft.getMinecraft().session.username,
        id = "B312",
        iconPicture = TextureManager.PlayerIDFrame.Num_6,
        basePicture = TextureManager.PlayerIDFrame.V,
        mainPicture = TextureManager.PlayerIDFrame.FRACTURE
    )

    override fun onInit() {
        resetAllItems()
    }

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        GLStateManager.smooth(true)
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
        val space = FontManager.infoFont.getHeight(scale * 0.5f) * 1.2f
        val buttonStartX = ResolutionHelper.scaledWidth * 0.05f
        var buttonStartY = ResolutionHelper.scaledHeight * 0.9f - space
        multiPlayer.draw(
            x = buttonStartX,
            y = buttonStartY,
            mouseX = mouseX,
            mouseY = mouseY,
            scale = scale * 0.5f,
            partialTicks = partialTicks
        )
        buttonStartY -= space
        singlePlayer.draw(
            x = buttonStartX,
            y = buttonStartY,
            mouseX = mouseX,
            mouseY = mouseY,
            scale = scale * 0.5f,
            partialTicks = partialTicks
        )
        buttonStartY -= space
        settings.draw(
            x = buttonStartX,
            y = buttonStartY,
            mouseX = mouseX,
            mouseY = mouseY,
            scale = scale * 0.5f,
            partialTicks = partialTicks
        )
        playerID.draw(
            x = ResolutionHelper.scaledWidth * 0.95f - playerID.width * scale,
            y = ResolutionHelper.scaledHeight - playerID.height * scale,
            mouseX = mouseX,
            mouseY = mouseY,
            partialTicks = partialTicks,
            scale = scale
        )
    }

    override fun onMouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        if (onItemsMouseClicked(mouseX, mouseY, mouseButton)) return
    }

    override fun onMouseReleased(mouseX: Int, mouseY: Int, state: Int) {

    }

}