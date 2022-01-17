package net.spartanb312.render.core.setting

import net.spartanb312.render.core.common.delegate.AsyncUpdateValue
import net.spartanb312.render.core.common.interfaces.Nameable
import kotlin.reflect.KProperty

abstract class AbstractSetting<T> : Nameable {

    abstract override val name: String
    abstract val defaultValue: T
    abstract val description: String
    abstract var value: T

    val visibilities = mutableListOf<() -> Boolean>()
    val currentVisibility = visibilities.all { it.invoke() }
    //Async update Visibility
    private val isVisible0 = AsyncUpdateValue { currentVisibility }
    val isVisible by isVisible0

    fun updateVisibility() = isVisible0.update()

    val isModified get() = this.value != this.defaultValue

    val listeners = ArrayList<() -> Unit>()
    val valueListeners = ArrayList<(prev: T, input: T) -> Unit>()

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

    open fun reset() {
        value = defaultValue
    }

    infix fun listen(listener: () -> Unit): AbstractSetting<T> {
        this.listeners.add(listener)
        return this
    }

    fun valueListen(listener: (prev: T, input: T) -> Unit) {
        this.valueListeners.add(listener)
    }

    override fun toString() = value.toString()

    override fun equals(other: Any?) = this === other
            || (other is AbstractSetting<*>
            && this.name == other.name
            && this.value == other.value)

    override fun hashCode() = name.hashCode() * 31 + value.hashCode()

}