package net.spartanb312.render.graphics.impl.legacy.vertex

import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.math.Vec3d
import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.core.common.graphics.ColorUtils.a
import net.spartanb312.render.core.common.graphics.ColorUtils.b
import net.spartanb312.render.core.common.graphics.ColorUtils.g
import net.spartanb312.render.core.common.graphics.ColorUtils.r
import net.spartanb312.render.core.common.math.Vec2d
import net.spartanb312.render.core.common.math.Vec2f
import net.spartanb312.render.core.common.math.Vec3f
import net.spartanb312.render.graphics.impl.Render2DMark

/**
 * Use minecraft buffer builder to draw
 */
object VertexBuffer {

    private val tessellator = Tessellator.getInstance()
    private val buffer = tessellator.buffer

    fun begin(mode: Int) {
        buffer.begin(mode, DefaultVertexFormats.POSITION_COLOR)
    }

    @Render2DMark
    inline fun Int.begin(block: VertexBuffer.() -> Unit) {
        begin(this)
        this@VertexBuffer.block()
        end()
    }

    /**
     * 2D vertex
     */
    fun put(x: Double, y: Double, color: ColorRGB) = buffer
        .pos(x, y, 0.0)
        .color(color.r, color.g, color.b, color.a)
        .endVertex()

    fun put(x: Double, y: Double, color: Int) = buffer
        .pos(x, y, 0.0)
        .color(color.r, color.g, color.b, color.a)
        .endVertex()

    fun put(pos: Vec2d, color: ColorRGB) = buffer
        .pos(pos.x, pos.y, 0.0)
        .color(color.r, color.g, color.b, color.a)
        .endVertex()

    fun put(pos: Vec2d, color: Int) = buffer
        .pos(pos.x, pos.y, 0.0)
        .color(color.r, color.g, color.b, color.a)
        .endVertex()

    fun put(x: Float, y: Float, color: ColorRGB) = buffer
        .pos(x.toDouble(), y.toDouble(), 0.0)
        .color(color.r, color.g, color.b, color.a)
        .endVertex()

    fun put(x: Float, y: Float, color: Int) = buffer
        .pos(x.toDouble(), y.toDouble(), 0.0)
        .color(color.r, color.g, color.b, color.a)
        .endVertex()

    fun put(pos: Vec2f, color: ColorRGB) = buffer
        .pos(pos.x.toDouble(), pos.y.toDouble(), 0.0)
        .color(color.r, color.g, color.b, color.a)
        .endVertex()

    fun put(pos: Vec2f, color: Int) = buffer
        .pos(pos.x.toDouble(), pos.y.toDouble(), 0.0)
        .color(color.r, color.g, color.b, color.a)
        .endVertex()

    fun put(x: Int, y: Int, color: ColorRGB) = buffer
        .pos(x.toDouble(), y.toDouble(), 0.0)
        .color(color.r, color.g, color.b, color.a)
        .endVertex()

    fun put(x: Int, y: Int, color: Int) = buffer
        .pos(x.toDouble(), y.toDouble(), 0.0)
        .color(color.r, color.g, color.b, color.a)
        .endVertex()

    /**
     * 3D vertex
     */
    fun put(x: Double, y: Double, z: Double, color: ColorRGB) = buffer
        .pos(x, y, z)
        .color(color.r, color.g, color.b, color.a)
        .endVertex()

    fun put(x: Double, y: Double, z: Double, color: Int) = buffer
        .pos(x, y, z)
        .color(color.r, color.g, color.b, color.a)
        .endVertex()

    fun put(pos: Vec3d, color: ColorRGB) = buffer
        .pos(pos.x, pos.y, pos.z)
        .color(color.r, color.g, color.b, color.a)
        .endVertex()

    fun put(pos: Vec3d, color: Int) = buffer
        .pos(pos.x, pos.y, pos.z)
        .color(color.r, color.g, color.b, color.a)
        .endVertex()

    fun put(x: Float, y: Float, z: Float, color: ColorRGB) = buffer
        .pos(x.toDouble(), y.toDouble(), z.toDouble())
        .color(color.r, color.g, color.b, color.a)
        .endVertex()

    fun put(x: Float, y: Float, z: Float, color: Int) = buffer
        .pos(x.toDouble(), y.toDouble(), z.toDouble())
        .color(color.r, color.g, color.b, color.a)
        .endVertex()

    fun put(pos: Vec3f, color: ColorRGB) = buffer
        .pos(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble())
        .color(color.r, color.g, color.b, color.a)
        .endVertex()

    fun put(pos: Vec3f, color: Int) = buffer
        .pos(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble())
        .color(color.r, color.g, color.b, color.a)
        .endVertex()

    fun put(x: Int, y: Int, z: Int, color: ColorRGB) = buffer
        .pos(x.toDouble(), y.toDouble(), z.toDouble())
        .color(color.r, color.g, color.b, color.a)
        .endVertex()

    fun put(x: Int, y: Int, z: Int, color: Int) = buffer
        .pos(x.toDouble(), y.toDouble(), z.toDouble())
        .color(color.r, color.g, color.b, color.a)
        .endVertex()

    fun end() = tessellator.draw()

}