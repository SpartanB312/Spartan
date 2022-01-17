package net.spartanb312.render.graphics.api.plane

import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.core.common.math.Vec2d

interface I2DRenderer {

    fun drawPoint(
        x: Double,
        y: Double,
        size: Float = 1F,
        color: ColorRGB
    )

    fun drawLine(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        width: Float = 1F,
        color1: ColorRGB,
        color2: ColorRGB
    )

    fun drawLinesStrip(
        vertexArray: Array<Vec2d>,
        width: Float = 1F,
        color: ColorRGB
    )

    fun drawLinesLoop(
        vertexArray: Array<Vec2d>,
        width: Float = 1F,
        color: ColorRGB
    )

    fun drawTriangle(
        pos1X: Double,
        pos1Y: Double,
        pos2X: Double,
        pos2Y: Double,
        pos3X: Double,
        pos3Y: Double,
        color: ColorRGB
    )

    fun drawTriangleOutline(
        pos1X: Double,
        pos1Y: Double,
        pos2X: Double,
        pos2Y: Double,
        pos3X: Double,
        pos3Y: Double,
        width: Float = 1F,
        color: ColorRGB
    )

    fun drawRect(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        color: ColorRGB
    )

    fun drawGradientRect(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        color1: ColorRGB,
        color2: ColorRGB,
        color3: ColorRGB,
        color4: ColorRGB
    )

    fun drawRectOutline(
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

    fun drawRoundedRect(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        radius: Float,
        segments: Int = 0,
        color: ColorRGB
    )

    fun drawRoundedRectOutline(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        radius: Float,
        width: Float = 1F,
        segments: Int = 0,
        color: ColorRGB
    )

    fun drawArc(
        centerX: Double,
        centerY: Double,
        radius: Float,
        angleRange: Pair<Float, Float>,
        segments: Int = 0,
        color: ColorRGB
    )

}