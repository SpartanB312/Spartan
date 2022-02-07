package net.spartanb312.render.features.ui.item

import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.core.common.timming.TickTimer
import net.spartanb312.render.core.common.timming.passedAndRun
import net.spartanb312.render.core.graphics.ConvergeUtil.converge
import net.spartanb312.render.features.manager.ResolutionHelper
import net.spartanb312.render.graphics.api.font.renderer.AbstractFontRenderer
import net.spartanb312.render.graphics.impl.scissor

class EffectFont(private val fontRenderer: AbstractFontRenderer) {

    private var shiningStart = 0F
    private var shiningEnd = 0F
    private val shiningTimer = TickTimer()

    fun drawShining(
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
        val width = fontRenderer.getWidth(word, scale) + 2
        val posX = if (centered) x - width / 2f else x
        val posY = if (centered) y - fontRenderer.getHeight(scale) / 2f else y
        //Base
        fontRenderer.drawString(word, posX, posY, baseColor, scale, shadow)
        //Update animation
        shiningStart = shiningStart.converge(width, minSpeed)
        shiningEnd = shiningEnd.converge(width, maxSpeed)
        shiningTimer.passedAndRun(updateTime) {
            reset()
        }
        //Layer
        scissor(posX + shiningStart, 0f, posX + shiningEnd, ResolutionHelper.scaledHeight.toFloat()) {
            fontRenderer.drawString(word, posX, posY, layerColor, scale, shadow)
        }
    }

    fun reset() {
        shiningStart = 0F
        shiningEnd = 0F
    }

}