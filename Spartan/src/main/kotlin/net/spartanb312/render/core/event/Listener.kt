package net.spartanb312.render.core.event

import kotlinx.coroutines.launch
import net.spartanb312.render.core.common.interfaces.Nameable
import net.spartanb312.render.core.event.inner.MainEventBus
import net.spartanb312.render.core.event.inner.SpartanScope
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Consumer

const val DEFAULT_LISTENER_PRIORITY = 0

fun <E : Any> listener(
    eventBus: EventBus,
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

fun <E : Any> parallelListener(
    eventBus: EventBus,
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

fun <E : Any> concurrentListener(
    eventBus: EventBus,
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

class Listener<E : Any>(
    owner: Any,
    eventClass: Class<E>,
    priority: Int,
    function: Consumer<E>,
) : AbstractListener<E, Consumer<E>>(owner, eventClass, priority, function)

class ParallelListener<E : Any>(
    owner: Any,
    eventClass: Class<E>,
    function: suspend (E) -> Unit,
) : AbstractListener<E, suspend (E) -> Unit>(owner, eventClass, DEFAULT_LISTENER_PRIORITY, function)

sealed class AbstractListener<E : Any, F>(
    owner: Any,
    val eventClass: Class<E>,
    val priority: Int,
    val function: F,
) : Comparable<AbstractListener<*, *>> {

    val id = listenerID.getAndIncrement()
    val ownerName: String = if (owner is Nameable) owner.nameAsString else owner.javaClass.simpleName

    override fun compareTo(other: AbstractListener<*, *>): Int {
        val result = other.priority.compareTo(this.priority)
        return if (result != 0) result
        else id.compareTo(other.id)
    }

    override fun equals(other: Any?): Boolean {
        return this === other
                || (other is AbstractListener<*, *>
                && other.eventClass == this.eventClass
                && other.id == this.id)
    }

    override fun hashCode(): Int {
        return 31 * eventClass.hashCode() + id.hashCode()
    }

    companion object {
        private val listenerID = AtomicInteger(Int.MIN_VALUE)
    }

}