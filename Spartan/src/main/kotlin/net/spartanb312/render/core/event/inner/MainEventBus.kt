package net.spartanb312.render.core.event.inner

import net.spartanb312.render.core.event.DEFAULT_LISTENER_PRIORITY
import net.spartanb312.render.core.event.EventBus
import java.util.function.Consumer

object MainEventBus : EventBus()

internal inline fun <reified E : Any> Any.listener(
    function: Consumer<E>,
) = net.spartanb312.render.core.event.listener(
    MainEventBus,
    this,
    E::class.java,
    DEFAULT_LISTENER_PRIORITY,
    false,
    function
)

internal inline fun <reified E : Any> Any.listener(
    priority: Int,
    function: Consumer<E>,
) = net.spartanb312.render.core.event.listener(MainEventBus, this, E::class.java, priority, false, function)

internal inline fun <reified E : Any> Any.listener(
    alwaysListening: Boolean,
    function: Consumer<E>,
) = net.spartanb312.render.core.event.listener(
    MainEventBus,
    this,
    E::class.java,
    DEFAULT_LISTENER_PRIORITY,
    alwaysListening,
    function
)

internal inline fun <reified E : Any> Any.listener(
    priority: Int,
    alwaysListening: Boolean,
    function: Consumer<E>,
) = net.spartanb312.render.core.event.listener(MainEventBus, this, E::class.java, priority, alwaysListening, function)

internal inline fun <reified E : Any> Any.parallelListener(
    noinline function: suspend (E) -> Unit,
) = net.spartanb312.render.core.event.parallelListener(MainEventBus, this, E::class.java, false, function)

internal inline fun <reified E : Any> Any.parallelListener(
    alwaysListening: Boolean,
    noinline function: suspend (E) -> Unit,
) = net.spartanb312.render.core.event.parallelListener(MainEventBus, this, E::class.java, alwaysListening, function)

internal inline fun <reified E : Any> Any.concurrentListener(
    noinline function: suspend (E) -> Unit,
) = net.spartanb312.render.core.event.concurrentListener(MainEventBus, this, E::class.java, false, function)

internal inline fun <reified E : Any> Any.concurrentListener(
    alwaysListening: Boolean,
    noinline function: suspend (E) -> Unit,
) = net.spartanb312.render.core.event.concurrentListener(MainEventBus, this, E::class.java, alwaysListening, function)