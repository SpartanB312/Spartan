package net.spartanb312.render.graphics.impl.vao

import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.core.common.math.Vec2d
import net.spartanb312.render.graphics.api.plane.I2DRenderer
import net.spartanb312.render.graphics.api.shader.Shader
import net.spartanb312.render.graphics.impl.GLStateManager
import net.spartanb312.render.graphics.impl.Renderer2D
import net.spartanb312.render.graphics.impl.vao.vertex.VertexObject
import net.spartanb312.render.graphics.impl.vao.vertex.useVao
import org.lwjgl.opengl.GL11.*

/**
 * Use VAO and VBO to render
 * NOTICE : It may cause some unpredictable bugs.And some old graphic card doesn't support shaders
 * Only supports OpenGL3.0+
 */
object VAO2DRenderer : I2DRenderer {

    private var vertexSize = 0

    override fun drawPoint0(x: Double, y: Double, size: Float, color: ColorRGB) {
        GLStateManager.pointSmooth(true)
        glPointSize(size)
        put(x, y, color)
        draw(GL_POINT)
        GLStateManager.pointSmooth(false)
    }

    override fun drawLine0(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        width: Float,
        color1: ColorRGB,
        color2: ColorRGB
    ) {
        GLStateManager.lineSmooth(true)
        glLineWidth(width)
        put(startX, startY, color1)
        put(endX, endY, color2)
        draw(GL_LINE)
        GLStateManager.lineSmooth(false)
    }

    override fun drawLinesStrip0(vertexArray: Array<Vec2d>, width: Float, color: ColorRGB) {
        GLStateManager.lineSmooth(true)
        glLineWidth(width)
        for (v in vertexArray) {
            put(v.x, v.y, color)
        }
        draw(GL_LINE_STRIP)
        GLStateManager.lineSmooth(false)
    }

    override fun drawLinesLoop0(vertexArray: Array<Vec2d>, width: Float, color: ColorRGB) {
        GLStateManager.lineSmooth(true)
        glLineWidth(width)
        for (v in vertexArray) {
            put(v.x, v.y, color)
        }
        draw(GL_LINE_LOOP)
        GLStateManager.lineSmooth(false)
    }

    override fun drawTriangle0(
        pos1X: Double,
        pos1Y: Double,
        pos2X: Double,
        pos2Y: Double,
        pos3X: Double,
        pos3Y: Double,
        color: ColorRGB
    ) {
        put(pos1X, pos1Y, color)
        put(pos2X, pos2Y, color)
        put(pos3X, pos3Y, color)
        draw(GL_TRIANGLES)
    }

    override fun drawTriangleOutline0(
        pos1X: Double,
        pos1Y: Double,
        pos2X: Double,
        pos2Y: Double,
        pos3X: Double,
        pos3Y: Double,
        width: Float,
        color: ColorRGB
    ) {
        GLStateManager.lineSmooth(true)
        glLineWidth(width)
        put(pos1X, pos1Y, color)
        put(pos2X, pos2Y, color)
        put(pos3X, pos3Y, color)
        draw(GL_LINE_STRIP)
        GLStateManager.lineSmooth(false)
    }

    override fun drawRect0(startX: Double, startY: Double, endX: Double, endY: Double, color: ColorRGB) {
        put(endX, startY, color)
        put(startX, startY, color)
        put(endX, endY, color)
        put(startX, endY, color)
        draw(GL_TRIANGLE_STRIP)
    }

    override fun drawGradientRect0(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        color1: ColorRGB,
        color2: ColorRGB,
        color3: ColorRGB,
        color4: ColorRGB
    ) {
        put(endX, startY, color1)
        put(startX, startY, color2)
        put(startX, endY, color3)
        put(endX, endY, color4)
        draw(GL_QUADS)
    }

    override fun drawRectOutline0(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        width: Float,
        color1: ColorRGB,
        color2: ColorRGB,
        color3: ColorRGB,
        color4: ColorRGB
    ) {
        GLStateManager.lineSmooth(true)
        glLineWidth(width)
        put(endX, startY, color1)
        put(startX, startY, color2)
        put(startX, endY, color3)
        put(endX, endY, color4)
        draw(GL_LINE_LOOP)
        GLStateManager.lineSmooth(false)
    }

    override fun drawRoundedRect0(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        radius: Float,
        segments: Int,
        color: ColorRGB
    ) {

    }

    override fun drawRoundedRectOutline0(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        radius: Float,
        width: Float,
        segments: Int,
        color: ColorRGB
    ) {

    }

    override fun drawArc0(
        centerX: Double,
        centerY: Double,
        radius: Float,
        angleRange: Pair<Float, Float>,
        segments: Int,
        color: ColorRGB
    ) {
        val arcVertices = Renderer2D.getArcVertices(centerX, centerY, radius, angleRange, segments)
        for (v in arcVertices) {
            put(v.x, v.y, color)
        }
        draw(GL_TRIANGLE_FAN)
    }

    @JvmStatic
    fun put(posX: Double, posY: Double, color: ColorRGB) {
        VertexObject.buffer.apply {
            putFloat(posX.toFloat())
            putFloat(posY.toFloat())
            putInt(color.rgba)
        }
        vertexSize++
    }

    @JvmStatic
    fun draw(mode: Int) {
        VertexObject.Pos2Color.uploadVertex(vertexSize)

        DrawShader.bind()
        VertexObject.Pos2Color.useVao {
            glDrawArrays(mode, 0, vertexSize)
        }

        vertexSize = 0
    }

    private object DrawShader : Shader(
        "assets/spartan/shader/vao/Pos2Color.vsh",
        "assets/spartan/shader/vao/Pos2Color.fsh"
    )

}