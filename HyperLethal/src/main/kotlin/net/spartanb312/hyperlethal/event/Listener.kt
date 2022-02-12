package net.spartanb312.hyperlethal.event

import kotlinx.coroutines.launch
import net.spartanb312.render.core.event.DEFAULT_LISTENER_PRIORITY
import net.spartanb312.render.core.event.EventBus
import net.spartanb312.render.core.event.Listener
import net.spartanb312.render.core.event.ParallelListener
import java.util.function.Consumer

internal inline fun <reified E : Any> Any.listener(
    eventBus: EventBus = HyperLethalEventBus,
    function: Consumer<E>,
) = listener(eventBus, this, E::class.java, DEFAULT_LISTENER_PRIORITY, false, function)

internal inline fun <reified E : Any> Any.listener(
    priority: Int,
    eventBus: EventBus = HyperLethalEventBus,
    function: Consumer<E>,
) = listener(eventBus, this, E::class.java, priority, false, function)

internal inline fun <reified E : Any> Any.listener(
    alwaysListening: Boolean,
    eventBus: EventBus = HyperLethalEventBus,
    function: Consumer<E>,
) = listener(eventBus, this, E::class.java, DEFAULT_LISTENER_PRIORITY, alwaysListening, function)

internal inline fun <reified E : Any> Any.listener(
    priority: Int,
    alwaysListening: Boolean,
    eventBus: EventBus = HyperLethalEventBus,
    function: Consumer<E>,
) = listener(eventBus, this, E::class.java, priority, alwaysListening, function)

internal inline fun <reified E : Any> Any.parallelListener(
    eventBus: EventBus = HyperLethalEventBus,
    noinline function: suspend (E) -> Unit,
) = parallelListener(eventBus, this, E::class.java, false, function)

internal inline fun <reified E : Any> Any.parallelListener(
    alwaysListening: Boolean,
    eventBus: EventBus = HyperLethalEventBus,
    noinline function: suspend (E) -> Unit,
) = parallelListener(eventBus, this, E::class.java, alwaysListening, function)

internal inline fun <reified E : Any> Any.concurrentListener(
    eventBus: EventBus = HyperLethalEventBus,
    noinline function: suspend (E) -> Unit,
) = concurrentListener(eventBus, this, E::class.java, false, function)

internal inline fun <reified E : Any> Any.concurrentListener(
    alwaysListening: Boolean,
    eventBus: EventBus = HyperLethalEventBus,
    noinline function: suspend (E) -> Unit,
) = concurrentListener(eventBus, this, E::class.java, alwaysListening, function)

internal fun <E : Any> listener(
    eventBus: EventBus,
    owner: Any,
    eventClass: Class<E>,
    priority: Int,
    alwaysListening: Boolean,
    function: Consumer<E>,
) {
    with(Listener(owner, eventClass, priority, function)) {
        if (alwaysListening) eventBus.subscribe(this)
        else eventBus.register(owner, this)
    }
}

internal fun <E : Any> parallelListener(
    eventBus: EventBus,
    owner: Any,
    eventClass: Class<E>,
    alwaysListening: Boolean,
    function: suspend (E) -> Unit,
) {
    with(ParallelListener(owner, eventClass, function)) {
        if (alwaysListening) eventBus.subscribe(this)
        else eventBus.register(owner, this)
    }
}

internal fun <E : Any> concurrentListener(
    eventBus: EventBus,
    owner: Any,
    eventClass: Class<E>,
    alwaysListening: Boolean,
    function: suspend (E) -> Unit,
) {
    with(Listener(owner, eventClass, Int.MAX_VALUE) { HyperLethalScope.launch { function.invoke(it) } }) {
        if (alwaysListening) eventBus.subscribe(this)
        else eventBus.register(owner, this)
    }
}