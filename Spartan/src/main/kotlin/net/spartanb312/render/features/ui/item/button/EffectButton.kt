package net.spartanb312.render.features.ui.item.button

import net.minecraft.client.renderer.GlStateManager
import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.core.common.timming.TickTimer
import net.spartanb312.render.core.common.timming.passedAndRun
import net.spartanb312.render.core.graphics.ConvergeUtil.converge
import net.spartanb312.render.features.manager.FontManager
import net.spartanb312.render.features.ui.item.SpecialItem
import net.spartanb312.render.features.ui.item.font.EffectFont
import net.spartanb312.render.features.ui.structure.Component
import net.spartanb312.render.graphics.impl.GLStateManager
import net.spartanb312.render.graphics.impl.legacy.vertex.VertexBuffer
import net.spartanb312.render.graphics.impl.scissor
import org.lwjgl.opengl.GL11

sealed class EffectButton(
    var action: (EffectButton.(mouseX: Int, mouseY: Int, button: Int) -> Boolean)? = null
) : SpecialItem, Component {

    class RectWavePulseButton(
        var word: String,
        override var x: Float = 0f,
        override var y: Float = 0f,
        override var width: Float = 0f,
        override var height: Float = 0f,
        var pauseDirection: PulseDirection = PulseDirection.Right,
        var updateTime: Int = 3000,
        action: (EffectButton.(mouseX: Int, mouseY: Int, button: Int) -> Boolean)? = null
    ) : EffectButton(action) {

        private var scale = 0f
        private val effectFont = EffectFont.Hoover(FontManager.infoFont)

        private var rate = -50f
        private val centerTimer = TickTimer()

        fun draw(x: Float, y: Float, scale: Float, mouseX: Int, mouseY: Int, partialTicks: Float) {
            this.x = x
            this.y = y
            this.scale = scale
            onRender(mouseX, mouseY, partialTicks)
        }

        override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
            this.width = FontManager.infoFont.getWidth(word, scale) + 32f * scale
            this.height = FontManager.infoFont.getHeight(scale) + 4f * scale

            rate = rate.converge(150f, 0.045f)

            centerTimer.passedAndRun(updateTime) {
                rate = -50f
            }

            if (isHoovered(mouseX, mouseY)) {
                val endX = x + width
                val endY = y + height

                val waveCenterX = x + width * rate / 100f
                val waveStartX = (waveCenterX - width * 0.2f)
                val waveEndX = (waveCenterX + width * 0.2f)

                val color = ColorRGB(255, 255, 255, 32)
                val edgeColor = ColorRGB(255, 255, 255, 0)
                val centerColor = ColorRGB(255, 255, 255, 96)
                GLStateManager.useProgram(0)

                scissor(x.toInt(), y.toInt(), endX.toInt(), endY.toInt()) {

                    GlStateManager.tryBlendFuncSeparate(
                        GlStateManager.SourceFactor.SRC_ALPHA,
                        GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                        GlStateManager.SourceFactor.ONE,
                        GlStateManager.DestFactor.ZERO
                    )

                    VertexBuffer.begin(GL11.GL_QUADS)

                    //Background
                    VertexBuffer.put(endX, y, color)
                    VertexBuffer.put(x, y, color)
                    VertexBuffer.put(x, endY, color)
                    VertexBuffer.put(endX, endY, color)

                    //Wave left
                    VertexBuffer.put(waveCenterX, y, centerColor)
                    VertexBuffer.put(waveStartX, y, edgeColor)
                    VertexBuffer.put(waveStartX, endY, edgeColor)
                    VertexBuffer.put(waveCenterX, endY, centerColor)

                    //Wave right
                    VertexBuffer.put(waveEndX, y, edgeColor)
                    VertexBuffer.put(waveCenterX, y, centerColor)
                    VertexBuffer.put(waveCenterX, endY, centerColor)
                    VertexBuffer.put(waveEndX, endY, edgeColor)

                    VertexBuffer.end()
                }

            }

            effectFont.draw(
                word = word,
                x = x + 4f * scale,
                y = y,
                offset = 12f * scale,
                xRate = 1f,
                mouseX = mouseX,
                mouseY = mouseY,
                scale = scale,
                shadow = false,
                baseColor = ColorRGB(220, 220, 220, 220),
                nextColor = ColorRGB(255, 255, 255, 255),
                speed = 0.045f,
                updateTime = 16
            )
        }

        override fun reset() {
            centerTimer.reset()
            rate = -50f
            effectFont.reset()
        }

        enum class PulseDirection {
            Left,
            Right
        }

    }

    abstract fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float)

    override fun onMouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int): Boolean =
        action?.invoke(this, mouseX, mouseY, mouseButton) ?: false

}