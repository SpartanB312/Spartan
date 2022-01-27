package net.spartanb312.render.features.event

import net.spartanb312.render.core.event.*
import net.spartanb312.render.core.event.inner.MainEventBus
import net.spartanb312.render.features.manager.ingame.SafeScope
import net.spartanb312.render.util.dsl.runSafe
import net.spartanb312.render.util.dsl.runSafeSuspend

internal inline fun <reified E : Event> EventBus.safeListener(
    eventBus: EventBus = MainEventBus,
    noinline function: SafeScope.(E) -> Unit
) = listener(eventBus, this, E::class.java, DEFAULT_LISTENER_PRIORITY, false) { runSafe { function(it) } }

internal inline fun <reified E : Event> EventBus.safeListener(
    eventBus: EventBus = MainEventBus,
    priority: Int,
    noinline function: SafeScope.(E) -> Unit
) = listener(eventBus, this, E::class.java, priority, false) { runSafe { function(it) } }

internal inline fun <reified E : Event> EventBus.safeListener(
    eventBus: EventBus = MainEventBus,
    alwaysListening: Boolean,
    noinline function: SafeScope.(E) -> Unit
) = listener(eventBus, this, E::class.java, DEFAULT_LISTENER_PRIORITY, alwaysListening) { runSafe { function(it) } }

internal inline fun <reified E : Event> EventBus.safeListener(
    eventBus: EventBus = MainEventBus,
    priority: Int,
    alwaysListening: Boolean,
    noinline function: SafeScope.(E) -> Unit
) = listener(eventBus, this, E::class.java, priority, alwaysListening) { runSafe { function(it) } }


internal inline fun <reified E : Event> EventBus.safeParallelListener(
    eventBus: EventBus = MainEventBus,
    noinline function: suspend SafeScope.(E) -> Unit
) = parallelListener(eventBus, this, E::class.java, false) { runSafeSuspend { function(it) } }

internal inline fun <reified E : Event> EventBus.safeParallelListener(
    eventBus: EventBus = MainEventBus,
    alwaysListening: Boolean,
    noinline function: suspend SafeScope.(E) -> Unit
) = parallelListener(eventBus, this, E::class.java, alwaysListening) { runSafeSuspend { function(it) } }


internal inline fun <reified E : Event> EventBus.safeConcurrentListener(
    eventBus: EventBus = MainEventBus,
    noinline function: suspend SafeScope.(E) -> Unit
) = concurrentListener(eventBus, this, E::class.java, false) { runSafeSuspend { function(it) } }

internal inline fun <reified E : Event> EventBus.safeConcurrentListener(
    eventBus: EventBus = MainEventBus,
    alwaysListening: Boolean,
    noinline function: suspend SafeScope.(E) -> Unit
) = concurrentListener(eventBus, this, E::class.java, alwaysListening) { runSafeSuspend { function(it) } }