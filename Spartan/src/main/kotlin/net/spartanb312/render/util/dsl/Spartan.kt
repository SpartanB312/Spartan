package net.spartanb312.render.util.dsl

import net.spartanb312.render.core.event.inner.listener
import net.spartanb312.render.core.event.inner.parallelListener
import net.spartanb312.render.features.event.client.AsyncTickEvent
import net.spartanb312.render.features.event.client.ClientTickEvent
import net.spartanb312.render.features.event.client.RenderTickEvent
import net.spartanb312.render.features.event.render.Render2DEvent
import net.spartanb312.render.features.event.render.Render3DEvent
import net.spartanb312.render.util.mc

fun <T, U> T.runSafe(block: (T) -> U): U? = if (mc.player != null && mc.world != null) block(this) else null

fun Any.onTick(block: ClientTickEvent.() -> Unit) =
    listener<ClientTickEvent> {
        it.block()
    }

fun Any.onRenderTick(block: RenderTickEvent.() -> Unit) =
    listener<RenderTickEvent> {
        it.block()
    }

fun Any.onAsyncTick(block: AsyncTickEvent.() -> Unit) =
    parallelListener<AsyncTickEvent> {
        it.block()
    }

fun Any.onRender(block: Render2DEvent.() -> Unit) =
    listener<Render2DEvent> {
        it.block()
    }

fun Any.onRender3D(block: Render3DEvent.() -> Unit) =
    listener<Render3DEvent> {
        it.block()
    }