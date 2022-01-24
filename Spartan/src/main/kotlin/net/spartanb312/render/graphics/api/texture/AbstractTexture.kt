package net.spartanb312.render.graphics.api.texture

import net.minecraft.client.renderer.GlStateManager
import net.spartanb312.render.graphics.api.exception.NoSuchTextureException
import org.lwjgl.opengl.GL11.glGenTextures

abstract class AbstractTexture {

    val textureID = glGenTextures()

    var available = true
        private set

    abstract val width: Int
    abstract val height: Int

    fun bindTexture() {
        if (available) GlStateManager.bindTexture(textureID)
        else throw NoSuchTextureException("This texture is already deleted!")
    }

    fun unbindTexture() = GlStateManager.bindTexture(0)

    fun deleteTexture() {
        GlStateManager.deleteTexture(textureID)
        available = false
    }

    override fun equals(other: Any?) =
        this === other || other is AbstractTexture
                && this.textureID == other.textureID

    override fun hashCode() = textureID

}