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
    private val endY: Double,
    render2DScope: Render2DScope,
) : Render2DScope(
    render2DScope.mouseX,
    render2DScope.mouseY,
    render2DScope.scaledResolution,
    render2DScope.renderer2D,
    render2DScope.fontRenderer
) {
    val minX get() = min(startX, endX)
    val minY get() = min(startY, endY)
    val maxX get() = max(startX, endX)
    val maxY get() = max(startY, endY)
    val width get() = abs(endX - startX)
    val height get() = abs(endY - startY)
}

@Render2DMark
fun Render2DScope.area(
    startX: Double,
    startY: Double,
    endX: Double,
    endY: Double,
): AreaScope = AreaScope(startX, startY, endX, endY, this)

@Render2DMark
fun Render2DScope.area(
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float,
): AreaScope = AreaScope(startX.toDouble(), startY.toDouble(), endX.toDouble(), endY.toDouble(), this)

@Render2DMark
fun Render2DScope.area(
    startX: Int,
    startY: Int,
    endX: Int,
    endY: Int,
): AreaScope = AreaScope(startX.toDouble(), startY.toDouble(), endX.toDouble(), endY.toDouble(), this)

@Render2DMark
inline fun Render2DScope.area(
    startX: Double,
    startY: Double,
    endX: Double,
    endY: Double,
    block: AreaScope.() -> Unit
): AreaScope = AreaScope(startX, startY, endX, endY, this).also {
    matrix {
        translate(it.minX, it.minY) {
            it.block()
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
): AreaScope = area(startX.toDouble(), startY.toDouble(), endX.toDouble(), endY.toDouble(), block)

@Render2DMark
inline fun Render2DScope.area(
    startX: Int,
    startY: Int,
    endX: Int,
    endY: Int,
    block: AreaScope.() -> Unit
): AreaScope = area(startX.toDouble(), startY.toDouble(), endX.toDouble(), endY.toDouble(), block)