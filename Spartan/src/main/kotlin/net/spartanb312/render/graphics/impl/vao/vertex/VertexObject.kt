package net.spartanb312.render.graphics.impl.vao.vertex

import net.minecraft.client.renderer.GLAllocation
import net.spartanb312.render.core.common.extension.ceilToInt
import net.spartanb312.render.core.common.timming.TickTimer
import net.spartanb312.render.core.event.inner.listener
import net.spartanb312.render.features.event.client.GameLoopEvent
import org.lwjgl.opengl.GL11.GL_FLOAT
import org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.glEnableVertexAttribArray
import org.lwjgl.opengl.GL20.glVertexAttribPointer
import org.lwjgl.opengl.GL30.glBindVertexArray
import org.lwjgl.opengl.GL30.glGenVertexArrays
import java.nio.ByteBuffer

@Suppress("LeakingThis")
sealed class VertexObject(private val size: Int, private val builder: () -> Unit) {

    object Pos2Color : VertexObject(12, {
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 12, 0L)
        glVertexAttribPointer(1, 4, GL_UNSIGNED_BYTE, true, 12, 8L)

        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)
    })

    object Pos3Color : VertexObject(16, {
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 16, 0L)
        glVertexAttribPointer(1, 4, GL_UNSIGNED_BYTE, true, 16, 12L)

        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)
    })

    val vaoID = glGenVertexArrays()
    val vboID = glGenBuffers()
    private var currentBufferSize = 64

    init {
        updateBufferSize(64)
        vertexObjects.add(this)
    }

    fun uploadVertex(size: Int) {
        buffer.flip()

        if (size * this.size > currentBufferSize) {
            updateBufferSize((size / 64.0).ceilToInt() * 64)
        }

        useVbo {
            glBufferSubData(GL_ARRAY_BUFFER, 0, buffer)
        }

        buffer.clear()
    }

    fun updateBufferSize(size: Int) {
        if (currentBufferSize == size) return
        else currentBufferSize = size
        useVao {
            useVbo {
                glBufferData(GL_ARRAY_BUFFER, size.toLong(), GL_DYNAMIC_DRAW)
                builder.invoke()
            }
        }
    }

    companion object {
        val buffer: ByteBuffer = GLAllocation.createDirectByteBuffer(0x800000)
        val vertexObjects = mutableSetOf<VertexObject>()

        init {
            val timer = TickTimer()
            listener<GameLoopEvent.Pre>(alwaysListening = true) {
                if (timer.tick(10)) {
                    timer.reset()
                    vertexObjects.forEach { v -> v.updateBufferSize(64) }
                }
            }
        }
    }

}

inline fun VertexObject.useVbo(block: VertexObject.() -> Unit) {
    glBindBuffer(GL_ARRAY_BUFFER, vboID)
    block()
    glBindBuffer(GL_ARRAY_BUFFER, 0)
}

inline fun VertexObject.useVao(block: VertexObject.() -> Unit) {
    glBindVertexArray(vaoID)
    block()
    glBindVertexArray(0)
}