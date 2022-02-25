package net.spartanb312.render.graphics.impl

import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.GlStateManager
import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.util.Helper
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20

object GLStateManager : Helper {

    private var bindProgram = 0

    val useVbo get() = mc.gameSettings.useVbo

    fun useProgram(id: Int, force: Boolean = false) {
        if (force || id != bindProgram) {
            GL20.glUseProgram(id)
            bindProgram = id
        }
    }

    fun alpha(state: Boolean) {
        if (state) {
            GlStateManager.enableAlpha()
        } else {
            GlStateManager.disableAlpha()
        }
    }

    fun blend(state: Boolean) {
        if (state) {
            GlStateManager.enableBlend()
        } else {
            GlStateManager.disableBlend()
        }
    }

    fun smooth(state: Boolean) {
        if (state) {
            GlStateManager.shadeModel(GL11.GL_SMOOTH)
        } else {
            GlStateManager.shadeModel(GL11.GL_FLAT)
        }
    }

    fun lineSmooth(state: Boolean) {
        if (state) {
            GL11.glEnable(GL11.GL_LINE_SMOOTH)
            GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST)
        } else {
            GL11.glDisable(GL11.GL_LINE_SMOOTH)
        }
    }

    fun pointSmooth(state: Boolean) {
        if (state) {
            GL11.glEnable(GL11.GL_POINT_SMOOTH)
            GL11.glHint(GL11.GL_POINT_SMOOTH_HINT, GL11.GL_NICEST)
        } else {
            GL11.glDisable(GL11.GL_POINT_SMOOTH)
        }
    }

    fun polygonSmooth(state: Boolean) {
        if (state) {
            GL11.glEnable(GL11.GL_POLYGON_SMOOTH)
            GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_NICEST)
        } else {
            GL11.glDisable(GL11.GL_POLYGON_SMOOTH)
        }
    }

    fun depth(state: Boolean) {
        if (state) {
            GlStateManager.enableDepth()
        } else {
            GlStateManager.disableDepth()
        }
    }

    fun texture2d(state: Boolean) {
        if (state) {
            GlStateManager.enableTexture2D()
        } else {
            GlStateManager.disableTexture2D()
        }
    }

    fun cull(state: Boolean) {
        if (state) {
            GlStateManager.enableCull()
        } else {
            GlStateManager.disableCull()
        }
    }

    fun lighting(state: Boolean) {
        if (state) {
            GlStateManager.enableLighting()
        } else {
            GlStateManager.disableLighting()
        }
    }

    fun rescaleActual() {
        rescale(mc.displayWidth.toDouble(), mc.displayHeight.toDouble())
    }

    fun rescaleMc() {
        val resolution = ScaledResolution(mc)
        rescale(resolution.scaledWidth_double, resolution.scaledHeight_double)
    }

    fun pushMatrixAll() {
        GL11.glMatrixMode(GL11.GL_MODELVIEW)
        GL11.glPushMatrix()
        GL11.glMatrixMode(GL11.GL_PROJECTION)
        GL11.glPushMatrix()
    }

    fun popMatrixAll() {
        GL11.glMatrixMode(GL11.GL_PROJECTION)
        GL11.glPopMatrix()
        GL11.glMatrixMode(GL11.GL_MODELVIEW)
        GL11.glPopMatrix()
    }

    fun rescale(width: Double, height: Double) {
        GlStateManager.clear(GL11.GL_DEPTH_BUFFER_BIT)
        GlStateManager.viewport(0, 0, mc.displayWidth, mc.displayHeight)
        GlStateManager.matrixMode(GL11.GL_PROJECTION)
        GlStateManager.loadIdentity()
        GlStateManager.ortho(0.0, width, height, 0.0, 1000.0, 3000.0)
        GlStateManager.matrixMode(GL11.GL_MODELVIEW)
        GlStateManager.loadIdentity()
        GlStateManager.translate(0.0f, 0.0f, -2000.0f)
    }

    fun color(colorRGB: ColorRGB) = colorRGB.glColor()

    fun resetColor() = GL11.glColor4f(1f, 1f, 1f, 1f)


}