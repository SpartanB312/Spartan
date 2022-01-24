package net.spartanb312.render.graphics.api.font.renderer

import net.spartanb312.render.core.common.graphics.ColorRGB

/**
 * Font system is from TrollHack
 * Credit : xiaro
 */
interface IFontRenderer {

    fun drawString(
        charSequence: CharSequence,
        posX: Float = 0.0f,
        posY: Float = 0.0f,
        color: ColorRGB = ColorRGB(255, 255, 255),
        scale: Float = 1.0f,
        drawShadow: Boolean = true
    )

    fun getHeight(): Float {
        return getHeight(1.0f)
    }

    fun getHeight(scale: Float): Float

    fun getWidth(text: CharSequence): Float {
        return getWidth(text, 1.0f)
    }

    fun getWidth(text: CharSequence, scale: Float): Float

    fun getWidth(char: Char): Float {
        return getWidth(char, 1.0f)
    }

    fun getWidth(char: Char, scale: Float): Float

}