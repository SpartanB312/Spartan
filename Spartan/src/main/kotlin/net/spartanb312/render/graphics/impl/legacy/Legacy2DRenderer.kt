package net.spartanb312.render.graphics.impl.legacy

import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.core.common.math.Vec2d
import net.spartanb312.render.graphics.api.plane.I2DRenderer
import net.spartanb312.render.graphics.impl.GLStateManager
import net.spartanb312.render.graphics.impl.Renderer2D
import net.spartanb312.render.graphics.impl.legacy.vertex.VertexBuffer
import org.lwjgl.opengl.GL11.*

/**
 * Render in compatibility mode
 * Support since OpenGL1.1
 */
object Legacy2DRenderer : I2DRenderer {

    override fun drawPoint0(x: Double, y: Double, size: Float, color: ColorRGB) {
        GLStateManager.pointSmooth(true)
        glPointSize(size)
        VertexBuffer.begin(GL_POINT)
        VertexBuffer.put(x, y, color)
        VertexBuffer.end()
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
        VertexBuffer.begin(GL_LINE)
        VertexBuffer.put(startX, startY, color1)
        VertexBuffer.put(endX, endY, color2)
        VertexBuffer.end()
        GLStateManager.lineSmooth(false)
    }

    override fun drawLinesStrip0(vertexArray: Array<Vec2d>, width: Float, color: ColorRGB) {
        GLStateManager.lineSmooth(true)
        glLineWidth(width)
        VertexBuffer.begin(GL_LINE_STRIP)
        for (v in vertexArray) {
            VertexBuffer.put(v.x, v.y, color)
        }
        VertexBuffer.end()
        GLStateManager.lineSmooth(false)
    }

    override fun drawLinesLoop0(vertexArray: Array<Vec2d>, width: Float, color: ColorRGB) {
        GLStateManager.lineSmooth(true)
        glLineWidth(width)
        VertexBuffer.begin(GL_LINE_LOOP)
        for (v in vertexArray) {
            VertexBuffer.put(v.x, v.y, color)
        }
        VertexBuffer.end()
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
        VertexBuffer.begin(GL_TRIANGLES)
        VertexBuffer.put(pos1X, pos1Y, color)
        VertexBuffer.put(pos2X, pos2Y, color)
        VertexBuffer.put(pos3X, pos3Y, color)
        VertexBuffer.end()
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
        VertexBuffer.begin(GL_LINE_STRIP)
        VertexBuffer.put(pos1X, pos1Y, color)
        VertexBuffer.put(pos2X, pos2Y, color)
        VertexBuffer.put(pos3X, pos3Y, color)
        VertexBuffer.end()
        GLStateManager.lineSmooth(false)
    }

    override fun drawRect0(startX: Double, startY: Double, endX: Double, endY: Double, color: ColorRGB) {
        VertexBuffer.begin(GL_TRIANGLE_STRIP)
        VertexBuffer.put(endX, startY, color)
        VertexBuffer.put(startX, startY, color)
        VertexBuffer.put(endX, endY, color)
        VertexBuffer.put(startX, endY, color)
        VertexBuffer.end()
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
        VertexBuffer.begin(GL_QUADS)
        VertexBuffer.put(endX, startY, color1)
        VertexBuffer.put(startX, startY, color2)
        VertexBuffer.put(startX, endY, color3)
        VertexBuffer.put(endX, endY, color4)
        VertexBuffer.end()
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
        VertexBuffer.begin(GL_LINE_LOOP)
        VertexBuffer.put(endX, startY, color1)
        VertexBuffer.put(startX, startY, color2)
        VertexBuffer.put(startX, endY, color3)
        VertexBuffer.put(endX, endY, color4)
        VertexBuffer.end()
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
        VertexBuffer.begin(GL_TRIANGLE_FAN)
        for (v in arcVertices) {
            VertexBuffer.put(v.x, v.y, color)
        }
        VertexBuffer.end()
    }

}