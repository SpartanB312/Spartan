package net.spartanb312.render.graphics.api.shader

import net.spartanb312.render.graphics.api.MatrixUtils
import org.joml.Matrix4f
import org.lwjgl.opengl.GL20.glGetUniformLocation
import org.lwjgl.opengl.GL20.glUniformMatrix4
import java.nio.FloatBuffer

open class DrawShader(
    vsh: String,
    fsh: String
) : Shader(vsh, fsh) {

    private val projectionUniform = glGetUniformLocation(id, "projection")
    private val modelViewUniform = glGetUniformLocation(id, "modelView")

    fun updateMatrix() {
        updateModelViewMatrix()
        updateProjectionMatrix()
    }

    fun updateProjectionMatrix() {
        MatrixUtils.loadProjectionMatrix().uploadMatrix(projectionUniform)
    }

    fun updateProjectionMatrix(matrix: Matrix4f) {
        MatrixUtils.loadMatrix(matrix).uploadMatrix(projectionUniform)
    }

    fun uploadProjectionMatrix(buffer: FloatBuffer) {
        glUniformMatrix4(projectionUniform, false, buffer)
    }

    fun updateModelViewMatrix() {
        MatrixUtils.loadModelViewMatrix().uploadMatrix(modelViewUniform)
    }

    fun updateModelViewMatrix(matrix: Matrix4f) {
        MatrixUtils.loadMatrix(matrix).uploadMatrix(modelViewUniform)
    }

    fun uploadModelViewMatrix(buffer: FloatBuffer) {
        glUniformMatrix4(modelViewUniform, false, buffer)
    }

}