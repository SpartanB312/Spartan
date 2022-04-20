package net.spartanb312.render.graphics.api.texture

import net.minecraft.client.audio.SoundManager
import net.minecraft.client.renderer.GLAllocation
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL12
import java.awt.image.BufferedImage

object ImageUtil {

    private val buffer = GLAllocation.createDirectIntBuffer(0x8000000)

    fun uploadImage(bufferedImage: BufferedImage, format: Int, width: Int, height: Int) {
        val data = IntArray(width * height)
        bufferedImage.getRGB(0, 0, width, height, data, 0, width)
        buffer.put(data)
        buffer.flip()
        GL11.glTexImage2D(
            GL11.GL_TEXTURE_2D,
            0,
            format,
            width,
            height,
            0,
            GL12.GL_BGRA,
            GL12.GL_UNSIGNED_INT_8_8_8_8_REV,
            buffer
        )
        buffer.clear()
    }

}