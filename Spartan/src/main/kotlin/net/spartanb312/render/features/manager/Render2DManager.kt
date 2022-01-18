package net.spartanb312.render.features.manager

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import net.spartanb312.render.core.event.inner.listener
import net.spartanb312.render.features.common.Render2DObject
import net.spartanb312.render.features.event.render.Render2DEvent
import net.spartanb312.render.graphics.impl.Render2DScope
import net.spartanb312.render.util.Helper
import org.lwjgl.input.Mouse

object Render2DManager : Helper {

    private val registered2DRenderers = mutableListOf<Render2DObject>()
    private val mutex = Mutex()

    init {
        listener<Render2DEvent> { event ->
            runBlocking {
                mutex.withLock {
                    registered2DRenderers.forEach {
                        with(it) {
                            Render2DScope(
                                Mouse.getX() * event.resolution.scaledWidth / mc.displayWidth,
                                event.resolution.scaledHeight - Mouse.getY() * event.resolution.scaledHeight / mc.displayHeight - 1,
                                event.resolution
                            ).onRender()
                        }
                    }
                }
            }
        }
    }

    fun register(obj: Render2DObject) = runBlocking {
        mutex.withLock {
            if (!registered2DRenderers.contains(obj)) {
                registered2DRenderers.add(obj)
                registered2DRenderers.sortByDescending { r -> r.renderPriority }
            }
        }
    }

    fun register(objs: Collection<Render2DObject>) = runBlocking {
        mutex.withLock {
            mutableSetOf<Render2DObject>().also {
                it.addAll(registered2DRenderers)
                objs.forEach { obj ->
                    it.add(obj)
                }
                registered2DRenderers.clear()
                registered2DRenderers.addAll(it)
                registered2DRenderers.sortByDescending { r -> r.renderPriority }
            }
        }
    }

}