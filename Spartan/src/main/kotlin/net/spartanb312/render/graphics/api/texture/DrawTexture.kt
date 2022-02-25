package net.spartanb312.render.graphics.api.texture

import net.spartanb312.render.core.common.extension.ceilToInt
import net.spartanb312.render.core.common.extension.floorToInt
import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.features.manager.TextureManager.drawTexture
import net.spartanb312.render.features.ui.item.SpecialItem
import net.spartanb312.render.graphics.api.shader.GLSLSandbox
import net.spartanb312.render.graphics.impl.GLStateManager
import net.spartanb312.render.graphics.impl.scissor
import net.spartanb312.render.util.mc
import org.lwjgl.input.Mouse

class DrawTexture : SpecialItem {

    private val isShader: Boolean
    private var shader: GLSLSandbox? = null
    private var texture: MipmapTexture? = null

    private var initTime = System.currentTimeMillis()

    constructor(shader: GLSLSandbox) {
        isShader = true
        this.shader = shader
    }

    constructor(texture: MipmapTexture) {
        isShader = false
        this.texture = texture
    }

    fun draw(
        startX: Number,
        startY: Number,
        endX: Number,
        endY: Number,
        color: ColorRGB = ColorRGB.WHITE,
    ) {
        if (isShader) {
            val shader = shader!!
            color.glColor()
            scissor(startX.toDouble().floorToInt(),
                startY.toDouble().floorToInt(),
                endX.toDouble().ceilToInt(),
                endY.toDouble().ceilToInt()) {
                val width = mc.displayWidth.toFloat()
                val height = mc.displayHeight.toFloat()
                val mouseX = Mouse.getX() - 1.0f
                val mouseY = height - Mouse.getY() - 1.0f
                GLStateManager.texture2d(true)
                GLStateManager.alpha(true)
                GLStateManager.blend(true)
                shader.render(width, height, mouseX, mouseY, initTime)
                GLStateManager.texture2d(false)
            }
            GLStateManager.resetColor()
        } else {
            texture!!.drawTexture(startX.toDouble(), startY.toDouble(), endX.toDouble(), endY.toDouble(), color)
        }
    }

    companion object {
        fun pic(path: String): DrawTexture? {
            val pic = MipmapTexture.from(path) ?: return null
            return DrawTexture(pic)
        }

        fun shader(path: String): DrawTexture? {
            val shader = try {
                GLSLSandbox(path)
            } catch (ignore: Exception) {
                return null
            }
            return DrawTexture(shader)
        }
    }

    override fun reset() {
        initTime = System.currentTimeMillis()
    }

}