package net.spartanb312.render.core.event

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.spartanb312.render.core.common.collections.ConcurrentSet
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentSkipListSet
import java.util.concurrent.CopyOnWriteArrayList

@Suppress("UNCHECKED_CAST")
open class EventBus {

    private val registered = ConcurrentHashMap<Any, CopyOnWriteArrayList<Listener<*>>>()
    private val registeredParallel = ConcurrentHashMap<Any, CopyOnWriteArrayList<ParallelListener<*>>>()

    private val subscribed = ConcurrentHashMap<Class<out Any>, ConcurrentSkipListSet<Listener<Any>>>()
    private val subscribedParallel = ConcurrentHashMap<Class<out Any>, ConcurrentSet<ParallelListener<Any>>>()

    fun <T : Listener<*>> register(owner: Any, listener: T) {
        registered.getOrPut(owner, ::CopyOnWriteArrayList).add(listener)
    }

    fun <T : ParallelListener<*>> register(owner: Any, listener: T) {
        registeredParallel.getOrPut(owner, ::CopyOnWriteArrayList).add(listener)
    }

    fun subscribe(obj: Any) {
        registered[obj]?.forEach(this::subscribe)
        registeredParallel[obj]?.forEach(this::subscribe)
    }

    fun subscribe(listener: Listener<*>) {
        subscribed.getOrPut(listener.eventClass, ::ConcurrentSkipListSet).add(listener as Listener<Any>)
    }

    fun subscribe(listener: ParallelListener<*>) {
        subscribedParallel.getOrPut(listener.eventClass, ::ConcurrentSet).add(listener as ParallelListener<Any>)
    }

    fun unsubscribe(obj: Any) {
        registered[obj]?.forEach(this::unsubscribe)
        registeredParallel[obj]?.forEach(this::unsubscribe)
    }

    fun unsubscribe(listener: Listener<*>) {
        subscribed[listener.eventClass]?.remove(listener as Listener<Any>)
    }

    fun unsubscribe(listener: ParallelListener<*>) {
        subscribedParallel[listener.eventClass]?.remove(listener as ParallelListener<Any>)
    }

    fun post(event: Any) {
        subscribed[event.javaClass]?.forEach {
            it.function.accept(event)
        }
        invokeParallel(event)
    }

    private fun invokeParallel(event: Any) {
        val listeners = subscribedParallel[event.javaClass]
        if (!listeners.isNullOrEmpty()) {
            runBlocking {
                listeners.forEach {
                    launch(Dispatchers.Default) {
                        it.function.invoke(event)
                    }
                }
            }
        }
    }

}