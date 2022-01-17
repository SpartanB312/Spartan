package net.spartanb312.render.core.event.inner

import kotlinx.coroutines.launch
import net.spartanb312.render.core.event.DEFAULT_LISTENER_PRIORITY
import net.spartanb312.render.core.event.EventBus
import net.spartanb312.render.core.event.Listener
import net.spartanb312.render.core.event.ParallelListener
import java.util.function.Consumer

object MainEventBus : EventBus()

internal inline fun <reified E : Any> Any.listener(
    function: Consumer<E>,
) = listener(this, E::class.java, DEFAULT_LISTENER_PRIORITY, false, function)

internal inline fun <reified E : Any> Any.listener(
    priority: Int,
    function: Consumer<E>,
) = listener(this, E::class.java, priority, false, function)

internal inline fun <reified E : Any> Any.listener(
    alwaysListening: Boolean,
    function: Consumer<E>,
) = listener(this, E::class.java, DEFAULT_LISTENER_PRIORITY, alwaysListening, function)

internal inline fun <reified E : Any> Any.listener(
    priority: Int,
    alwaysListening: Boolean,
    function: Consumer<E>,
) = listener(this, E::class.java, priority, alwaysListening, function)

internal inline fun <reified E : Any> Any.parallelListener(
    noinline function: suspend (E) -> Unit,
) = parallelListener(this, E::class.java, false, function)

internal inline fun <reified E : Any> Any.parallelListener(
    alwaysListening: Boolean,
    noinline function: suspend (E) -> Unit,
) = parallelListener(this, E::class.java, alwaysListening, function)

internal inline fun <reified E : Any> Any.concurrentListener(
    noinline function: suspend (E) -> Unit,
) = concurrentListener(this, E::class.java, false, function)

internal inline fun <reified E : Any> Any.concurrentListener(
    alwaysListening: Boolean,
    noinline function: suspend (E) -> Unit,
) = concurrentListener(this, E::class.java, alwaysListening, function)

internal fun <E : Any> listener(
    owner: Any,
    eventClass: Class<E>,
    priority: Int,
    alwaysListening: Boolean,
    function: Consumer<E>,
) {
    with(Listener(owner, eventClass, priority, function)) {
        if (alwaysListening) MainEventBus.subscribe(this)
        else MainEventBus.register(owner, this)
    }
}

internal fun <E : Any> parallelListener(
    owner: Any,
    eventClass: Class<E>,
    alwaysListening: Boolean,
    function: suspend (E) -> Unit,
) {
    with(ParallelListener(owner, eventClass, function)) {
        if (alwaysListening) MainEventBus.subscribe(this)
        else MainEventBus.register(owner, this)
    }
}

internal fun <E : Any> concurrentListener(
    owner: Any,
    eventClass: Class<E>,
    alwaysListening: Boolean,
    function: suspend (E) -> Unit,
) {
    with(Listener(owner, eventClass, Int.MAX_VALUE) { SpartanScope.launch { function.invoke(it) } }) {
        if (alwaysListening) MainEventBus.subscribe(this)
        else MainEventBus.register(owner, this)
    }
}