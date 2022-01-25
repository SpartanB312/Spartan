@file:Suppress("NOTHING_TO_INLINE")

package net.spartanb312.render.graphics.impl

import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.GlStateManager
import net.spartanb312.render.core.common.extension.map
import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.core.common.math.Vec2d
import net.spartanb312.render.core.common.math.Vec2f
import net.spartanb312.render.graphics.api.I2DRenderer
import net.spartanb312.render.graphics.api.font.setting.LinkedSettableFontRenderer
import net.spartanb312.render.graphics.impl.legacy.Legacy2DRenderer
import net.spartanb312.render.graphics.impl.vao.VAO2DRenderer
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP

@DslMarker
annotation class Render2DMark

open class Render2DScope(
    val mouseX: Int,
    val mouseY: Int,
    val scaledResolution: ScaledResolution,
    val renderer2D: I2DRenderer,
    val fontRenderer: LinkedSettableFontRenderer
)

@Render2DMark
inline fun Render2DScope.matrix(block: () -> Unit) {
    glPushMatrix()
    block()
    glPopMatrix()
}

@Render2DMark
inline fun Render2DScope.translate(
    transX: Double,
    transY: Double,
    transZ: Double = 0.0,
    block: Render2DScope.() -> Unit
) {
    glTranslated(transX, transY, transZ)
    block()
    glTranslated(-transX, -transY, -transZ)
}

@Render2DMark
inline fun Render2DScope.scope2D(block: Render2DScope.() -> Unit) {
    GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ONE)
    glEnable(GL_DEPTH_CLAMP)
    Renderer2D.prepareGl()
    block()
    Renderer2D.releaseGl()
    glDisable(GL_DEPTH_CLAMP)
    GLStateManager.useProgram(0, true)
}

@Render2DMark
inline fun legacy2D(block: Legacy2DRenderer.() -> Unit) {
    Renderer2D.prepareGl()
    Legacy2DRenderer.block()
    Renderer2D.releaseGl()
}

@Render2DMark
inline fun vao2D(block: VAO2DRenderer.() -> Unit) {
    glEnable(GL_DEPTH_CLAMP)
    Renderer2D.prepareGl()
    VAO2DRenderer.block()
    Renderer2D.releaseGl()
    glDisable(GL_DEPTH_CLAMP)
    GLStateManager.useProgram(0, true)
}

@Render2DMark
inline fun Render2DScope.render2D(renderer: I2DRenderer = renderer2D, block: I2DRenderer.() -> Unit) {
    Renderer2D.prepareGl()
    renderer.block()
    Renderer2D.releaseGl()
}

@Render2DMark
fun Render2DScope.drawString(
    charSequence: CharSequence,
    posX: Float = 0.0f,
    posY: Float = 0.0f,
    color: ColorRGB = ColorRGB(255, 255, 255),
    scale: Float = 1.0f,
) = fontRenderer.drawString(charSequence, posX, posY, color, scale, false)

@Render2DMark
fun Render2DScope.drawStringWithShadow(
    charSequence: CharSequence,
    posX: Float = 0.0f,
    posY: Float = 0.0f,
    color: ColorRGB = ColorRGB(255, 255, 255),
    scale: Float = 1.0f,
) = fontRenderer.drawString(charSequence, posX, posY, color, scale, true)

@Render2DMark
fun Render2DScope.drawStringInJava(
    string: String,
    posX: Float,
    posY: Float,
    color: Int,
    scale: Float,
) = fontRenderer.drawStringJava(string, posX, posY, color, scale, false)

@Render2DMark
fun Render2DScope.drawStringWithShadowInJava(
    string: String,
    posX: Float,
    posY: Float,
    color: Int,
    scale: Float,
) = fontRenderer.drawStringJava(string, posX, posY, color, scale, true)

@Render2DMark
fun Render2DScope.drawString(
    charSequence: CharSequence,
    posX: Double = 0.0,
    posY: Double = 0.0,
    color: ColorRGB = ColorRGB(255, 255, 255),
    scale: Float = 1.0f,
) = fontRenderer.drawString(charSequence, posX.toFloat(), posY.toFloat(), color, scale, false)

@Render2DMark
fun Render2DScope.drawStringWithShadow(
    charSequence: CharSequence,
    posX: Double = 0.0,
    posY: Double = 0.0,
    color: ColorRGB = ColorRGB(255, 255, 255),
    scale: Float = 1.0f,
) = fontRenderer.drawString(charSequence, posX.toFloat(), posY.toFloat(), color, scale, true)

@Render2DMark
fun Render2DScope.drawStringInJava(
    string: String,
    posX: Double,
    posY: Double,
    color: Int,
    scale: Float = 1.0F,
) = fontRenderer.drawStringJava(string, posX.toFloat(), posY.toFloat(), color, scale, false)

@Render2DMark
fun Render2DScope.drawStringWithShadowInJava(
    string: String,
    posX: Double,
    posY: Double,
    color: Int,
    scale: Float = 1.0F,
) = fontRenderer.drawStringJava(string, posX.toFloat(), posY.toFloat(), color, scale, true)

@Render2DMark
fun Render2DScope.drawString(
    charSequence: CharSequence,
    posX: Int = 0,
    posY: Int = 0,
    color: ColorRGB = ColorRGB(255, 255, 255),
    scale: Float = 1.0F,
) = fontRenderer.drawString(charSequence, posX.toFloat(), posY.toFloat(), color, scale, false)

@Render2DMark
fun Render2DScope.drawStringWithShadow(
    charSequence: CharSequence,
    posX: Int = 0,
    posY: Int = 0,
    color: ColorRGB = ColorRGB(255, 255, 255),
    scale: Float = 1.0F,
) = fontRenderer.drawString(charSequence, posX.toFloat(), posY.toFloat(), color, scale, true)

@Render2DMark
fun Render2DScope.drawStringInJava(
    string: String,
    posX: Int,
    posY: Int,
    color: Int,
    scale: Float = 1.0F,
) = fontRenderer.drawStringJava(string, posX.toFloat(), posY.toFloat(), color, scale, false)

@Render2DMark
fun Render2DScope.drawStringWithShadowInJava(
    string: String,
    posX: Int,
    posY: Int,
    color: Int,
    scale: Float = 1.0F,
) = fontRenderer.drawStringJava(string, posX.toFloat(), posY.toFloat(), color, scale, true)

@Render2DMark
fun Render2DScope.getHeight(scale: Float): Float = fontRenderer.getHeight(scale)

@Render2DMark
fun Render2DScope.getWidth(text: CharSequence, scale: Float): Float = fontRenderer.getWidth(text, scale)

/**
 * Point
 */
@Render2DMark
inline fun Render2DScope.drawPoint(
    x: Double,
    y: Double,
    size: Float = 1F,
    color: ColorRGB
) = renderer2D.drawPoint0(x, y, size, color)

@Render2DMark
inline fun Render2DScope.drawPoint(
    x: Int,
    y: Int,
    size: Float = 1F,
    color: ColorRGB
) = renderer2D.drawPoint0(x.toDouble(), y.toDouble(), size, color)

@Render2DMark
inline fun Render2DScope.drawPoint(
    x: Float,
    y: Float,
    size: Float = 1F,
    color: ColorRGB
) = renderer2D.drawPoint0(x.toDouble(), y.toDouble(), size, color)

/**
 * Line
 */
@Render2DMark
inline fun Render2DScope.drawLine(
    startX: Double,
    startY: Double,
    endX: Double,
    endY: Double,
    width: Float = 1F,
    color1: ColorRGB,
    color2: ColorRGB = color1
) = renderer2D.drawLine0(startX, startY, endX, endY, width, color1, color2)

@Render2DMark
inline fun Render2DScope.drawLine(
    startX: Int,
    startY: Int,
    endX: Int,
    endY: Int,
    width: Float = 1F,
    color1: ColorRGB,
    color2: ColorRGB = color1
) = renderer2D.drawLine0(startX.toDouble(), startY.toDouble(), endX.toDouble(), endY.toDouble(), width, color1, color2)

@Render2DMark
inline fun Render2DScope.drawLine(
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float,
    width: Float = 1F,
    color1: ColorRGB,
    color2: ColorRGB = color1
) = renderer2D.drawLine0(startX.toDouble(), startY.toDouble(), endX.toDouble(), endY.toDouble(), width, color1, color2)

@Render2DMark
inline fun Render2DScope.drawLine(
    start: Vec2d,
    end: Vec2d,
    width: Float = 1F,
    color1: ColorRGB,
    color2: ColorRGB = color1
) = renderer2D.drawLine0(start.x, start.y, end.x, end.y, width, color1, color2)

@Render2DMark
inline fun Render2DScope.drawLine(
    start: Vec2f,
    end: Vec2f,
    width: Float = 1F,
    color1: ColorRGB,
    color2: ColorRGB = color1
) = renderer2D.drawLine0(
    start.x.toDouble(),
    start.y.toDouble(),
    end.x.toDouble(),
    end.y.toDouble(),
    width,
    color1,
    color2
)

@Render2DMark
inline fun Render2DScope.drawLinesStrip(
    vertexArray: Array<Vec2d>,
    width: Float = 1F,
    color: ColorRGB
) = renderer2D.drawLinesStrip0(vertexArray, width, color)

@Render2DMark
inline fun Render2DScope.drawLinesLoop(
    vertexArray: Array<Vec2d>,
    width: Float = 1F,
    color: ColorRGB
) = renderer2D.drawLinesLoop0(vertexArray, width, color)

@Render2DMark
inline fun Render2DScope.drawLinesStrip(
    vertexArray: Array<Vec2f>,
    width: Float = 1F,
    color: ColorRGB
) = renderer2D.drawLinesStrip0(vertexArray.map { it.toVec2d() }, width, color)

@Render2DMark
inline fun Render2DScope.drawLinesLoop(
    vertexArray: Array<Vec2f>,
    width: Float = 1F,
    color: ColorRGB
) = renderer2D.drawLinesLoop0(vertexArray.map { it.toVec2d() }, width, color)

/**
 * Triangle
 */
@Render2DMark
inline fun Render2DScope.drawTriangle(
    pos1X: Double,
    pos1Y: Double,
    pos2X: Double,
    pos2Y: Double,
    pos3X: Double,
    pos3Y: Double,
    color: ColorRGB
) = renderer2D.drawTriangle0(pos1X, pos1Y, pos2X, pos2Y, pos3X, pos3Y, color)

@Render2DMark
inline fun Render2DScope.drawTriangle(
    pos1X: Int,
    pos1Y: Int,
    pos2X: Int,
    pos2Y: Int,
    pos3X: Int,
    pos3Y: Int,
    color: ColorRGB
) = renderer2D.drawTriangle0(
    pos1X.toDouble(),
    pos1Y.toDouble(),
    pos2X.toDouble(),
    pos2Y.toDouble(),
    pos3X.toDouble(),
    pos3Y.toDouble(),
    color
)

@Render2DMark
inline fun Render2DScope.drawTriangle(
    pos1X: Float,
    pos1Y: Float,
    pos2X: Float,
    pos2Y: Float,
    pos3X: Float,
    pos3Y: Float,
    color: ColorRGB
) = renderer2D.drawTriangle0(
    pos1X.toDouble(),
    pos1Y.toDouble(),
    pos2X.toDouble(),
    pos2Y.toDouble(),
    pos3X.toDouble(),
    pos3Y.toDouble(),
    color
)

@Render2DMark
inline fun Render2DScope.drawTriangle(
    pos1: Vec2d,
    pos2: Vec2d,
    pos3: Vec2d,
    color: ColorRGB
) = renderer2D.drawTriangle0(
    pos1.x,
    pos1.y,
    pos2.x,
    pos2.y,
    pos3.x,
    pos3.y,
    color
)

@Render2DMark
inline fun Render2DScope.drawTriangle(
    pos1: Vec2f,
    pos2: Vec2f,
    pos3: Vec2f,
    color: ColorRGB
) = renderer2D.drawTriangle0(
    pos1.x.toDouble(),
    pos1.y.toDouble(),
    pos2.x.toDouble(),
    pos2.y.toDouble(),
    pos3.x.toDouble(),
    pos3.y.toDouble(),
    color
)

//Triangle outline
@Render2DMark
inline fun Render2DScope.drawTriangleOutline(
    pos1X: Double,
    pos1Y: Double,
    pos2X: Double,
    pos2Y: Double,
    pos3X: Double,
    pos3Y: Double,
    width: Float = 1F,
    color: ColorRGB
) = renderer2D.drawTriangleOutline0(pos1X, pos1Y, pos2X, pos2Y, pos3X, pos3Y, width, color)

@Render2DMark
inline fun Render2DScope.drawTriangleOutline(
    pos1X: Int,
    pos1Y: Int,
    pos2X: Int,
    pos2Y: Int,
    pos3X: Int,
    pos3Y: Int,
    width: Float = 1F,
    color: ColorRGB
) = renderer2D.drawTriangleOutline0(
    pos1X.toDouble(),
    pos1Y.toDouble(),
    pos2X.toDouble(),
    pos2Y.toDouble(),
    pos3X.toDouble(),
    pos3Y.toDouble(),
    width,
    color
)

@Render2DMark
inline fun Render2DScope.drawTriangleOutline(
    pos1X: Float,
    pos1Y: Float,
    pos2X: Float,
    pos2Y: Float,
    pos3X: Float,
    pos3Y: Float,
    width: Float = 1F,
    color: ColorRGB
) = renderer2D.drawTriangleOutline0(
    pos1X.toDouble(),
    pos1Y.toDouble(),
    pos2X.toDouble(),
    pos2Y.toDouble(),
    pos3X.toDouble(),
    pos3Y.toDouble(),
    width,
    color
)

@Render2DMark
inline fun Render2DScope.drawTriangleOutline(
    pos1: Vec2d,
    pos2: Vec2d,
    pos3: Vec2d,
    width: Float = 1F,
    color: ColorRGB
) = renderer2D.drawTriangleOutline0(
    pos1.x,
    pos1.y,
    pos2.x,
    pos2.y,
    pos3.x,
    pos3.y,
    width,
    color
)

@Render2DMark
inline fun Render2DScope.drawTriangleOutline(
    pos1: Vec2f,
    pos2: Vec2f,
    pos3: Vec2f,
    width: Float = 1F,
    color: ColorRGB
) = renderer2D.drawTriangleOutline0(
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
@Render2DMark
inline fun Render2DScope.drawRect(
    startX: Double,
    startY: Double,
    endX: Double,
    endY: Double,
    color: ColorRGB
) = renderer2D.drawRect0(startX, startY, endX, endY, color)

@Render2DMark
inline fun Render2DScope.drawRect(
    startX: Int,
    startY: Int,
    endX: Int,
    endY: Int,
    color: ColorRGB
) = renderer2D.drawRect0(startX.toDouble(), startY.toDouble(), endX.toDouble(), endY.toDouble(), color)

@Render2DMark
inline fun Render2DScope.drawRect(
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float,
    color: ColorRGB
) = renderer2D.drawRect0(startX.toDouble(), startY.toDouble(), endX.toDouble(), endY.toDouble(), color)

@Render2DMark
inline fun Render2DScope.drawRect(
    start: Vec2d,
    end: Vec2d,
    color: ColorRGB
) = renderer2D.drawRect0(start.x, start.y, end.x, end.y, color)

@Render2DMark
inline fun Render2DScope.drawRect(
    start: Vec2f,
    end: Vec2f,
    color: ColorRGB
) = renderer2D.drawRect0(start.x.toDouble(), start.y.toDouble(), end.x.toDouble(), end.y.toDouble(), color)

//Gradient Rectangle
// color2  color1
// color3  color4
@Render2DMark
inline fun Render2DScope.drawGradientRect(
    startX: Double,
    startY: Double,
    endX: Double,
    endY: Double,
    color1: ColorRGB,
    color2: ColorRGB,
    color3: ColorRGB,
    color4: ColorRGB
) = renderer2D.drawGradientRect0(startX, startY, endX, endY, color1, color2, color3, color4)

@Render2DMark
inline fun Render2DScope.drawGradientRect(
    startX: Int,
    startY: Int,
    endX: Int,
    endY: Int,
    color1: ColorRGB,
    color2: ColorRGB,
    color3: ColorRGB,
    color4: ColorRGB
) = renderer2D.drawGradientRect0(
    startX.toDouble(),
    startY.toDouble(),
    endX.toDouble(),
    endY.toDouble(),
    color1,
    color2,
    color3,
    color4
)

@Render2DMark
inline fun Render2DScope.drawGradientRect(
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float,
    color1: ColorRGB,
    color2: ColorRGB,
    color3: ColorRGB,
    color4: ColorRGB
) = renderer2D.drawGradientRect0(
    startX.toDouble(),
    startY.toDouble(),
    endX.toDouble(),
    endY.toDouble(),
    color1,
    color2,
    color3,
    color4
)

@Render2DMark
inline fun Render2DScope.drawGradientRect(
    start: Vec2d,
    end: Vec2d,
    color1: ColorRGB,
    color2: ColorRGB,
    color3: ColorRGB,
    color4: ColorRGB
) = renderer2D.drawGradientRect0(start.x, start.y, end.x, end.y, color1, color2, color3, color4)

@Render2DMark
inline fun Render2DScope.drawGradientRect(
    start: Vec2f,
    end: Vec2f,
    color1: ColorRGB,
    color2: ColorRGB,
    color3: ColorRGB,
    color4: ColorRGB
) = renderer2D.drawGradientRect0(
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
@Render2DMark
inline fun Render2DScope.drawHorizontalRect(
    startX: Double,
    startY: Double,
    endX: Double,
    endY: Double,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = renderer2D.drawGradientRect0(startX, startY, endX, endY, endColorRGB, startColor, startColor, endColorRGB)

@Render2DMark
inline fun Render2DScope.drawHorizontalRect(
    startX: Int,
    startY: Int,
    endX: Int,
    endY: Int,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = renderer2D.drawGradientRect0(
    startX.toDouble(),
    startY.toDouble(),
    endX.toDouble(),
    endY.toDouble(),
    endColorRGB,
    startColor,
    startColor,
    endColorRGB
)

@Render2DMark
inline fun Render2DScope.drawHorizontalRect(
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = renderer2D.drawGradientRect0(
    startX.toDouble(),
    startY.toDouble(),
    endX.toDouble(),
    endY.toDouble(),
    endColorRGB,
    startColor,
    startColor,
    endColorRGB
)

@Render2DMark
inline fun Render2DScope.drawHorizontalRect(
    start: Vec2d,
    end: Vec2d,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = renderer2D.drawGradientRect0(
    start.x,
    start.y,
    end.x,
    end.y,
    endColorRGB,
    startColor,
    startColor,
    endColorRGB
)

@Render2DMark
inline fun Render2DScope.drawHorizontalRect(
    start: Vec2f,
    end: Vec2f,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = renderer2D.drawGradientRect0(
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
@Render2DMark
inline fun Render2DScope.drawVerticalRect(
    startX: Double,
    startY: Double,
    endX: Double,
    endY: Double,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = renderer2D.drawGradientRect0(startX, startY, endX, endY, startColor, startColor, endColorRGB, endColorRGB)

@Render2DMark
inline fun Render2DScope.drawVerticalRect(
    startX: Int,
    startY: Int,
    endX: Int,
    endY: Int,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = renderer2D.drawGradientRect0(
    startX.toDouble(),
    startY.toDouble(),
    endX.toDouble(),
    endY.toDouble(),
    startColor,
    startColor,
    endColorRGB,
    endColorRGB
)

@Render2DMark
inline fun Render2DScope.drawVerticalRect(
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = renderer2D.drawGradientRect0(
    startX.toDouble(),
    startY.toDouble(),
    endX.toDouble(),
    endY.toDouble(),
    startColor,
    startColor,
    endColorRGB,
    endColorRGB
)

@Render2DMark
inline fun Render2DScope.drawVerticalRect(
    start: Vec2d,
    end: Vec2d,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = renderer2D.drawGradientRect0(
    start.x,
    start.y,
    end.x,
    end.y,
    startColor,
    startColor,
    endColorRGB,
    endColorRGB
)

@Render2DMark
inline fun Render2DScope.drawVerticalRect(
    start: Vec2f,
    end: Vec2f,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = renderer2D.drawGradientRect0(
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
@Render2DMark
inline fun Render2DScope.drawRectOutline(
    startX: Double,
    startY: Double,
    endX: Double,
    endY: Double,
    width: Float = 1F,
    color1: ColorRGB,
    color2: ColorRGB = color1,
    color3: ColorRGB = color1,
    color4: ColorRGB = color1
) = renderer2D.drawRectOutline0(startX, startY, endX, endY, width, color1, color2, color3, color4)

@Render2DMark
inline fun Render2DScope.drawRectOutline(
    startX: Int,
    startY: Int,
    endX: Int,
    endY: Int,
    width: Float = 1F,
    color1: ColorRGB,
    color2: ColorRGB = color1,
    color3: ColorRGB = color1,
    color4: ColorRGB = color1
) = renderer2D.drawRectOutline0(
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

@Render2DMark
inline fun Render2DScope.drawRectOutline(
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float,
    width: Float = 1F,
    color1: ColorRGB,
    color2: ColorRGB = color1,
    color3: ColorRGB = color1,
    color4: ColorRGB = color1
) = renderer2D.drawRectOutline0(
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

@Render2DMark
inline fun Render2DScope.drawRectOutline(
    start: Vec2d,
    end: Vec2d,
    width: Float = 1F,
    color1: ColorRGB,
    color2: ColorRGB = color1,
    color3: ColorRGB = color1,
    color4: ColorRGB = color1
) = renderer2D.drawRectOutline0(start.x, start.y, end.x, end.y, width, color1, color2, color3, color4)

@Render2DMark
inline fun Render2DScope.drawRectOutline(
    start: Vec2f,
    end: Vec2f,
    width: Float = 1F,
    color1: ColorRGB,
    color2: ColorRGB = color1,
    color3: ColorRGB = color1,
    color4: ColorRGB = color1
) = renderer2D.drawRectOutline0(
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
@Render2DMark
inline fun Render2DScope.drawHorizontalRectOutline(
    startX: Double,
    startY: Double,
    endX: Double,
    endY: Double,
    width: Float = 1F,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = renderer2D.drawRectOutline0(startX, startY, endX, endY, width, endColorRGB, startColor, startColor, endColorRGB)

@Render2DMark
inline fun Render2DScope.drawHorizontalRectOutline(
    startX: Int,
    startY: Int,
    endX: Int,
    endY: Int,
    width: Float = 1F,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = renderer2D.drawRectOutline0(
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

@Render2DMark
inline fun Render2DScope.drawHorizontalRectOutline(
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float,
    width: Float = 1F,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = renderer2D.drawRectOutline0(
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

@Render2DMark
inline fun Render2DScope.drawHorizontalRectOutline(
    start: Vec2d,
    end: Vec2d,
    width: Float = 1F,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = renderer2D.drawRectOutline0(
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

@Render2DMark
inline fun Render2DScope.drawHorizontalRectOutline(
    start: Vec2f,
    end: Vec2f,
    width: Float = 1F,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = renderer2D.drawRectOutline0(
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
@Render2DMark
inline fun Render2DScope.drawVerticalRectOutline(
    startX: Double,
    startY: Double,
    endX: Double,
    endY: Double,
    width: Float = 1F,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = renderer2D.drawRectOutline0(startX, startY, endX, endY, width, startColor, startColor, endColorRGB, endColorRGB)

@Render2DMark
inline fun Render2DScope.drawVerticalRectOutline(
    startX: Int,
    startY: Int,
    endX: Int,
    endY: Int,
    width: Float = 1F,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = renderer2D.drawRectOutline0(
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

@Render2DMark
inline fun Render2DScope.drawVerticalRectOutline(
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float,
    width: Float = 1F,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = renderer2D.drawRectOutline0(
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

@Render2DMark
inline fun Render2DScope.drawVerticalRectOutline(
    start: Vec2d,
    end: Vec2d,
    width: Float = 1F,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = renderer2D.drawRectOutline0(
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

@Render2DMark
inline fun Render2DScope.drawVerticalRectOutline(
    start: Vec2f,
    end: Vec2f,
    width: Float = 1F,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = renderer2D.drawRectOutline0(
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
@Render2DMark
inline fun Render2DScope.drawRoundedRect(
    startX: Double,
    startY: Double,
    endX: Double,
    endY: Double,
    radius: Float,
    segments: Int = 0,
    color: ColorRGB
) = renderer2D.drawRoundedRect0(startX, startY, endX, endY, radius, segments, color)

@Render2DMark
inline fun Render2DScope.drawRoundedRect(
    startX: Int,
    startY: Int,
    endX: Int,
    endY: Int,
    radius: Float,
    segments: Int = 0,
    color: ColorRGB
) = renderer2D.drawRoundedRect0(
    startX.toDouble(),
    startY.toDouble(),
    endX.toDouble(),
    endY.toDouble(),
    radius,
    segments,
    color
)

@Render2DMark
inline fun Render2DScope.drawRoundedRect(
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float,
    radius: Float,
    segments: Int = 0,
    color: ColorRGB
) = renderer2D.drawRoundedRect0(
    startX.toDouble(),
    startY.toDouble(),
    endX.toDouble(),
    endY.toDouble(),
    radius,
    segments,
    color
)

@Render2DMark
inline fun Render2DScope.drawRoundedRect(
    start: Vec2d,
    end: Vec2d,
    radius: Float,
    segments: Int = 0,
    color: ColorRGB
) = renderer2D.drawRoundedRect0(
    start.x,
    start.y,
    end.x,
    end.y,
    radius,
    segments,
    color
)

@Render2DMark
inline fun Render2DScope.drawRoundedRect(
    start: Vec2f,
    end: Vec2f,
    radius: Float,
    segments: Int = 0,
    color: ColorRGB
) = renderer2D.drawRoundedRect0(
    start.x.toDouble(),
    start.y.toDouble(),
    end.x.toDouble(),
    end.y.toDouble(),
    radius,
    segments,
    color
)

@Render2DMark
inline fun Render2DScope.drawRoundedRectOutline(
    startX: Double,
    startY: Double,
    endX: Double,
    endY: Double,
    radius: Float,
    width: Float = 1F,
    segments: Int = 0,
    color: ColorRGB
) = renderer2D.drawRoundedRectOutline0(startX, startY, endX, endY, radius, width, segments, color)

@Render2DMark
inline fun Render2DScope.drawRoundedRectOutline(
    startX: Int,
    startY: Int,
    endX: Int,
    endY: Int,
    radius: Float,
    width: Float = 1F,
    segments: Int = 0,
    color: ColorRGB
) = renderer2D.drawRoundedRectOutline0(
    startX.toDouble(),
    startY.toDouble(),
    endX.toDouble(),
    endY.toDouble(),
    radius,
    width,
    segments,
    color
)

@Render2DMark
inline fun Render2DScope.drawRoundedRectOutline(
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float,
    radius: Float,
    width: Float = 1F,
    segments: Int = 0,
    color: ColorRGB
) = renderer2D.drawRoundedRectOutline0(
    startX.toDouble(),
    startY.toDouble(),
    endX.toDouble(),
    endY.toDouble(),
    radius,
    width,
    segments,
    color
)

@Render2DMark
inline fun Render2DScope.drawRoundedRectOutline(
    start: Vec2d,
    end: Vec2d,
    radius: Float,
    width: Float = 1F,
    segments: Int = 0,
    color: ColorRGB
) = renderer2D.drawRoundedRectOutline0(
    start.x,
    start.y,
    end.x,
    end.y,
    radius,
    width,
    segments,
    color
)

@Render2DMark
inline fun Render2DScope.drawRoundedRectOutline(
    start: Vec2f,
    end: Vec2f,
    radius: Float,
    width: Float = 1F,
    segments: Int = 0,
    color: ColorRGB
) = renderer2D.drawRoundedRectOutline0(
    start.x.toDouble(),
    start.y.toDouble(),
    end.x.toDouble(),
    end.y.toDouble(),
    radius,
    width,
    segments,
    color
)

@Render2DMark
inline fun Render2DScope.drawArc(
    centerX: Double,
    centerY: Double,
    radius: Float,
    angleRange: Pair<Float, Float>,
    segments: Int = 0,
    color: ColorRGB
) = renderer2D.drawArc0(centerX, centerY, radius, angleRange, segments, color)

@Render2DMark
inline fun Render2DScope.drawArc(
    centerX: Int,
    centerY: Int,
    radius: Float,
    angleRange: Pair<Float, Float>,
    segments: Int = 0,
    color: ColorRGB
) = renderer2D.drawArc0(centerX.toDouble(), centerY.toDouble(), radius, angleRange, segments, color)

@Render2DMark
inline fun Render2DScope.drawArc(
    centerX: Float,
    centerY: Float,
    radius: Float,
    angleRange: Pair<Float, Float>,
    segments: Int = 0,
    color: ColorRGB
) = renderer2D.drawArc0(centerX.toDouble(), centerY.toDouble(), radius, angleRange, segments, color)

@Render2DMark
inline fun Render2DScope.drawArc(
    center: Vec2d,
    radius: Float,
    angleRange: Pair<Float, Float>,
    segments: Int = 0,
    color: ColorRGB
) = renderer2D.drawArc0(center.x, center.y, radius, angleRange, segments, color)

@Render2DMark
inline fun Render2DScope.drawArc(
    center: Vec2f,
    radius: Float,
    angleRange: Pair<Float, Float>,
    segments: Int = 0,
    color: ColorRGB
) = renderer2D.drawArc0(center.x.toDouble(), center.y.toDouble(), radius, angleRange, segments, color)

@Render2DMark
inline fun Render2DScope.drawArcOutline(
    centerX: Double,
    centerY: Double,
    radius: Float,
    angleRange: Pair<Float, Float>,
    segments: Int = 0,
    lineWidth: Float = 1f,
    color: ColorRGB
) = renderer2D.drawLinesStrip0(
    Renderer2D.getArcVertices(centerX, centerY, radius, angleRange, segments),
    lineWidth,
    color
)

@Render2DMark
inline fun Render2DScope.drawArcOutline(
    centerX: Int,
    centerY: Int,
    radius: Float,
    angleRange: Pair<Float, Float>,
    segments: Int = 0,
    lineWidth: Float = 1f,
    color: ColorRGB
) = renderer2D.drawLinesStrip0(
    Renderer2D.getArcVertices(centerX.toDouble(), centerY.toDouble(), radius, angleRange, segments),
    lineWidth,
    color
)

@Render2DMark
inline fun Render2DScope.drawArcOutline(
    centerX: Float,
    centerY: Float,
    radius: Float,
    angleRange: Pair<Float, Float>,
    segments: Int = 0,
    lineWidth: Float = 1f,
    color: ColorRGB
) = renderer2D.drawLinesStrip0(
    Renderer2D.getArcVertices(centerX.toDouble(), centerY.toDouble(), radius, angleRange, segments),
    lineWidth,
    color
)

@Render2DMark
inline fun Render2DScope.drawArcOutline(
    center: Vec2d,
    radius: Float,
    angleRange: Pair<Float, Float>,
    segments: Int = 0,
    lineWidth: Float = 1f,
    color: ColorRGB
) = renderer2D.drawLinesStrip0(
    Renderer2D.getArcVertices(center.x, center.y, radius, angleRange, segments),
    lineWidth,
    color
)

@Render2DMark
inline fun Render2DScope.drawArcOutline(
    center: Vec2f,
    radius: Float,
    angleRange: Pair<Float, Float>,
    segments: Int = 0,
    lineWidth: Float = 1f,
    color: ColorRGB
) = renderer2D.drawLinesStrip0(
    Renderer2D.getArcVertices(center.x.toDouble(), center.y.toDouble(), radius, angleRange, segments),
    lineWidth,
    color
)

/**
 * Point
 */
@Render2DMark
inline fun I2DRenderer.drawPoint(
    x: Double,
    y: Double,
    size: Float = 1F,
    color: ColorRGB
) = this.drawPoint0(x, y, size, color)

@Render2DMark
inline fun I2DRenderer.drawPoint(
    x: Int,
    y: Int,
    size: Float = 1F,
    color: ColorRGB
) = this.drawPoint0(x.toDouble(), y.toDouble(), size, color)

@Render2DMark
inline fun I2DRenderer.drawPoint(
    x: Float,
    y: Float,
    size: Float = 1F,
    color: ColorRGB
) = this.drawPoint0(x.toDouble(), y.toDouble(), size, color)

/**
 * Line
 */
@Render2DMark
inline fun I2DRenderer.drawLine(
    startX: Double,
    startY: Double,
    endX: Double,
    endY: Double,
    width: Float = 1F,
    color1: ColorRGB,
    color2: ColorRGB = color1
) = this.drawLine0(startX, startY, endX, endY, width, color1, color2)

@Render2DMark
inline fun I2DRenderer.drawLine(
    startX: Int,
    startY: Int,
    endX: Int,
    endY: Int,
    width: Float = 1F,
    color1: ColorRGB,
    color2: ColorRGB = color1
) = this.drawLine0(startX.toDouble(), startY.toDouble(), endX.toDouble(), endY.toDouble(), width, color1, color2)

@Render2DMark
inline fun I2DRenderer.drawLine(
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float,
    width: Float = 1F,
    color1: ColorRGB,
    color2: ColorRGB = color1
) = this.drawLine0(startX.toDouble(), startY.toDouble(), endX.toDouble(), endY.toDouble(), width, color1, color2)

@Render2DMark
inline fun I2DRenderer.drawLine(
    start: Vec2d,
    end: Vec2d,
    width: Float = 1F,
    color1: ColorRGB,
    color2: ColorRGB = color1
) = this.drawLine0(start.x, start.y, end.x, end.y, width, color1, color2)

@Render2DMark
inline fun I2DRenderer.drawLine(
    start: Vec2f,
    end: Vec2f,
    width: Float = 1F,
    color1: ColorRGB,
    color2: ColorRGB = color1
) = this.drawLine0(
    start.x.toDouble(),
    start.y.toDouble(),
    end.x.toDouble(),
    end.y.toDouble(),
    width,
    color1,
    color2
)

@Render2DMark
inline fun I2DRenderer.drawLinesStrip(
    vertexArray: Array<Vec2d>,
    width: Float = 1F,
    color: ColorRGB
) = this.drawLinesStrip0(vertexArray, width, color)

@Render2DMark
inline fun I2DRenderer.drawLinesLoop(
    vertexArray: Array<Vec2d>,
    width: Float = 1F,
    color: ColorRGB
) = this.drawLinesLoop0(vertexArray, width, color)

@Render2DMark
inline fun I2DRenderer.drawLinesStrip(
    vertexArray: Array<Vec2f>,
    width: Float = 1F,
    color: ColorRGB
) = this.drawLinesStrip0(vertexArray.map { it.toVec2d() }, width, color)

@Render2DMark
inline fun I2DRenderer.drawLinesLoop(
    vertexArray: Array<Vec2f>,
    width: Float = 1F,
    color: ColorRGB
) = this.drawLinesLoop0(vertexArray.map { it.toVec2d() }, width, color)

/**
 * Triangle
 */
@Render2DMark
inline fun I2DRenderer.drawTriangle(
    pos1X: Double,
    pos1Y: Double,
    pos2X: Double,
    pos2Y: Double,
    pos3X: Double,
    pos3Y: Double,
    color: ColorRGB
) = this.drawTriangle0(pos1X, pos1Y, pos2X, pos2Y, pos3X, pos3Y, color)

@Render2DMark
inline fun I2DRenderer.drawTriangle(
    pos1X: Int,
    pos1Y: Int,
    pos2X: Int,
    pos2Y: Int,
    pos3X: Int,
    pos3Y: Int,
    color: ColorRGB
) = this.drawTriangle0(
    pos1X.toDouble(),
    pos1Y.toDouble(),
    pos2X.toDouble(),
    pos2Y.toDouble(),
    pos3X.toDouble(),
    pos3Y.toDouble(),
    color
)

@Render2DMark
inline fun I2DRenderer.drawTriangle(
    pos1X: Float,
    pos1Y: Float,
    pos2X: Float,
    pos2Y: Float,
    pos3X: Float,
    pos3Y: Float,
    color: ColorRGB
) = this.drawTriangle0(
    pos1X.toDouble(),
    pos1Y.toDouble(),
    pos2X.toDouble(),
    pos2Y.toDouble(),
    pos3X.toDouble(),
    pos3Y.toDouble(),
    color
)

@Render2DMark
inline fun I2DRenderer.drawTriangle(
    pos1: Vec2d,
    pos2: Vec2d,
    pos3: Vec2d,
    color: ColorRGB
) = this.drawTriangle0(
    pos1.x,
    pos1.y,
    pos2.x,
    pos2.y,
    pos3.x,
    pos3.y,
    color
)

@Render2DMark
inline fun I2DRenderer.drawTriangle(
    pos1: Vec2f,
    pos2: Vec2f,
    pos3: Vec2f,
    color: ColorRGB
) = this.drawTriangle0(
    pos1.x.toDouble(),
    pos1.y.toDouble(),
    pos2.x.toDouble(),
    pos2.y.toDouble(),
    pos3.x.toDouble(),
    pos3.y.toDouble(),
    color
)

//Triangle outline
@Render2DMark
inline fun I2DRenderer.drawTriangleOutline(
    pos1X: Double,
    pos1Y: Double,
    pos2X: Double,
    pos2Y: Double,
    pos3X: Double,
    pos3Y: Double,
    width: Float = 1F,
    color: ColorRGB
) = this.drawTriangleOutline0(pos1X, pos1Y, pos2X, pos2Y, pos3X, pos3Y, width, color)

@Render2DMark
inline fun I2DRenderer.drawTriangleOutline(
    pos1X: Int,
    pos1Y: Int,
    pos2X: Int,
    pos2Y: Int,
    pos3X: Int,
    pos3Y: Int,
    width: Float = 1F,
    color: ColorRGB
) = this.drawTriangleOutline0(
    pos1X.toDouble(),
    pos1Y.toDouble(),
    pos2X.toDouble(),
    pos2Y.toDouble(),
    pos3X.toDouble(),
    pos3Y.toDouble(),
    width,
    color
)

@Render2DMark
inline fun I2DRenderer.drawTriangleOutline(
    pos1X: Float,
    pos1Y: Float,
    pos2X: Float,
    pos2Y: Float,
    pos3X: Float,
    pos3Y: Float,
    width: Float = 1F,
    color: ColorRGB
) = this.drawTriangleOutline0(
    pos1X.toDouble(),
    pos1Y.toDouble(),
    pos2X.toDouble(),
    pos2Y.toDouble(),
    pos3X.toDouble(),
    pos3Y.toDouble(),
    width,
    color
)

@Render2DMark
inline fun I2DRenderer.drawTriangleOutline(
    pos1: Vec2d,
    pos2: Vec2d,
    pos3: Vec2d,
    width: Float = 1F,
    color: ColorRGB
) = this.drawTriangleOutline0(
    pos1.x,
    pos1.y,
    pos2.x,
    pos2.y,
    pos3.x,
    pos3.y,
    width,
    color
)

@Render2DMark
inline fun I2DRenderer.drawTriangleOutline(
    pos1: Vec2f,
    pos2: Vec2f,
    pos3: Vec2f,
    width: Float = 1F,
    color: ColorRGB
) = this.drawTriangleOutline0(
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
@Render2DMark
inline fun I2DRenderer.drawRect(
    startX: Double,
    startY: Double,
    endX: Double,
    endY: Double,
    color: ColorRGB
) = this.drawRect0(startX, startY, endX, endY, color)

@Render2DMark
inline fun I2DRenderer.drawRect(
    startX: Int,
    startY: Int,
    endX: Int,
    endY: Int,
    color: ColorRGB
) = this.drawRect0(startX.toDouble(), startY.toDouble(), endX.toDouble(), endY.toDouble(), color)

@Render2DMark
inline fun I2DRenderer.drawRect(
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float,
    color: ColorRGB
) = this.drawRect0(startX.toDouble(), startY.toDouble(), endX.toDouble(), endY.toDouble(), color)

@Render2DMark
inline fun I2DRenderer.drawRect(
    start: Vec2d,
    end: Vec2d,
    color: ColorRGB
) = this.drawRect0(start.x, start.y, end.x, end.y, color)

@Render2DMark
inline fun I2DRenderer.drawRect(
    start: Vec2f,
    end: Vec2f,
    color: ColorRGB
) = this.drawRect0(start.x.toDouble(), start.y.toDouble(), end.x.toDouble(), end.y.toDouble(), color)

//Gradient Rectangle
// color2  color1
// color3  color4
@Render2DMark
inline fun I2DRenderer.drawGradientRect(
    startX: Double,
    startY: Double,
    endX: Double,
    endY: Double,
    color1: ColorRGB,
    color2: ColorRGB,
    color3: ColorRGB,
    color4: ColorRGB
) = this.drawGradientRect0(startX, startY, endX, endY, color1, color2, color3, color4)

@Render2DMark
inline fun I2DRenderer.drawGradientRect(
    startX: Int,
    startY: Int,
    endX: Int,
    endY: Int,
    color1: ColorRGB,
    color2: ColorRGB,
    color3: ColorRGB,
    color4: ColorRGB
) = this.drawGradientRect0(
    startX.toDouble(),
    startY.toDouble(),
    endX.toDouble(),
    endY.toDouble(),
    color1,
    color2,
    color3,
    color4
)

@Render2DMark
inline fun I2DRenderer.drawGradientRect(
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float,
    color1: ColorRGB,
    color2: ColorRGB,
    color3: ColorRGB,
    color4: ColorRGB
) = this.drawGradientRect0(
    startX.toDouble(),
    startY.toDouble(),
    endX.toDouble(),
    endY.toDouble(),
    color1,
    color2,
    color3,
    color4
)

@Render2DMark
inline fun I2DRenderer.drawGradientRect(
    start: Vec2d,
    end: Vec2d,
    color1: ColorRGB,
    color2: ColorRGB,
    color3: ColorRGB,
    color4: ColorRGB
) = this.drawGradientRect0(start.x, start.y, end.x, end.y, color1, color2, color3, color4)

@Render2DMark
inline fun I2DRenderer.drawGradientRect(
    start: Vec2f,
    end: Vec2f,
    color1: ColorRGB,
    color2: ColorRGB,
    color3: ColorRGB,
    color4: ColorRGB
) = this.drawGradientRect0(
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
@Render2DMark
inline fun I2DRenderer.drawHorizontalRect(
    startX: Double,
    startY: Double,
    endX: Double,
    endY: Double,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = this.drawGradientRect0(startX, startY, endX, endY, endColorRGB, startColor, startColor, endColorRGB)

@Render2DMark
inline fun I2DRenderer.drawHorizontalRect(
    startX: Int,
    startY: Int,
    endX: Int,
    endY: Int,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = this.drawGradientRect0(
    startX.toDouble(),
    startY.toDouble(),
    endX.toDouble(),
    endY.toDouble(),
    endColorRGB,
    startColor,
    startColor,
    endColorRGB
)

@Render2DMark
inline fun I2DRenderer.drawHorizontalRect(
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = this.drawGradientRect0(
    startX.toDouble(),
    startY.toDouble(),
    endX.toDouble(),
    endY.toDouble(),
    endColorRGB,
    startColor,
    startColor,
    endColorRGB
)

@Render2DMark
inline fun I2DRenderer.drawHorizontalRect(
    start: Vec2d,
    end: Vec2d,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = this.drawGradientRect0(
    start.x,
    start.y,
    end.x,
    end.y,
    endColorRGB,
    startColor,
    startColor,
    endColorRGB
)

@Render2DMark
inline fun I2DRenderer.drawHorizontalRect(
    start: Vec2f,
    end: Vec2f,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = this.drawGradientRect0(
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
@Render2DMark
inline fun I2DRenderer.drawVerticalRect(
    startX: Double,
    startY: Double,
    endX: Double,
    endY: Double,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = this.drawGradientRect0(startX, startY, endX, endY, startColor, startColor, endColorRGB, endColorRGB)

@Render2DMark
inline fun I2DRenderer.drawVerticalRect(
    startX: Int,
    startY: Int,
    endX: Int,
    endY: Int,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = this.drawGradientRect0(
    startX.toDouble(),
    startY.toDouble(),
    endX.toDouble(),
    endY.toDouble(),
    startColor,
    startColor,
    endColorRGB,
    endColorRGB
)

@Render2DMark
inline fun I2DRenderer.drawVerticalRect(
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = this.drawGradientRect0(
    startX.toDouble(),
    startY.toDouble(),
    endX.toDouble(),
    endY.toDouble(),
    startColor,
    startColor,
    endColorRGB,
    endColorRGB
)

@Render2DMark
inline fun I2DRenderer.drawVerticalRect(
    start: Vec2d,
    end: Vec2d,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = this.drawGradientRect0(
    start.x,
    start.y,
    end.x,
    end.y,
    startColor,
    startColor,
    endColorRGB,
    endColorRGB
)

@Render2DMark
inline fun I2DRenderer.drawVerticalRect(
    start: Vec2f,
    end: Vec2f,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = this.drawGradientRect0(
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
@Render2DMark
inline fun I2DRenderer.drawRectOutline(
    startX: Double,
    startY: Double,
    endX: Double,
    endY: Double,
    width: Float = 1F,
    color1: ColorRGB,
    color2: ColorRGB = color1,
    color3: ColorRGB = color1,
    color4: ColorRGB = color1
) = this.drawRectOutline0(startX, startY, endX, endY, width, color1, color2, color3, color4)

@Render2DMark
inline fun I2DRenderer.drawRectOutline(
    startX: Int,
    startY: Int,
    endX: Int,
    endY: Int,
    width: Float = 1F,
    color1: ColorRGB,
    color2: ColorRGB = color1,
    color3: ColorRGB = color1,
    color4: ColorRGB = color1
) = this.drawRectOutline0(
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

@Render2DMark
inline fun I2DRenderer.drawRectOutline(
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float,
    width: Float = 1F,
    color1: ColorRGB,
    color2: ColorRGB = color1,
    color3: ColorRGB = color1,
    color4: ColorRGB = color1
) = this.drawRectOutline0(
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

@Render2DMark
inline fun I2DRenderer.drawRectOutline(
    start: Vec2d,
    end: Vec2d,
    width: Float = 1F,
    color1: ColorRGB,
    color2: ColorRGB = color1,
    color3: ColorRGB = color1,
    color4: ColorRGB = color1
) = this.drawRectOutline0(start.x, start.y, end.x, end.y, width, color1, color2, color3, color4)

@Render2DMark
inline fun I2DRenderer.drawRectOutline(
    start: Vec2f,
    end: Vec2f,
    width: Float = 1F,
    color1: ColorRGB,
    color2: ColorRGB = color1,
    color3: ColorRGB = color1,
    color4: ColorRGB = color1
) = this.drawRectOutline0(
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
@Render2DMark
inline fun I2DRenderer.drawHorizontalRectOutline(
    startX: Double,
    startY: Double,
    endX: Double,
    endY: Double,
    width: Float = 1F,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = this.drawRectOutline0(startX, startY, endX, endY, width, endColorRGB, startColor, startColor, endColorRGB)

@Render2DMark
inline fun I2DRenderer.drawHorizontalRectOutline(
    startX: Int,
    startY: Int,
    endX: Int,
    endY: Int,
    width: Float = 1F,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = this.drawRectOutline0(
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

@Render2DMark
inline fun I2DRenderer.drawHorizontalRectOutline(
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float,
    width: Float = 1F,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = this.drawRectOutline0(
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

@Render2DMark
inline fun I2DRenderer.drawHorizontalRectOutline(
    start: Vec2d,
    end: Vec2d,
    width: Float = 1F,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = this.drawRectOutline0(
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

@Render2DMark
inline fun I2DRenderer.drawHorizontalRectOutline(
    start: Vec2f,
    end: Vec2f,
    width: Float = 1F,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = this.drawRectOutline0(
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
@Render2DMark
inline fun I2DRenderer.drawVerticalRectOutline(
    startX: Double,
    startY: Double,
    endX: Double,
    endY: Double,
    width: Float = 1F,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = this.drawRectOutline0(startX, startY, endX, endY, width, startColor, startColor, endColorRGB, endColorRGB)

@Render2DMark
inline fun I2DRenderer.drawVerticalRectOutline(
    startX: Int,
    startY: Int,
    endX: Int,
    endY: Int,
    width: Float = 1F,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = this.drawRectOutline0(
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

@Render2DMark
inline fun I2DRenderer.drawVerticalRectOutline(
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float,
    width: Float = 1F,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = this.drawRectOutline0(
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

@Render2DMark
inline fun I2DRenderer.drawVerticalRectOutline(
    start: Vec2d,
    end: Vec2d,
    width: Float = 1F,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = this.drawRectOutline0(
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

@Render2DMark
inline fun I2DRenderer.drawVerticalRectOutline(
    start: Vec2f,
    end: Vec2f,
    width: Float = 1F,
    startColor: ColorRGB,
    endColorRGB: ColorRGB,
) = this.drawRectOutline0(
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
@Render2DMark
inline fun I2DRenderer.drawRoundedRect(
    startX: Double,
    startY: Double,
    endX: Double,
    endY: Double,
    radius: Float,
    segments: Int = 0,
    color: ColorRGB
) = this.drawRoundedRect0(startX, startY, endX, endY, radius, segments, color)

@Render2DMark
inline fun I2DRenderer.drawRoundedRect(
    startX: Int,
    startY: Int,
    endX: Int,
    endY: Int,
    radius: Float,
    segments: Int = 0,
    color: ColorRGB
) = this.drawRoundedRect0(
    startX.toDouble(),
    startY.toDouble(),
    endX.toDouble(),
    endY.toDouble(),
    radius,
    segments,
    color
)

@Render2DMark
inline fun I2DRenderer.drawRoundedRect(
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float,
    radius: Float,
    segments: Int = 0,
    color: ColorRGB
) = this.drawRoundedRect0(
    startX.toDouble(),
    startY.toDouble(),
    endX.toDouble(),
    endY.toDouble(),
    radius,
    segments,
    color
)

@Render2DMark
inline fun I2DRenderer.drawRoundedRect(
    start: Vec2d,
    end: Vec2d,
    radius: Float,
    segments: Int = 0,
    color: ColorRGB
) = this.drawRoundedRect0(
    start.x,
    start.y,
    end.x,
    end.y,
    radius,
    segments,
    color
)

@Render2DMark
inline fun I2DRenderer.drawRoundedRect(
    start: Vec2f,
    end: Vec2f,
    radius: Float,
    segments: Int = 0,
    color: ColorRGB
) = this.drawRoundedRect0(
    start.x.toDouble(),
    start.y.toDouble(),
    end.x.toDouble(),
    end.y.toDouble(),
    radius,
    segments,
    color
)

@Render2DMark
inline fun I2DRenderer.drawRoundedRectOutline(
    startX: Double,
    startY: Double,
    endX: Double,
    endY: Double,
    radius: Float,
    width: Float = 1F,
    segments: Int = 0,
    color: ColorRGB
) = this.drawRoundedRectOutline0(startX, startY, endX, endY, radius, width, segments, color)

@Render2DMark
inline fun I2DRenderer.drawRoundedRectOutline(
    startX: Int,
    startY: Int,
    endX: Int,
    endY: Int,
    radius: Float,
    width: Float = 1F,
    segments: Int = 0,
    color: ColorRGB
) = this.drawRoundedRectOutline0(
    startX.toDouble(),
    startY.toDouble(),
    endX.toDouble(),
    endY.toDouble(),
    radius,
    width,
    segments,
    color
)

@Render2DMark
inline fun I2DRenderer.drawRoundedRectOutline(
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float,
    radius: Float,
    width: Float = 1F,
    segments: Int = 0,
    color: ColorRGB
) = this.drawRoundedRectOutline0(
    startX.toDouble(),
    startY.toDouble(),
    endX.toDouble(),
    endY.toDouble(),
    radius,
    width,
    segments,
    color
)

@Render2DMark
inline fun I2DRenderer.drawRoundedRectOutline(
    start: Vec2d,
    end: Vec2d,
    radius: Float,
    width: Float = 1F,
    segments: Int = 0,
    color: ColorRGB
) = this.drawRoundedRectOutline0(
    start.x,
    start.y,
    end.x,
    end.y,
    radius,
    width,
    segments,
    color
)

@Render2DMark
inline fun I2DRenderer.drawRoundedRectOutline(
    start: Vec2f,
    end: Vec2f,
    radius: Float,
    width: Float = 1F,
    segments: Int = 0,
    color: ColorRGB
) = this.drawRoundedRectOutline0(
    start.x.toDouble(),
    start.y.toDouble(),
    end.x.toDouble(),
    end.y.toDouble(),
    radius,
    width,
    segments,
    color
)

@Render2DMark
inline fun I2DRenderer.drawArc(
    centerX: Double,
    centerY: Double,
    radius: Float,
    angleRange: Pair<Float, Float>,
    segments: Int = 0,
    color: ColorRGB
) = this.drawArc0(centerX, centerY, radius, angleRange, segments, color)

@Render2DMark
inline fun I2DRenderer.drawArc(
    centerX: Int,
    centerY: Int,
    radius: Float,
    angleRange: Pair<Float, Float>,
    segments: Int = 0,
    color: ColorRGB
) = this.drawArc0(centerX.toDouble(), centerY.toDouble(), radius, angleRange, segments, color)

@Render2DMark
inline fun I2DRenderer.drawArc(
    centerX: Float,
    centerY: Float,
    radius: Float,
    angleRange: Pair<Float, Float>,
    segments: Int = 0,
    color: ColorRGB
) = this.drawArc0(centerX.toDouble(), centerY.toDouble(), radius, angleRange, segments, color)

@Render2DMark
inline fun I2DRenderer.drawArc(
    center: Vec2d,
    radius: Float,
    angleRange: Pair<Float, Float>,
    segments: Int = 0,
    color: ColorRGB
) = this.drawArc0(center.x, center.y, radius, angleRange, segments, color)

@Render2DMark
inline fun I2DRenderer.drawArc(
    center: Vec2f,
    radius: Float,
    angleRange: Pair<Float, Float>,
    segments: Int = 0,
    color: ColorRGB
) = this.drawArc0(center.x.toDouble(), center.y.toDouble(), radius, angleRange, segments, color)

@Render2DMark
inline fun I2DRenderer.drawArcOutline(
    centerX: Double,
    centerY: Double,
    radius: Float,
    angleRange: Pair<Float, Float>,
    segments: Int = 0,
    lineWidth: Float = 1f,
    color: ColorRGB
) = this.drawLinesStrip0(Renderer2D.getArcVertices(centerX, centerY, radius, angleRange, segments), lineWidth, color)

@Render2DMark
inline fun I2DRenderer.drawArcOutline(
    centerX: Int,
    centerY: Int,
    radius: Float,
    angleRange: Pair<Float, Float>,
    segments: Int = 0,
    lineWidth: Float = 1f,
    color: ColorRGB
) = this.drawLinesStrip0(
    Renderer2D.getArcVertices(centerX.toDouble(), centerY.toDouble(), radius, angleRange, segments),
    lineWidth,
    color
)

@Render2DMark
inline fun I2DRenderer.drawArcOutline(
    centerX: Float,
    centerY: Float,
    radius: Float,
    angleRange: Pair<Float, Float>,
    segments: Int = 0,
    lineWidth: Float = 1f,
    color: ColorRGB
) = this.drawLinesStrip0(
    Renderer2D.getArcVertices(centerX.toDouble(), centerY.toDouble(), radius, angleRange, segments),
    lineWidth,
    color
)

@Render2DMark
inline fun I2DRenderer.drawArcOutline(
    center: Vec2d,
    radius: Float,
    angleRange: Pair<Float, Float>,
    segments: Int = 0,
    lineWidth: Float = 1f,
    color: ColorRGB
) = this.drawLinesStrip0(Renderer2D.getArcVertices(center.x, center.y, radius, angleRange, segments), lineWidth, color)

@Render2DMark
inline fun I2DRenderer.drawArcOutline(
    center: Vec2f,
    radius: Float,
    angleRange: Pair<Float, Float>,
    segments: Int = 0,
    lineWidth: Float = 1f,
    color: ColorRGB
) = this.drawLinesStrip0(
    Renderer2D.getArcVertices(center.x.toDouble(), center.y.toDouble(), radius, angleRange, segments),
    lineWidth,
    color
)