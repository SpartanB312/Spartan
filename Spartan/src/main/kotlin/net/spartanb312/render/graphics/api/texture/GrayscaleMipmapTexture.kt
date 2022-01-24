package net.spartanb312.render.graphics.api.texture

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL12
import org.lwjgl.opengl.GL30

class GrayscaleMipmapTexture(
    data: ByteArray,
    override val width: Int,
    override val height: Int,
    levels: Int
) : AbstractTexture() {

    init {
        useTexture {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MIN_LOD, 0)
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LOD, levels)
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_BASE_LEVEL, 0)
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, levels)

            //Grayscale
            TextureUtils.uploadRed(data, width, height)
            GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D)
        }
    }

}