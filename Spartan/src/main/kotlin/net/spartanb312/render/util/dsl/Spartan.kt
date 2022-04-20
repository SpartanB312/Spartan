package net.spartanb312.render.util.dsl

import net.spartanb312.render.core.event.inner.listener
import net.spartanb312.render.core.event.inner.parallelListener
import net.spartanb312.render.features.event.client.AsyncTickEvent
import net.spartanb312.render.features.event.client.ClientTickEvent
import net.spartanb312.render.features.event.client.RenderTickEvent
import net.spartanb312.render.features.event.render.Render2DEvent
import net.spartanb312.render.features.event.render.Render3DEvent
import net.spartanb312.render.features.common.SafeScope
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
inline fun <R> runSafe(block: SafeScope.() -> R): R? {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }

    val instance = SafeScope.instance
    return instance?.block()
}

suspend fun <R> runSafeSuspend(block: suspend SafeScope.() -> R): R? = SafeScope.instance?.block()

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