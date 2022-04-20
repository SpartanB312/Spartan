package net.spartanb312.render.features.ui.menu

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.*
import net.minecraftforge.fml.client.GuiModList
import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.features.manager.FontManager
import net.spartanb312.render.features.manager.ResolutionHelper
import net.spartanb312.render.features.manager.TextureManager
import net.spartanb312.render.features.setting.ui.Background
import net.spartanb312.render.features.ui.DisplayManager
import net.spartanb312.render.features.ui.DisplayManager.screen
import net.spartanb312.render.features.ui.menu.MainMenuRenderer.Sidebar.Companion.sidebar
import net.spartanb312.render.features.ui.menu.MainMenuRenderer.Sidebar.Companion.under
import net.spartanb312.render.graphics.impl.item.SpecialItemContentDefImpl
import net.spartanb312.render.graphics.impl.item.button.EffectButton
import net.spartanb312.render.graphics.impl.item.text.EffectText
import net.spartanb312.render.graphics.impl.item.other.PlayerID
import net.spartanb312.render.features.ui.wrapper.ScreenRenderer
import net.spartanb312.render.graphics.impl.GLStateManager
import net.spartanb312.render.graphics.impl.item.SpecialItemContent
import net.spartanb312.render.graphics.impl.item.checkClick
import net.spartanb312.render.util.mc
import kotlin.math.min

object MainMenuRenderer : ScreenRenderer, SpecialItemContentDefImpl() {

    private val logo = EffectText.Shining(FontManager.haloFont).register()

    //Sidebars
    private val mainSidebar = sidebar {
        button("Settings") { mc.displayGuiScreen(GuiOptions(screen, mc.gameSettings)) }
        button("Gameplay") { }
        button("Exit") { mc.shutdown() }
    }

    private val gameplaySidebar = sidebar {
        button("Single Player") { mc.displayGuiScreen(GuiWorldSelection(screen)) }
        button("Multi Player") { mc.displayGuiScreen(GuiMultiplayer(screen)) }
        button("Language") { mc.displayGuiScreen(GuiLanguage(screen, mc.gameSettings, mc.languageManager)) }
        button("Mods") { mc.displayGuiScreen(GuiModList(screen)) }
        button("Back") { }
    }.under(mainSidebar)

    //Player ID
    private val playerID = PlayerID(
        name = Minecraft.getMinecraft().session.username,
        id = "B312",
        iconPicture = TextureManager.PlayerIDFrame.Num_6,
        basePicture = TextureManager.PlayerIDFrame.V,
        mainPicture = TextureManager.PlayerIDFrame.FRACTURE
    )

    override fun onInit() {
        playerID.iconPicture = TextureManager.PlayerIDFrame.CCCP
        playerID.basePicture = TextureManager.PlayerIDFrame.BLUE_ARROW
        playerID.mainPicture = TextureManager.PlayerIDFrame.SACRIFICE
        resetAllItems()
        Background.resetBackground()
    }

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        GLStateManager.smooth(true)
        Background.drawBackground()
        val scale = min(ResolutionHelper.scaledWidth / 2560F, ResolutionHelper.scaledHeight / 1440F) * 1.3f
        //Head
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

        //Sidebar
        val space = FontManager.infoFont.getHeight(scale * 0.5f) * 1.2f
        val buttonStartX = ResolutionHelper.scaledWidth * 0.05f
        val buttonStartY = ResolutionHelper.scaledHeight * 0.9f - space
        mainSidebar.draw(buttonStartX, buttonStartY, mouseX, mouseY, space, partialTicks, scale, Sidebar.Direction.UP)

        //Frame
        playerID.draw(
            x = ResolutionHelper.scaledWidth * 0.95f - playerID.width * scale,
            y = ResolutionHelper.scaledHeight - playerID.height * scale,
            mouseX = mouseX,
            mouseY = mouseY,
            partialTicks = partialTicks,
            scale = scale
        )
    }

    override fun onMouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int): Boolean {
        if (onItemsMouseClicked(mouseX, mouseY, mouseButton)) return true
        return false
    }

    override fun onMouseReleased(mouseX: Int, mouseY: Int, state: Int) {

    }

    private class Sidebar {
        private val children = mutableListOf<EffectButton.MouseFollowOffsetButton>()
        var fatherSidebar: Sidebar? = null

        inner class SidebarBuilder {
            val addition = mutableListOf<EffectButton.MouseFollowOffsetButton>()
            lateinit var fatherContent: SpecialItemContent

            fun button(
                word: String,
                x: Float = 0f,
                y: Float = 0f,
                width: Float = 0f,
                height: Float = 0f,
                buttonId: Int = 0,
                block: EffectButton.() -> Unit
            ): EffectButton.MouseFollowOffsetButton =
                EffectButton.MouseFollowOffsetButton(word, x, y, width, height).also {
                    addition.add(it)
                    it.checkClick { mouseX, mouseY, button ->
                        if (isHoovered(mouseX, mouseY) && button == buttonId) {
                            block()
                            true
                        } else false
                    }
                }

            fun clear() = children.clear()

            fun bind(specialItemContent: SpecialItemContent) {
                fatherContent = specialItemContent
            }
        }

        fun draw(
            x: Float,
            y: Float,
            mouseX: Int,
            mouseY: Int,
            space: Float,
            partialTicks: Float,
            scale: Float,
            direction: Direction
        ) {
            var buttonStartY = y

            (if (direction == Direction.DOWN) children else children.reversed()).forEach {
                it.draw(
                    x = x,
                    y = buttonStartY,
                    mouseX = mouseX,
                    mouseY = mouseY,
                    scale = scale * 0.5f,
                    partialTicks = partialTicks
                )
                if (direction == Direction.UP) buttonStartY -= space
                else buttonStartY += space
            }
        }

        companion object {
            fun SpecialItemContent.sidebar(block: SidebarBuilder.() -> Unit): Sidebar = Sidebar().apply {
                SidebarBuilder().apply {
                    block()
                    children.addAll(addition)
                    bind(this@sidebar)
                    fatherContent.registered.addAll(addition)
                }
            }

            fun Sidebar.attach(block: SidebarBuilder.() -> Unit): Sidebar = this.apply {
                SidebarBuilder().apply {
                    block()
                    children.addAll(addition)
                    fatherContent.registered.addAll(addition)
                }
            }

            fun Sidebar.under(father: Sidebar): Sidebar = this.apply { fatherSidebar = father }
        }

        enum class Direction { UP, DOWN }
    }

}