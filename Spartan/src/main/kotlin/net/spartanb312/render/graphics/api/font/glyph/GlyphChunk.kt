package net.spartanb312.render.graphics.api.font.glyph

import net.spartanb312.render.graphics.api.texture.GrayscaleMipmapTexture
import org.lwjgl.opengl.GL11.GL_TEXTURE_2D
import org.lwjgl.opengl.GL11.glTexParameterf
import org.lwjgl.opengl.GL14.GL_TEXTURE_LOD_BIAS

class GlyphChunk(
    val id: Int,
    val texture: GrayscaleMipmapTexture,
    val charInfoArray: Array<CharInfo>
) {

    private var lodbias = Float.NaN

    fun updateLodBias(input: Float) {
        if (input != lodbias) {
            lodbias = input
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_LOD_BIAS, input)
        }
    }

    override fun equals(other: Any?) =
        this === other || other is GlyphChunk
                && id == other.id
                && texture == other.texture

    override fun hashCode() = 31 * id + texture.hashCode()

}