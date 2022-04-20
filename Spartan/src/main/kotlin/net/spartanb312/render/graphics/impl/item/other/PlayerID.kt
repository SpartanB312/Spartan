package net.spartanb312.render.graphics.impl.item.other

import net.minecraft.client.Minecraft
import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.core.common.graphics.ColorUtils.ITALIC
import net.spartanb312.render.features.manager.FontManager
import net.spartanb312.render.features.manager.TextureManager.drawTexture
import net.spartanb312.render.graphics.impl.structure.Component
import net.spartanb312.render.graphics.api.texture.DrawTexture
import net.spartanb312.render.graphics.api.texture.MipmapTexture
import net.spartanb312.render.graphics.impl.GLStateManager
import net.spartanb312.render.graphics.impl.Render2DScope
import net.spartanb312.render.graphics.impl.drawRect
import net.spartanb312.render.graphics.impl.legacy.Legacy2DRenderer
import net.spartanb312.render.graphics.impl.matrix

class PlayerID(
    var name: String,
    var id: String,
    var iconPicture: IconDisplay,
    var basePicture: MipmapTexture,
    var mainPicture: DrawTexture,
    var scale: Float = 1.0f,
    override var x: Float = 0f,
    override var y: Float = 0f,
    override var height: Float = 400f,
    override var width: Float = 400f,
) : Component {

    fun draw(
        x: Float,
        y: Float,
        mouseX: Int,
        mouseY: Int,
        partialTicks: Float,
        iconPicture: IconDisplay = this.iconPicture,
        basePicture: MipmapTexture = this.basePicture,
        mainPicture: DrawTexture = this.mainPicture,
        scale: Float = 1.0f,
    ) {
        this.x = x
        this.y = y
        this.iconPicture = iconPicture
        this.basePicture = basePicture
        this.mainPicture = mainPicture
        this.scale = scale
        onRender(mouseX, mouseY, partialTicks)
    }

    fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        matrix {
            //Cache it
            val scale = scale
            val scaledWidth = width * scale
            val scaledHeight = height * scale

            basePicture.drawTexture(x, y, x + scaledWidth, y + scaledHeight, ColorRGB.WHITE)

            //NameTag
            val nameTagAlpha = 220

            //Main
            val rectX = x
            val rectY = y + scaledHeight * 0.4f
            val rectEndX = x + scaledWidth
            val rectEndY = y + scaledHeight * 0.6f
            mainPicture.draw(rectX, rectY, rectEndX, rectEndY, ColorRGB.WHITE.alpha(nameTagAlpha))
            //Icon
            val iconX = x + scaledHeight * 0.055f
            val iconY = y + scaledHeight * 0.405f // +0.005
            val iconEndX = x + scaledHeight * 0.24f
            val iconEndY = y + scaledHeight * 0.59f // -0.01
            iconPicture.display(iconX, iconY, iconEndX, iconEndY, ColorRGB.WHITE.alpha(nameTagAlpha))
            //EXP
            val rate = 0.7f
            val xpY = y + scaledHeight * 0.59f // -0.01
            val xpEndY = y + scaledHeight * 0.6f
            Legacy2DRenderer.drawRect(rectX, xpY, rectEndX, xpEndY, ColorRGB.DARK_GRAY.alpha(nameTagAlpha))
            Legacy2DRenderer.drawRect(rectX, xpY, rectX + scaledWidth * rate, xpEndY, ColorRGB.AQUA.alpha(nameTagAlpha))
            GLStateManager.cull(false)
            //Name
            FontManager.infoFont.drawString(
                name,
                rectX + scaledWidth * 0.3f,
                rectY + scaledHeight * 0.02f,
                ColorRGB.WHITE.alpha(nameTagAlpha),
                scale = scale * 0.4f
            )
            //ID
            FontManager.infoFont.drawString(
                ITALIC + id,
                rectX + scaledWidth * 0.3f,
                rectY + scaledHeight * 0.02f + FontManager.infoFont.getHeight(scale * 0.4f),
                ColorRGB.GRAY.alpha(nameTagAlpha),
                scale = scale * 0.3f
            )
        }
        GLStateManager.texture2d(false)
    }

    override fun Render2DScope.onRender() {
        onRender(this.mouseX, this.mouseY, Minecraft.getMinecraft().renderPartialTicks)
    }

    override fun onMouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int): Boolean {
        return false
    }

}