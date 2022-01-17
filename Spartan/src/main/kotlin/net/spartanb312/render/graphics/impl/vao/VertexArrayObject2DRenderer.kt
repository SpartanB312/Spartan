package net.spartanb312.render.graphics.impl.vao

import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.core.common.math.Vec2d
import net.spartanb312.render.graphics.api.plane.I2DRenderer

/**
 * Use VAO and VBO to render
 * NOTICE : It may cause some unpredictable bugs.And some old graphic card doesn't support shaders
 * Only supports OpenGL3.0+
 */
object VertexArrayObject2DRenderer : I2DRenderer {

    override fun drawPoint(x: Double, y: Double, size: Float, color: ColorRGB) {
        TODO("Not yet implemented")
    }

    override fun drawLine(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        width: Float,
        color1: ColorRGB,
        color2: ColorRGB
    ) {
        TODO("Not yet implemented")
    }

    override fun drawLinesStrip(vertexArray: Array<Vec2d>, width: Float, color: ColorRGB) {
        TODO("Not yet implemented")
    }

    override fun drawLinesLoop(vertexArray: Array<Vec2d>, width: Float, color: ColorRGB) {
        TODO("Not yet implemented")
    }

    override fun drawTriangle(
        pos1X: Double,
        pos1Y: Double,
        pos2X: Double,
        pos2Y: Double,
        pos3X: Double,
        pos3Y: Double,
        color: ColorRGB
    ) {
        TODO("Not yet implemented")
    }

    override fun drawTriangleOutline(
        pos1X: Double,
        pos1Y: Double,
        pos2X: Double,
        pos2Y: Double,
        pos3X: Double,
        pos3Y: Double,
        width: Float,
        color: ColorRGB
    ) {
        TODO("Not yet implemented")
    }

    override fun drawRect(startX: Double, startY: Double, endX: Double, endY: Double, color: ColorRGB) {
        TODO("Not yet implemented")
    }

    override fun drawGradientRect(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        color1: ColorRGB,
        color2: ColorRGB,
        color3: ColorRGB,
        color4: ColorRGB
    ) {
        TODO("Not yet implemented")
    }

    override fun drawRectOutline(
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
        TODO("Not yet implemented")
    }

    override fun drawRoundedRect(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        radius: Float,
        segments: Int,
        color: ColorRGB
    ) {
        TODO("Not yet implemented")
    }

    override fun drawRoundedRectOutline(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        radius: Float,
        width: Float,
        segments: Int,
        color: ColorRGB
    ) {
        TODO("Not yet implemented")
    }

    override fun drawArc(
        centerX: Double,
        centerY: Double,
        radius: Float,
        angleRange: Pair<Float, Float>,
        segments: Int,
        color: ColorRGB
    ) {
        TODO("Not yet implemented")
    }


}