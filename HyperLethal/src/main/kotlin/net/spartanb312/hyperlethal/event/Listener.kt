package net.spartanb312.hyperlethal.event

import kotlinx.coroutines.launch
import net.spartanb312.render.core.event.DEFAULT_LISTENER_PRIORITY
import net.spartanb312.render.core.event.Listener
import net.spartanb312.render.core.event.ParallelListener
import net.spartanb312.render.core.event.inner.MainEventBus
import java.util.function.Consumer

internal inline fun <reified E : Any> Any.listener(
    inMain: Boolean = false,
    function: Consumer<E>,
) = listener(this, E::class.java, DEFAULT_LISTENER_PRIORITY, false, inMain, function)

internal inline fun <reified E : Any> Any.listener(
    inMain: Boolean = false,
    priority: Int,
    function: Consumer<E>,
) = listener(this, E::class.java, priority, false, inMain, function)

internal inline fun <reified E : Any> Any.listener(
    inMain: Boolean = false,
    alwaysListening: Boolean,
    function: Consumer<E>,
) = listener(this, E::class.java, DEFAULT_LISTENER_PRIORITY, alwaysListening, inMain, function)

internal inline fun <reified E : Any> Any.listener(
    inMain: Boolean = false,
    priority: Int,
    alwaysListening: Boolean,
    function: Consumer<E>,
) = listener(this, E::class.java, priority, alwaysListening, inMain, function)

internal inline fun <reified E : Any> Any.parallelListener(
    inMain: Boolean = false,
    noinline function: suspend (E) -> Unit,
) = parallelListener(this, E::class.java, false, inMain, function)

internal inline fun <reified E : Any> Any.parallelListener(
    inMain: Boolean = false,
    alwaysListening: Boolean,
    noinline function: suspend (E) -> Unit,
) = parallelListener(this, E::class.java, alwaysListening, inMain, function)

internal inline fun <reified E : Any> Any.concurrentListener(
    inMain: Boolean = false,
    noinline function: suspend (E) -> Unit,
) = concurrentListener(this, E::class.java, false, inMain, function)

internal inline fun <reified E : Any> Any.concurrentListener(
    inMain: Boolean = false,
    alwaysListening: Boolean,
    noinline function: suspend (E) -> Unit,
) = concurrentListener(this, E::class.java, alwaysListening, inMain, function)

internal fun <E : Any> listener(
    owner: Any,
    eventClass: Class<E>,
    priority: Int,
    alwaysListening: Boolean,
    inMain: Boolean = false,
    function: Consumer<E>
) {
    with(Listener(owner, eventClass, priority, function)) {
        if (alwaysListening) {
            EventBus.subscribe(this)
            if (inMain) MainEventBus.subscribe(this)
        } else {
            EventBus.register(owner, this)
            if (inMain) MainEventBus.register(owner, this)
        }
    }
}

internal fun <E : Any> parallelListener(
    owner: Any,
    eventClass: Class<E>,
    alwaysListening: Boolean,
    inMain: Boolean = false,
    function: suspend (E) -> Unit,
) {
    with(ParallelListener(owner, eventClass, function)) {
        if (alwaysListening) {
            EventBus.subscribe(this)
            if (inMain) MainEventBus.subscribe(this)
        } else {
            EventBus.register(owner, this)
            if (inMain) MainEventBus.register(owner, this)
        }
    }
}

internal fun <E : Any> concurrentListener(
    owner: Any,
    eventClass: Class<E>,
    alwaysListening: Boolean,
    inMain: Boolean = false,
    function: suspend (E) -> Unit,
) {
    with(Listener(owner, eventClass, Int.MAX_VALUE) { HyperLethalScope.launch { function.invoke(it) } }) {
        if (alwaysListening) {
            EventBus.subscribe(this)
            if (inMain) MainEventBus.subscribe(this)
        } else {
            EventBus.register(owner, this)
            if (inMain) MainEventBus.register(owner, this)
        }
    }
}