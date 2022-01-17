package net.spartanb312.render.core.common.collections

import java.io.Serializable
import java.util.AbstractSet
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.ConcurrentHashMap

class ConcurrentSet<E> : AbstractSet<E>(), Serializable {

    private val map: ConcurrentMap<E, Boolean> = ConcurrentHashMap()
    override val size: Int get() = map.size

    override fun contains(element: E): Boolean {
        return map.containsKey(element)
    }

    override fun add(element: E): Boolean {
        return map.putIfAbsent(element, java.lang.Boolean.TRUE) == null
    }

    override fun remove(element: E): Boolean {
        return map.remove(element) != null
    }

    override fun clear() {
        map.clear()
    }

    override fun iterator(): MutableIterator<E> {
        return map.keys.iterator()
    }

}