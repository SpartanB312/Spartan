package net.spartanb312.render.core.event

import kotlinx.coroutines.launch
import net.spartanb312.render.core.common.interfaces.Nameable
import net.spartanb312.render.core.event.inner.SpartanScope
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Consumer

const val DEFAULT_LISTENER_PRIORITY = 0

inline fun <reified E : Any> EventBus.listener(
    function: Consumer<E>,
) = listener(this, E::class.java, DEFAULT_LISTENER_PRIORITY, false, function)

inline fun <reified E : Any> EventBus.listener(
    priority: Int,
    function: Consumer<E>,
) = listener(this, E::class.java, priority, false, function)

inline fun <reified E : Any> EventBus.listener(
    alwaysListening: Boolean,
    function: Consumer<E>,
) = listener(this, E::class.java, DEFAULT_LISTENER_PRIORITY, alwaysListening, function)

inline fun <reified E : Any> EventBus.listener(
    priority: Int,
    alwaysListening: Boolean,
    function: Consumer<E>,
) = listener(this, E::class.java, priority, alwaysListening, function)


inline fun <reified E : Any> EventBus.parallelListener(
    noinline function: suspend (E) -> Unit,
) = parallelListener(this, E::class.java, false, function)

inline fun <reified E : Any> EventBus.parallelListener(
    alwaysListening: Boolean,
    noinline function: suspend (E) -> Unit,
) = parallelListener(this, E::class.java, alwaysListening, function)

inline fun <reified E : Any> EventBus.concurrentListener(
    noinline function: suspend (E) -> Unit,
) = concurrentListener(this, E::class.java, false, function)

inline fun <reified E : Any> EventBus.concurrentListener(
    alwaysListening: Boolean,
    noinline function: suspend (E) -> Unit,
) = concurrentListener(this, E::class.java, alwaysListening, function)

fun <E : Any> EventBus.listener(
    owner: Any,
    eventClass: Class<E>,
    priority: Int,
    alwaysListening: Boolean,
    function: Consumer<E>,
) {
    Listener(owner, eventClass, priority, function).let {
        if (alwaysListening) this.subscribe(it)
        else this.register(owner, it)
    }
}

fun <E : Any> EventBus.parallelListener(
    owner: Any,
    eventClass: Class<E>,
    alwaysListening: Boolean,
    function: suspend (E) -> Unit,
) {
    ParallelListener(owner, eventClass, function).let {
        if (alwaysListening) this.subscribe(it)
        else this.register(owner, it)
    }
}

fun <E : Any> EventBus.concurrentListener(
    owner: Any,
    eventClass: Class<E>,
    alwaysListening: Boolean,
    function: suspend (E) -> Unit,
) {
    Listener(owner, eventClass, Int.MAX_VALUE) { SpartanScope.launch { function.invoke(it) } }.let {
        if (alwaysListening) this.subscribe(it)
        else this.register(owner, it)
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