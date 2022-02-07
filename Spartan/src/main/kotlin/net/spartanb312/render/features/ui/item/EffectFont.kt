package net.spartanb312.render.features.ui.item

import net.spartanb312.render.core.common.graphics.ColorHSB
import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.core.common.timming.TickTimer
import net.spartanb312.render.core.common.timming.passedAndRun
import net.spartanb312.render.core.graphics.ConvergeUtil.converge
import net.spartanb312.render.features.manager.ResolutionHelper
import net.spartanb312.render.graphics.api.font.renderer.AbstractFontRenderer
import net.spartanb312.render.graphics.impl.scissor

sealed class EffectFont(protected val fontRenderer: AbstractFontRenderer) {

    class Shining(fontRenderer: AbstractFontRenderer) : EffectFont(fontRenderer) {

        private var shiningStart = 0F
        private var shiningEnd = 0F
        private val shiningTimer = TickTimer()

        fun draw(
            word: String,
            x: Float,
            y: Float,
            scale: Float = 1F,
            shadow: Boolean = false,
            centered: Boolean = false,
            baseColor: ColorRGB,
            layerColor: ColorRGB,
            minSpeed: Float,
            maxSpeed: Float,
            updateTime: Int
        ) {
            val width = fontRenderer.getWidth(word, scale)
            val posX = if (centered) x - width / 2f else x
            val posY = if (centered) y - fontRenderer.getHeight(scale) / 2f else y
            //Base
            fontRenderer.drawString(word, posX, posY, baseColor, scale, shadow)
            //Update animation
            shiningStart = shiningStart.converge(width + 2, minSpeed)
            shiningEnd = shiningEnd.converge(width + 2, maxSpeed)
            shiningTimer.passedAndRun(updateTime) {
                reset()
            }
            //Layer
            scissor(posX + shiningStart - 1, 0f, posX + shiningEnd - 1, ResolutionHelper.scaledHeight.toFloat()) {
                fontRenderer.drawString(word, posX, posY, layerColor, scale, shadow)
            }
        }

        fun reset() {
            shiningStart = 0F
            shiningEnd = 0F
            shiningTimer.reset()
        }

    }

    class Rainbow(fontRenderer: AbstractFontRenderer) : EffectFont(fontRenderer) {

        private var startTime = System.currentTimeMillis()

        fun draw(
            word: String,
            x: Float,
            y: Float,
            scale: Float = 1F,
            shadow: Boolean = false,
            centered: Boolean = false,
            alpha: Int = 255,
            cycle: Int,
            hueOffsetMultiplier: Float,
            saturation: Int = 255,
            brightness: Int = 255
        ) {
            val width = fontRenderer.getWidth(word, scale)
            val posX = if (centered) x - width / 2f else x
            val posY = if (centered) y - fontRenderer.getHeight(scale) / 2f else y
            var startX = posX
            val startHue = (((System.currentTimeMillis() - startTime) % cycle.toFloat()) / cycle.toFloat() * 255f).toInt()
            for (char in word) {
                val charWidth = fontRenderer.getWidth(char, scale)
                val color = ColorHSB(
                    h = (startHue + (startX + charWidth - posX) * hueOffsetMultiplier).toInt() % 255,
                    s = saturation,
                    b = brightness,
                    a = alpha
                ).toRGB()
                fontRenderer.drawString(char.toString(), startX, posY, color, scale, shadow)
                startX += charWidth
            }
        }

        fun reset() {
            startTime = System.currentTimeMillis()
        }

    }

}