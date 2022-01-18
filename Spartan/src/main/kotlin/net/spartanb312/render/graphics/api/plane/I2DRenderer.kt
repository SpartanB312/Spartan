package net.spartanb312.render.graphics.api.plane

import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.core.common.math.Vec2d

interface I2DRenderer {

    fun drawPoint0(
        x: Double,
        y: Double,
        size: Float = 1F,
        color: ColorRGB
    )

    fun drawLine0(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        width: Float = 1F,
        color1: ColorRGB,
        color2: ColorRGB
    )

    fun drawLinesStrip0(
        vertexArray: Array<Vec2d>,
        width: Float = 1F,
        color: ColorRGB
    )

    fun drawLinesLoop0(
        vertexArray: Array<Vec2d>,
        width: Float = 1F,
        color: ColorRGB
    )

    fun drawTriangle0(
        pos1X: Double,
        pos1Y: Double,
        pos2X: Double,
        pos2Y: Double,
        pos3X: Double,
        pos3Y: Double,
        color: ColorRGB
    )

    fun drawTriangleOutline0(
        pos1X: Double,
        pos1Y: Double,
        pos2X: Double,
        pos2Y: Double,
        pos3X: Double,
        pos3Y: Double,
        width: Float = 1F,
        color: ColorRGB
    )

    fun drawRect0(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        color: ColorRGB
    )

    fun drawGradientRect0(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        color1: ColorRGB,
        color2: ColorRGB,
        color3: ColorRGB,
        color4: ColorRGB
    )

    fun drawRectOutline0(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        width: Float = 1F,
        color1: ColorRGB,
        color2: ColorRGB = color1,
        color3: ColorRGB = color1,
        color4: ColorRGB = color1
    )

    fun drawRoundedRect0(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        radius: Float,
        segments: Int = 0,
        color: ColorRGB
    )

    fun drawRoundedRectOutline0(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        radius: Float,
        width: Float = 1F,
        segments: Int = 0,
        color: ColorRGB
    )

    fun drawArc0(
        centerX: Double,
        centerY: Double,
        radius: Float,
        angleRange: Pair<Float, Float>,
        segments: Int = 0,
        color: ColorRGB
    )

}