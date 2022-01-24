package net.spartanb312.render.graphics.api.font.setting

import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.core.common.graphics.ColorUtils
import net.spartanb312.render.graphics.api.font.renderer.ExtendedFontRenderer
import net.spartanb312.render.graphics.impl.GLStateManager
import java.awt.Font

class LinkedSettableFontRenderer(
    font: Font,
    val setting: FontSetting,
    size: Float = 64f,
    textureSize: Int = 2048
) : ExtendedFontRenderer(font, size, textureSize) {

    override val sizeMultiplier: Float
        get() = setting.actualSize

    override val baselineOffset: Float
        get() = setting.actualBaselineOffset

    override val charGap: Float
        get() = setting.actualCharGap

    override val lineSpace: Float
        get() = setting.actualLineSpace

    override val lodBias: Float
        get() = setting.actualLodBias

    override val shadowDist: Float
        get() = 5.0f

    override fun getHeight(scale: Float): Float {
        return run {
            regularGlyph.fontHeight * lineSpace * scale
        }
    }

    fun drawStringJava(string: String, posX: Float, posY: Float, color: Int, scale: Float, drawShadow: Boolean) {
        var adjustedColor = color
        if (adjustedColor and -67108864 == 0) adjustedColor = color or -16777216
        GLStateManager.alpha(false)
        drawString(string, posX, posY - 1.0f, ColorRGB(ColorUtils.argbToRgba(adjustedColor)), scale, drawShadow)
        GLStateManager.alpha(true)
        GLStateManager.useProgram(0, true)
    }

}