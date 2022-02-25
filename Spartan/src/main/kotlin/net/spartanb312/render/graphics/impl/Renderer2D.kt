package net.spartanb312.render.graphics.impl

import net.spartanb312.render.core.common.delegate.AsyncUpdateValue
import net.spartanb312.render.core.common.extension.map
import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.core.common.math.MathUtils
import net.spartanb312.render.core.common.math.Vec2d
import net.spartanb312.render.core.common.math.Vec2f
import net.spartanb312.render.core.event.inner.listener
import net.spartanb312.render.features.event.client.GameLoopEvent
import net.spartanb312.render.graphics.impl.legacy.Legacy2DRenderer
import net.spartanb312.render.graphics.impl.vao.VAO2DRenderer
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

object Renderer2D {

    var currentRenderMode = RenderMode.Legacy

    //delegate renderer
    var renderer by AsyncUpdateValue {
        if (currentRenderMode == RenderMode.Legacy) Legacy2DRenderer else VAO2DRenderer
    }.also { r ->
        listener<GameLoopEvent.Pre>(Int.MAX_VALUE, true) {
            r.update()
        }
    }

    enum class RenderMode {
        Legacy,
        VAO
    }

    ////--------Overloads start--------////
    /**
     * Point
     */
    @JvmStatic
    fun drawPoint(
        x: Double,
        y: Double,
        size: Float = 1F,
        color: ColorRGB,
    ) = renderer.drawPoint0(x, y, size, color)

    @JvmStatic
    fun drawPoint(
        x: Int,
        y: Int,
        size: Float = 1F,
        color: ColorRGB,
    ) = renderer.drawPoint0(x.toDouble(), y.toDouble(), size, color)

    @JvmStatic
    fun drawPoint(
        x: Float,
        y: Float,
        size: Float = 1F,
        color: ColorRGB,
    ) = renderer.drawPoint0(x.toDouble(), y.toDouble(), size, color)

    /**
     * Line
     */
    @JvmStatic
    fun drawLine(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        width: Float = 1F,
        color1: ColorRGB,
        color2: ColorRGB = color1,
    ) = renderer.drawLine0(startX, startY, endX, endY, width, color1, color2)

    @JvmStatic
    fun drawLine(
        startX: Int,
        startY: Int,
        endX: Int,
        endY: Int,
        width: Float = 1F,
        color1: ColorRGB,
        color2: ColorRGB = color1,
    ) = renderer.drawLine0(
        startX.toDouble(),
        startY.toDouble(),
        endX.toDouble(),
        endY.toDouble(),
        width,
        color1,
        color2
    )

    @JvmStatic
    fun drawLine(
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float,
        width: Float = 1F,
        color1: ColorRGB,
        color2: ColorRGB = color1,
    ) = renderer.drawLine0(
        startX.toDouble(),
        startY.toDouble(),
        endX.toDouble(),
        endY.toDouble(),
        width,
        color1,
        color2
    )

    @JvmStatic
    fun drawLine(
        start: Vec2d,
        end: Vec2d,
        width: Float = 1F,
        color1: ColorRGB,
        color2: ColorRGB = color1,
    ) = renderer.drawLine0(start.x, start.y, end.x, end.y, width, color1, color2)

    @JvmStatic
    fun drawLine(
        start: Vec2f,
        end: Vec2f,
        width: Float = 1F,
        color1: ColorRGB,
        color2: ColorRGB = color1,
    ) = renderer.drawLine0(
        start.x.toDouble(),
        start.y.toDouble(),
        end.x.toDouble(),
        end.y.toDouble(),
        width,
        color1,
        color2
    )

    @JvmStatic
    fun drawLinesStrip(
        vertexArray: Array<Vec2d>,
        width: Float = 1F,
        color: ColorRGB,
    ) = renderer.drawLinesStrip0(vertexArray, width, color)

    @JvmStatic
    fun drawLinesLoop(
        vertexArray: Array<Vec2d>,
        width: Float = 1F,
        color: ColorRGB,
    ) = renderer.drawLinesLoop0(vertexArray, width, color)

    @JvmStatic
    fun drawLinesStrip(
        vertexArray: Array<Vec2f>,
        width: Float = 1F,
        color: ColorRGB,
    ) = renderer.drawLinesStrip0(vertexArray.map { it.toVec2d() }, width, color)

    @JvmStatic
    fun drawLinesLoop(
        vertexArray: Array<Vec2f>,
        width: Float = 1F,
        color: ColorRGB,
    ) = renderer.drawLinesLoop0(vertexArray.map { it.toVec2d() }, width, color)

    /**
     * Triangle
     */
    @JvmStatic
    fun drawTriangle(
        pos1X: Double,
        pos1Y: Double,
        pos2X: Double,
        pos2Y: Double,
        pos3X: Double,
        pos3Y: Double,
        color: ColorRGB,
    ) = renderer.drawTriangle0(pos1X, pos1Y, pos2X, pos2Y, pos3X, pos3Y, color)

    @JvmStatic
    fun drawTriangle(
        pos1X: Int,
        pos1Y: Int,
        pos2X: Int,
        pos2Y: Int,
        pos3X: Int,
        pos3Y: Int,
        color: ColorRGB,
    ) = renderer.drawTriangle0(
        pos1X.toDouble(),
        pos1Y.toDouble(),
        pos2X.toDouble(),
        pos2Y.toDouble(),
        pos3X.toDouble(),
        pos3Y.toDouble(),
        color
    )

    @JvmStatic
    fun drawTriangle(
        pos1X: Float,
        pos1Y: Float,
        pos2X: Float,
        pos2Y: Float,
        pos3X: Float,
        pos3Y: Float,
        color: ColorRGB,
    ) = renderer.drawTriangle0(
        pos1X.toDouble(),
        pos1Y.toDouble(),
        pos2X.toDouble(),
        pos2Y.toDouble(),
        pos3X.toDouble(),
        pos3Y.toDouble(),
        color
    )

    @JvmStatic
    fun drawTriangle(
        pos1: Vec2d,
        pos2: Vec2d,
        pos3: Vec2d,
        color: ColorRGB,
    ) = renderer.drawTriangle0(
        pos1.x,
        pos1.y,
        pos2.x,
        pos2.y,
        pos3.x,
        pos3.y,
        color
    )

    @JvmStatic
    fun drawTriangle(
        pos1: Vec2f,
        pos2: Vec2f,
        pos3: Vec2f,
        color: ColorRGB,
    ) = renderer.drawTriangle0(
        pos1.x.toDouble(),
        pos1.y.toDouble(),
        pos2.x.toDouble(),
        pos2.y.toDouble(),
        pos3.x.toDouble(),
        pos3.y.toDouble(),
        color
    )

    //Triangle outline
    @JvmStatic
    fun drawTriangleOutline(
        pos1X: Double,
        pos1Y: Double,
        pos2X: Double,
        pos2Y: Double,
        pos3X: Double,
        pos3Y: Double,
        width: Float = 1F,
        color: ColorRGB,
    ) = renderer.drawTriangleOutline0(pos1X, pos1Y, pos2X, pos2Y, pos3X, pos3Y, width, color)

    @JvmStatic
    fun drawTriangleOutline(
        pos1X: Int,
        pos1Y: Int,
        pos2X: Int,
        pos2Y: Int,
        pos3X: Int,
        pos3Y: Int,
        width: Float = 1F,
        color: ColorRGB,
    ) = renderer.drawTriangleOutline0(
        pos1X.toDouble(),
        pos1Y.toDouble(),
        pos2X.toDouble(),
        pos2Y.toDouble(),
        pos3X.toDouble(),
        pos3Y.toDouble(),
        width,
        color
    )

    @JvmStatic
    fun drawTriangleOutline(
        pos1X: Float,
        pos1Y: Float,
        pos2X: Float,
        pos2Y: Float,
        pos3X: Float,
        pos3Y: Float,
        width: Float = 1F,
        color: ColorRGB,
    ) = renderer.drawTriangleOutline0(
        pos1X.toDouble(),
        pos1Y.toDouble(),
        pos2X.toDouble(),
        pos2Y.toDouble(),
        pos3X.toDouble(),
        pos3Y.toDouble(),
        width,
        color
    )

    @JvmStatic
    fun drawTriangleOutline(
        pos1: Vec2d,
        pos2: Vec2d,
        pos3: Vec2d,
        width: Float = 1F,
        color: ColorRGB,
    ) = renderer.drawTriangleOutline0(
        pos1.x,
        pos1.y,
        pos2.x,
        pos2.y,
        pos3.x,
        pos3.y,
        width,
        color
    )

    @JvmStatic
    fun drawTriangleOutline(
        pos1: Vec2f,
        pos2: Vec2f,
        pos3: Vec2f,
        width: Float = 1F,
        color: ColorRGB,
    ) = renderer.drawTriangleOutline0(
        pos1.x.toDouble(),
        pos1.y.toDouble(),
        pos2.x.toDouble(),
        pos2.y.toDouble(),
        pos3.x.toDouble(),
        pos3.y.toDouble(),
        width,
        color
    )

    /**
     * Rectangle
     */
    @JvmStatic
    fun drawRect(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        color: ColorRGB,
    ) = renderer.drawRect0(startX, startY, endX, endY, color)

    @JvmStatic
    fun drawRect(
        startX: Int,
        startY: Int,
        endX: Int,
        endY: Int,
        color: ColorRGB,
    ) = renderer.drawRect0(startX.toDouble(), startY.toDouble(), endX.toDouble(), endY.toDouble(), color)

    @JvmStatic
    fun drawRect(
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float,
        color: ColorRGB,
    ) = renderer.drawRect0(startX.toDouble(), startY.toDouble(), endX.toDouble(), endY.toDouble(), color)

    @JvmStatic
    fun drawRect(
        start: Vec2d,
        end: Vec2d,
        color: ColorRGB,
    ) = renderer.drawRect0(start.x, start.y, end.x, end.y, color)

    @JvmStatic
    fun drawRect(
        start: Vec2f,
        end: Vec2f,
        color: ColorRGB,
    ) = renderer.drawRect0(start.x.toDouble(), start.y.toDouble(), end.x.toDouble(), end.y.toDouble(), color)

    //Gradient Rectangle
    // color2  color1
    // color3  color4
    @JvmStatic
    fun drawGradientRect(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        color1: ColorRGB,
        color2: ColorRGB,
        color3: ColorRGB,
        color4: ColorRGB,
    ) = renderer.drawGradientRect0(startX, startY, endX, endY, color1, color2, color3, color4)

    @JvmStatic
    fun drawGradientRect(
        startX: Int,
        startY: Int,
        endX: Int,
        endY: Int,
        color1: ColorRGB,
        color2: ColorRGB,
        color3: ColorRGB,
        color4: ColorRGB,
    ) = renderer.drawGradientRect0(
        startX.toDouble(),
        startY.toDouble(),
        endX.toDouble(),
        endY.toDouble(),
        color1,
        color2,
        color3,
        color4
    )

    @JvmStatic
    fun drawGradientRect(
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float,
        color1: ColorRGB,
        color2: ColorRGB,
        color3: ColorRGB,
        color4: ColorRGB,
    ) = renderer.drawGradientRect0(
        startX.toDouble(),
        startY.toDouble(),
        endX.toDouble(),
        endY.toDouble(),
        color1,
        color2,
        color3,
        color4
    )

    @JvmStatic
    fun drawGradientRect(
        start: Vec2d,
        end: Vec2d,
        color1: ColorRGB,
        color2: ColorRGB,
        color3: ColorRGB,
        color4: ColorRGB,
    ) = renderer.drawGradientRect0(start.x, start.y, end.x, end.y, color1, color2, color3, color4)

    @JvmStatic
    fun drawGradientRect(
        start: Vec2f,
        end: Vec2f,
        color1: ColorRGB,
        color2: ColorRGB,
        color3: ColorRGB,
        color4: ColorRGB,
    ) = renderer.drawGradientRect0(
        start.x.toDouble(),
        start.y.toDouble(),
        end.x.toDouble(),
        end.y.toDouble(),
        color1,
        color2,
        color3,
        color4
    )

    //Horizontal
    @JvmStatic
    fun drawHorizontalRect(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = renderer.drawGradientRect0(startX, startY, endX, endY, endColorRGB, startColor, startColor, endColorRGB)

    @JvmStatic
    fun drawHorizontalRect(
        startX: Int,
        startY: Int,
        endX: Int,
        endY: Int,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = renderer.drawGradientRect0(
        startX.toDouble(),
        startY.toDouble(),
        endX.toDouble(),
        endY.toDouble(),
        endColorRGB,
        startColor,
        startColor,
        endColorRGB
    )

    @JvmStatic
    fun drawHorizontalRect(
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = renderer.drawGradientRect0(
        startX.toDouble(),
        startY.toDouble(),
        endX.toDouble(),
        endY.toDouble(),
        endColorRGB,
        startColor,
        startColor,
        endColorRGB
    )

    @JvmStatic
    fun drawHorizontalRect(
        start: Vec2d,
        end: Vec2d,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = renderer.drawGradientRect0(
        start.x,
        start.y,
        end.x,
        end.y,
        endColorRGB,
        startColor,
        startColor,
        endColorRGB
    )

    @JvmStatic
    fun drawHorizontalRect(
        start: Vec2f,
        end: Vec2f,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = renderer.drawGradientRect0(
        start.x.toDouble(),
        start.y.toDouble(),
        end.x.toDouble(),
        end.y.toDouble(),
        endColorRGB,
        startColor,
        startColor,
        endColorRGB
    )

    //Vertical
    @JvmStatic
    fun drawVerticalRect(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = renderer.drawGradientRect0(startX, startY, endX, endY, startColor, startColor, endColorRGB, endColorRGB)

    @JvmStatic
    fun drawVerticalRect(
        startX: Int,
        startY: Int,
        endX: Int,
        endY: Int,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = renderer.drawGradientRect0(
        startX.toDouble(),
        startY.toDouble(),
        endX.toDouble(),
        endY.toDouble(),
        startColor,
        startColor,
        endColorRGB,
        endColorRGB
    )

    @JvmStatic
    fun drawVerticalRect(
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = renderer.drawGradientRect0(
        startX.toDouble(),
        startY.toDouble(),
        endX.toDouble(),
        endY.toDouble(),
        startColor,
        startColor,
        endColorRGB,
        endColorRGB
    )

    @JvmStatic
    fun drawVerticalRect(
        start: Vec2d,
        end: Vec2d,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = renderer.drawGradientRect0(
        start.x,
        start.y,
        end.x,
        end.y,
        startColor,
        startColor,
        endColorRGB,
        endColorRGB
    )

    @JvmStatic
    fun drawVerticalRect(
        start: Vec2f,
        end: Vec2f,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = renderer.drawGradientRect0(
        start.x.toDouble(),
        start.y.toDouble(),
        end.x.toDouble(),
        end.y.toDouble(),
        startColor,
        startColor,
        endColorRGB,
        endColorRGB
    )

    //Rectangle outline
    @JvmStatic
    fun drawRectOutline(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        width: Float = 1F,
        color1: ColorRGB,
        color2: ColorRGB = color1,
        color3: ColorRGB = color1,
        color4: ColorRGB = color1,
    ) = renderer.drawRectOutline0(startX, startY, endX, endY, width, color1, color2, color3, color4)

    @JvmStatic
    fun drawRectOutline(
        startX: Int,
        startY: Int,
        endX: Int,
        endY: Int,
        width: Float = 1F,
        color1: ColorRGB,
        color2: ColorRGB = color1,
        color3: ColorRGB = color1,
        color4: ColorRGB = color1,
    ) = renderer.drawRectOutline0(
        startX.toDouble(),
        startY.toDouble(),
        endX.toDouble(),
        endY.toDouble(),
        width,
        color1,
        color2,
        color3,
        color4
    )

    @JvmStatic
    fun drawRectOutline(
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float,
        width: Float = 1F,
        color1: ColorRGB,
        color2: ColorRGB = color1,
        color3: ColorRGB = color1,
        color4: ColorRGB = color1,
    ) = renderer.drawRectOutline0(
        startX.toDouble(),
        startY.toDouble(),
        endX.toDouble(),
        endY.toDouble(),
        width,
        color1,
        color2,
        color3,
        color4
    )

    @JvmStatic
    fun drawRectOutline(
        start: Vec2d,
        end: Vec2d,
        width: Float = 1F,
        color1: ColorRGB,
        color2: ColorRGB = color1,
        color3: ColorRGB = color1,
        color4: ColorRGB = color1,
    ) = renderer.drawRectOutline0(start.x, start.y, end.x, end.y, width, color1, color2, color3, color4)

    @JvmStatic
    fun drawRectOutline(
        start: Vec2f,
        end: Vec2f,
        width: Float = 1F,
        color1: ColorRGB,
        color2: ColorRGB = color1,
        color3: ColorRGB = color1,
        color4: ColorRGB = color1,
    ) = renderer.drawRectOutline0(
        start.x.toDouble(),
        start.y.toDouble(),
        end.x.toDouble(),
        end.y.toDouble(),
        width,
        color1,
        color2,
        color3,
        color4
    )

    //Horizontal
    @JvmStatic
    fun drawHorizontalRectOutline(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        width: Float = 1F,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = renderer.drawRectOutline0(startX, startY, endX, endY, width, endColorRGB, startColor, startColor, endColorRGB)

    @JvmStatic
    fun drawHorizontalRectOutline(
        startX: Int,
        startY: Int,
        endX: Int,
        endY: Int,
        width: Float = 1F,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = renderer.drawRectOutline0(
        startX.toDouble(),
        startY.toDouble(),
        endX.toDouble(),
        endY.toDouble(),
        width,
        endColorRGB,
        startColor,
        startColor,
        endColorRGB
    )

    @JvmStatic
    fun drawHorizontalRectOutline(
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float,
        width: Float = 1F,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = renderer.drawRectOutline0(
        startX.toDouble(),
        startY.toDouble(),
        endX.toDouble(),
        endY.toDouble(),
        width,
        endColorRGB,
        startColor,
        startColor,
        endColorRGB
    )

    @JvmStatic
    fun drawHorizontalRectOutline(
        start: Vec2d,
        end: Vec2d,
        width: Float = 1F,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = renderer.drawRectOutline0(
        start.x,
        start.y,
        end.x,
        end.y,
        width,
        endColorRGB,
        startColor,
        startColor,
        endColorRGB
    )

    @JvmStatic
    fun drawHorizontalRectOutline(
        start: Vec2f,
        end: Vec2f,
        width: Float = 1F,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = renderer.drawRectOutline0(
        start.x.toDouble(),
        start.y.toDouble(),
        end.x.toDouble(),
        end.y.toDouble(),
        width,
        endColorRGB,
        startColor,
        startColor,
        endColorRGB
    )

    //Vertical
    @JvmStatic
    fun drawVerticalRectOutline(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        width: Float = 1F,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = renderer.drawRectOutline0(startX, startY, endX, endY, width, startColor, startColor, endColorRGB, endColorRGB)

    @JvmStatic
    fun drawVerticalRectOutline(
        startX: Int,
        startY: Int,
        endX: Int,
        endY: Int,
        width: Float = 1F,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = renderer.drawRectOutline0(
        startX.toDouble(),
        startY.toDouble(),
        endX.toDouble(),
        endY.toDouble(),
        width,
        startColor,
        startColor,
        endColorRGB,
        endColorRGB
    )

    @JvmStatic
    fun drawVerticalRectOutline(
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float,
        width: Float = 1F,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = renderer.drawRectOutline0(
        startX.toDouble(),
        startY.toDouble(),
        endX.toDouble(),
        endY.toDouble(),
        width,
        startColor,
        startColor,
        endColorRGB,
        endColorRGB
    )

    @JvmStatic
    fun drawVerticalRectOutline(
        start: Vec2d,
        end: Vec2d,
        width: Float = 1F,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = renderer.drawRectOutline0(
        start.x,
        start.y,
        end.x,
        end.y,
        width,
        startColor,
        startColor,
        endColorRGB,
        endColorRGB
    )

    @JvmStatic
    fun drawVerticalRectOutline(
        start: Vec2f,
        end: Vec2f,
        width: Float = 1F,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = renderer.drawRectOutline0(
        start.x.toDouble(),
        start.y.toDouble(),
        end.x.toDouble(),
        end.y.toDouble(),
        width,
        startColor,
        startColor,
        endColorRGB,
        endColorRGB
    )

    /**
     * RoundedRect
     */
    @JvmStatic
    fun drawRoundedRect(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        radius: Float,
        segments: Int = 0,
        color: ColorRGB,
    ) = renderer.drawRoundedRect0(startX, startY, endX, endY, radius, segments, color)

    @JvmStatic
    fun drawRoundedRect(
        startX: Int,
        startY: Int,
        endX: Int,
        endY: Int,
        radius: Float,
        segments: Int = 0,
        color: ColorRGB,
    ) = renderer.drawRoundedRect0(
        startX.toDouble(),
        startY.toDouble(),
        endX.toDouble(),
        endY.toDouble(),
        radius,
        segments,
        color
    )

    @JvmStatic
    fun drawRoundedRect(
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float,
        radius: Float,
        segments: Int = 0,
        color: ColorRGB,
    ) = renderer.drawRoundedRect0(
        startX.toDouble(),
        startY.toDouble(),
        endX.toDouble(),
        endY.toDouble(),
        radius,
        segments,
        color
    )

    @JvmStatic
    fun drawRoundedRect(
        start: Vec2d,
        end: Vec2d,
        radius: Float,
        segments: Int = 0,
        color: ColorRGB,
    ) = renderer.drawRoundedRect0(
        start.x,
        start.y,
        end.x,
        end.y,
        radius,
        segments,
        color
    )

    @JvmStatic
    fun drawRoundedRect(
        start: Vec2f,
        end: Vec2f,
        radius: Float,
        segments: Int = 0,
        color: ColorRGB,
    ) = renderer.drawRoundedRect0(
        start.x.toDouble(),
        start.y.toDouble(),
        end.x.toDouble(),
        end.y.toDouble(),
        radius,
        segments,
        color
    )

    @JvmStatic
    fun drawRoundedRectOutline(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        radius: Float,
        width: Float = 1F,
        segments: Int = 0,
        color: ColorRGB,
    ) = renderer.drawRoundedRectOutline0(startX, startY, endX, endY, radius, width, segments, color)

    @JvmStatic
    fun drawRoundedRectOutline(
        startX: Int,
        startY: Int,
        endX: Int,
        endY: Int,
        radius: Float,
        width: Float = 1F,
        segments: Int = 0,
        color: ColorRGB,
    ) = renderer.drawRoundedRectOutline0(
        startX.toDouble(),
        startY.toDouble(),
        endX.toDouble(),
        endY.toDouble(),
        radius,
        width,
        segments,
        color
    )

    @JvmStatic
    fun drawRoundedRectOutline(
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float,
        radius: Float,
        width: Float = 1F,
        segments: Int = 0,
        color: ColorRGB,
    ) = renderer.drawRoundedRectOutline0(
        startX.toDouble(),
        startY.toDouble(),
        endX.toDouble(),
        endY.toDouble(),
        radius,
        width,
        segments,
        color
    )

    @JvmStatic
    fun drawRoundedRectOutline(
        start: Vec2d,
        end: Vec2d,
        radius: Float,
        width: Float = 1F,
        segments: Int = 0,
        color: ColorRGB,
    ) = renderer.drawRoundedRectOutline0(
        start.x,
        start.y,
        end.x,
        end.y,
        radius,
        width,
        segments,
        color
    )

    @JvmStatic
    fun drawRoundedRectOutline(
        start: Vec2f,
        end: Vec2f,
        radius: Float,
        width: Float = 1F,
        segments: Int = 0,
        color: ColorRGB,
    ) = renderer.drawRoundedRectOutline0(
        start.x.toDouble(),
        start.y.toDouble(),
        end.x.toDouble(),
        end.y.toDouble(),
        radius,
        width,
        segments,
        color
    )

    @JvmStatic
    fun drawArc(
        centerX: Double,
        centerY: Double,
        radius: Float,
        angleRange: Pair<Float, Float>,
        segments: Int = 0,
        color: ColorRGB,
    ) = renderer.drawArc0(centerX, centerY, radius, angleRange, segments, color)

    @JvmStatic
    fun drawArc(
        centerX: Int,
        centerY: Int,
        radius: Float,
        angleRange: Pair<Float, Float>,
        segments: Int = 0,
        color: ColorRGB,
    ) = renderer.drawArc0(centerX.toDouble(), centerY.toDouble(), radius, angleRange, segments, color)

    @JvmStatic
    fun drawArc(
        centerX: Float,
        centerY: Float,
        radius: Float,
        angleRange: Pair<Float, Float>,
        segments: Int = 0,
        color: ColorRGB,
    ) = renderer.drawArc0(centerX.toDouble(), centerY.toDouble(), radius, angleRange, segments, color)

    @JvmStatic
    fun drawArc(
        center: Vec2d,
        radius: Float,
        angleRange: Pair<Float, Float>,
        segments: Int = 0,
        color: ColorRGB,
    ) = renderer.drawArc0(center.x, center.y, radius, angleRange, segments, color)

    @JvmStatic
    fun drawArc(
        center: Vec2f,
        radius: Float,
        angleRange: Pair<Float, Float>,
        segments: Int = 0,
        color: ColorRGB,
    ) = renderer.drawArc0(center.x.toDouble(), center.y.toDouble(), radius, angleRange, segments, color)

    @JvmStatic
    fun drawArcOutline(
        centerX: Double,
        centerY: Double,
        radius: Float,
        angleRange: Pair<Float, Float>,
        segments: Int = 0,
        lineWidth: Float = 1f,
        color: ColorRGB,
    ) = renderer.drawLinesStrip0(getArcVertices(centerX, centerY, radius, angleRange, segments), lineWidth, color)

    @JvmStatic
    fun drawArcOutline(
        centerX: Int,
        centerY: Int,
        radius: Float,
        angleRange: Pair<Float, Float>,
        segments: Int = 0,
        lineWidth: Float = 1f,
        color: ColorRGB,
    ) = renderer.drawLinesStrip0(
        getArcVertices(centerX.toDouble(), centerY.toDouble(), radius, angleRange, segments),
        lineWidth,
        color
    )

    @JvmStatic
    fun drawArcOutline(
        centerX: Float,
        centerY: Float,
        radius: Float,
        angleRange: Pair<Float, Float>,
        segments: Int = 0,
        lineWidth: Float = 1f,
        color: ColorRGB,
    ) = renderer.drawLinesStrip0(
        getArcVertices(centerX.toDouble(), centerY.toDouble(), radius, angleRange, segments),
        lineWidth,
        color
    )

    @JvmStatic
    fun drawArcOutline(
        center: Vec2d,
        radius: Float,
        angleRange: Pair<Float, Float>,
        segments: Int = 0,
        lineWidth: Float = 1f,
        color: ColorRGB,
    ) = renderer.drawLinesStrip0(getArcVertices(center.x, center.y, radius, angleRange, segments), lineWidth, color)

    @JvmStatic
    fun drawArcOutline(
        center: Vec2f,
        radius: Float,
        angleRange: Pair<Float, Float>,
        segments: Int = 0,
        lineWidth: Float = 1f,
        color: ColorRGB,
    ) = renderer.drawLinesStrip0(
        getArcVertices(center.x.toDouble(), center.y.toDouble(), radius, angleRange, segments),
        lineWidth,
        color
    )

    @JvmStatic
    fun getArcVertices(
        centerX: Double,
        centerY: Double,
        radius: Float,
        angleRange: Pair<Float, Float> = Pair(0f, 360f),
        segments: Int = 0,
    ): Array<Vec2d> {
        val range = max(angleRange.first, angleRange.second) - min(angleRange.first, angleRange.second)
        val seg = MathUtils.calcSegments(segments, radius.toDouble(), range)
        val segAngle = (range.toDouble() / seg.toDouble())

        return Array(seg + 1) {
            val angle = Math.toRadians(it * segAngle + angleRange.first.toDouble())
            val unRounded = Vec2d(sin(angle), -cos(angle)).times(radius.toDouble()).plus(Vec2d(centerX, centerY))
            Vec2d(MathUtils.round(unRounded.x, 8), MathUtils.round(unRounded.y, 8))
        }
    }

    @JvmStatic
    fun prepareGl() {
        GLStateManager.texture2d(false)
        GLStateManager.blend(true)
        GLStateManager.smooth(true)
        GLStateManager.lineSmooth(true)
        GLStateManager.cull(false)
    }

    @JvmStatic
    fun releaseGl() {
        GLStateManager.texture2d(true)
        GLStateManager.smooth(false)
        GLStateManager.lineSmooth(false)
        GLStateManager.cull(true)
    }

}