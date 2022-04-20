package net.spartanb312.render.features.manager

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import net.spartanb312.render.core.event.inner.listener
import net.spartanb312.render.features.common.Render2DObject
import net.spartanb312.render.features.event.render.Render2DEvent
import net.spartanb312.render.features.hud.HUDModule
import net.spartanb312.render.features.manager.functional.WindowBlurShader
import net.spartanb312.render.graphics.impl.FontRenderer
import net.spartanb312.render.graphics.impl.Render2DScope
import net.spartanb312.render.graphics.impl.Renderer2D
import net.spartanb312.render.graphics.impl.area
import net.spartanb312.render.util.Helper
import org.lwjgl.input.Mouse

object Render2DManager : Helper {

    private val registered2DRenderers = mutableListOf<Render2DObject>()
    private val mutex = Mutex()

    init {
        listener<Render2DEvent>(Int.MAX_VALUE, true) { event ->
            runBlocking {
                mutex.withLock {
                    val renderScope = Render2DScope(
                        mouseX = Mouse.getX() * event.resolution.scaledWidth / mc.displayWidth,
                        mouseY = event.resolution.scaledHeight - Mouse.getY() * event.resolution.scaledHeight / mc.displayHeight - 1,
                        scaledResolution = event.resolution,
                        renderer2D = Renderer2D.renderer,
                        fontRenderer = FontRenderer.renderer
                    )
                    registered2DRenderers.forEach {
                        with(it) {
                            if (it is HUDModule) {
                                renderScope.area(it.x, it.y, it.x + it.width, it.y + it.height) {
                                    onRender()
                                }
                            } else renderScope.onRender()
                        }
                    }
                }
            }
        }
        WindowBlurShader
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