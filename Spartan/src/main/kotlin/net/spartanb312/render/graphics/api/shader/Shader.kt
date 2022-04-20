package net.spartanb312.render.graphics.api.shader

import net.spartanb312.render.graphics.impl.GLStateManager
import net.spartanb312.render.launch.Logger
import net.spartanb312.render.launch.ResourceCenter
import net.spartanb312.render.util.Helper
import org.lwjgl.opengl.GL20.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

open class Shader(
    vsh: String,
    fsh: String
) : GLObject, Helper {

    final override val id = glCreateProgram().also { id ->
        val vshID = createShader(vsh, GL_VERTEX_SHADER)
        val fshID = createShader(fsh, GL_FRAGMENT_SHADER)

        glAttachShader(id, vshID)
        glAttachShader(id, fshID)
        glLinkProgram(id)

        if (glGetProgrami(id, GL_LINK_STATUS) == 0) {
            Logger.error("Failed to link shader " + glGetProgramInfoLog(id, 1024))
            glDeleteProgram(id)
            glDeleteShader(vshID)
            glDeleteShader(fshID)
            return@also
        }

        glDetachShader(id, vshID)
        glDetachShader(id, fshID)
        glDeleteShader(vshID)
        glDeleteShader(fshID)
    }

    private fun createShader(path: String, shaderType: Int): Int {
        val srcString = ResourceCenter.getBytes(path)!!.decodeToString()
        val shaderId = glCreateShader(shaderType)

        glShaderSource(shaderId, srcString)
        glCompileShader(shaderId)

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            glDeleteShader(shaderId)
            throw IllegalStateException("Failed to compile shader $path")
        }
        return shaderId
    }

    override fun bind() = GLStateManager.useProgram(id)

    override fun unbind() = GLStateManager.useProgram(0)

    override fun destroy() = glDeleteProgram(id)

}

@OptIn(ExperimentalContracts::class)
inline fun <T : Shader> T.use(block: T.() -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    bind()
    block.invoke(this)
    GLStateManager.useProgram(0, force = true)
}