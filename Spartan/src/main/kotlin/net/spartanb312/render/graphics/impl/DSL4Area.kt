package net.spartanb312.render.graphics.impl

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class AreaScope(
    @Transient
    private val startX: Double,
    @Transient
    private val startY: Double,
    @Transient
    private val endX: Double,
    @Transient
    private val endY: Double
) {
    val minX get() = min(startX, endX)
    val minY get() = min(startY, endY)
    val maxX get() = max(startX, endX)
    val maxY get() = max(startY, endY)
    val width get() = abs(endX - startX)
    val height get() = abs(endY - startY)
}

@Render2DMark
inline fun Render2DScope.area(
    startX: Double,
    startY: Double,
    endX: Double,
    endY: Double,
    block: AreaScope.() -> Unit
) {
    val area = AreaScope(startX, startY, endX, endY)
    matrix {
        translate(area.minX, area.minY) {
            area.block()
        }
    }
}

@Render2DMark
inline fun Render2DScope.area(
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float,
    block: AreaScope.() -> Unit
) = area(startX.toDouble(), startY.toDouble(), endX.toDouble(), endY.toDouble(), block)

@Render2DMark
inline fun Render2DScope.area(
    startX: Int,
    startY: Int,
    endX: Int,
    endY: Int,
    block: AreaScope.() -> Unit
) = area(startX.toDouble(), startY.toDouble(), endX.toDouble(), endY.toDouble(), block)