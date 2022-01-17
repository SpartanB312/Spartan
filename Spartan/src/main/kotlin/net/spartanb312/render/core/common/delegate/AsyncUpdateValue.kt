package net.spartanb312.render.core.common.delegate

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class AsyncUpdateValue<T>(private val block: () -> T) : ReadWriteProperty<Any?, T> {

    private var value0: T? = null

    val value get() = value0 ?: block().also { value0 = it }

    fun update(valueIn: T? = null) {
        value0 = valueIn ?: block()
    }

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T = value

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value0 = value
    }

}