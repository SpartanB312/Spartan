package net.spartanb312.render.features.ui.item.button

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.spartanb312.render.core.common.extension.ceilToInt
import net.spartanb312.render.core.common.extension.floorToInt
import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.core.common.timming.TickTimer
import net.spartanb312.render.core.common.timming.passedAndRun
import net.spartanb312.render.core.graphics.ConvergeUtil.converge
import net.spartanb312.render.features.manager.FontManager
import net.spartanb312.render.features.ui.item.SpecialItem
import net.spartanb312.render.features.ui.item.font.EffectFont
import net.spartanb312.render.features.ui.structure.Component
import net.spartanb312.render.graphics.impl.*
import net.spartanb312.render.graphics.impl.legacy.Legacy2DRenderer
import net.spartanb312.render.graphics.impl.legacy.vertex.VertexBuffer.begin
import org.lwjgl.opengl.GL11.GL_QUADS
import org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN

sealed class EffectButton(
    var action: (EffectButton.(mouseX: Int, mouseY: Int, button: Int) -> Boolean)? = null,
) : SpecialItem, Component {

    class RectWavePulseOffsetButton(
        var word: String,
        override var x: Float = 0f,
        override var y: Float = 0f,
        override var width: Float = 0f,
        override var height: Float = 0f,
        var pulseDirection: PulseDirection = PulseDirection.Right,
        var updateTime: Int = 3000,
        action: (EffectButton.(mouseX: Int, mouseY: Int, button: Int) -> Boolean)? = null,
    ) : EffectButton(action) {

        private var scale = 0f
        private val effectFont = EffectFont.Hoover(FontManager.infoFont)
        private var lastFrameHoovered = false

        private var rate = if (pulseDirection == PulseDirection.Right) -50f else 150f
        private val centerTimer = TickTimer()

        fun draw(x: Float, y: Float, scale: Float, mouseX: Int, mouseY: Int, partialTicks: Float) {
            this.x = x
            this.y = y
            this.scale = scale
            onRender(mouseX, mouseY, partialTicks)
        }

        override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
            val isHoovered = isHoovered(mouseX, mouseY)
            lastFrameHoovered = if (isHoovered) {
                if (!lastFrameHoovered) {
                    centerTimer.reset()
                    rate = if (pulseDirection == PulseDirection.Right) -50f else 150f
                }
                true
            } else false

            this.width = FontManager.infoFont.getWidth(word, scale) + 32f * scale
            this.height = FontManager.infoFont.getHeight(scale) + 4f * scale

            rate = rate.converge(if (pulseDirection == PulseDirection.Right) 150f else -50f, 0.045f)

            centerTimer.passedAndRun(updateTime) {
                rate = if (pulseDirection == PulseDirection.Right) -50f else 150f
            }

            if (isHoovered) {
                val endX = x + width
                val endY = y + height

                val waveCenterX = x + width * rate / 100f
                val waveStartX = (waveCenterX - width * 0.2f)
                val waveEndX = (waveCenterX + width * 0.2f)

                val color = ColorRGB(255, 255, 255, 32)
                val edgeColor = ColorRGB(255, 255, 255, 0)
                val centerColor = ColorRGB(255, 255, 255, 96)
                GLStateManager.useProgram(0)

                scissor(x.ceilToInt(), y.ceilToInt(), endX.floorToInt(), endY.floorToInt()) {

                    GlStateManager.tryBlendFuncSeparate(
                        GlStateManager.SourceFactor.SRC_ALPHA,
                        GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                        GlStateManager.SourceFactor.ONE,
                        GlStateManager.DestFactor.ZERO
                    )

                    GL_QUADS.begin {
                        //Background
                        put(endX, y, color)
                        put(x, y, color)
                        put(x, endY, color)
                        put(endX, endY, color)

                        //Wave left
                        put(waveCenterX, y, centerColor)
                        put(waveStartX, y, edgeColor)
                        put(waveStartX, endY, edgeColor)
                        put(waveCenterX, endY, centerColor)

                        //Wave right
                        put(waveEndX, y, edgeColor)
                        put(waveCenterX, y, centerColor)
                        put(waveCenterX, endY, centerColor)
                        put(waveEndX, endY, edgeColor)
                    }

                }

            }

            effectFont.draw(
                word = word,
                x = x + 4f * scale,
                y = y,
                offset = 12f * scale,
                xRate = 1f,
                isHoovered = isHoovered,
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
            rate = if (pulseDirection == PulseDirection.Right) -50f else 150f
            effectFont.reset()
        }

        enum class PulseDirection {
            Left,
            Right
        }

    }

    class MouseFollowOffsetButton(
        var word: String,
        override var x: Float = 0f,
        override var y: Float = 0f,
        override var width: Float = 0f,
        override var height: Float = 0f,
        action: (EffectButton.(mouseX: Int, mouseY: Int, button: Int) -> Boolean)? = null,
    ) : EffectButton(action) {

        private var scale = 0f
        private val effectFont = EffectFont.Hoover(FontManager.infoFont)

        fun draw(x: Float, y: Float, scale: Float, mouseX: Int, mouseY: Int, partialTicks: Float) {
            this.x = x
            this.y = y
            this.scale = scale
            onRender(mouseX, mouseY, partialTicks)
        }

        override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
            val isHoovered = isHoovered(mouseX, mouseY)
            this.width = FontManager.infoFont.getWidth(word, scale) + 32f * scale
            this.height = FontManager.infoFont.getHeight(scale) + 4f * scale

            if (isHoovered) {
                val endX = x + width
                val endY = y + height
                val radius = width * 0.3f
                GLStateManager.useProgram(0)
                scissor(x.ceilToInt(), y.ceilToInt(), endX.floorToInt(), endY.floorToInt()) {

                    GlStateManager.tryBlendFuncSeparate(
                        GlStateManager.SourceFactor.SRC_ALPHA,
                        GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                        GlStateManager.SourceFactor.ONE,
                        GlStateManager.DestFactor.ZERO
                    )

                    //Background
                    Legacy2DRenderer.drawRect(x, y, endX, endY, ColorRGB(255, 255, 255, 32))

                    //Circle
                    GL_TRIANGLE_FAN.begin {
                        put(mouseX.toFloat(), mouseY.toFloat(), ColorRGB(255, 255, 255, 96))
                        val vertexes = Renderer2D.getArcVertices(mouseX.toDouble(), mouseY.toDouble(), radius)
                        for (v in vertexes) {
                            put(v, ColorRGB(255, 255, 255, 0))
                        }
                    }

                    GLStateManager.resetColor()

                }

            }

            effectFont.draw(
                word = word,
                x = x + 4f * scale,
                y = y,
                offset = 12f * scale,
                xRate = 1f,
                isHoovered = isHoovered,
                scale = scale,
                shadow = false,
                baseColor = ColorRGB(220, 220, 220, 220),
                nextColor = ColorRGB(255, 255, 255, 255),
                speed = 0.045f,
                updateTime = 16
            )
        }

        override fun reset() {
            effectFont.reset()
        }

    }

    abstract fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float)

    final override fun Render2DScope.onRender() {
        onRender(this.mouseX, this.mouseY, Minecraft.getMinecraft().renderPartialTicks)
    }

    override fun onMouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int): Boolean =
        action?.invoke(this, mouseX, mouseY, mouseButton) ?: false

}