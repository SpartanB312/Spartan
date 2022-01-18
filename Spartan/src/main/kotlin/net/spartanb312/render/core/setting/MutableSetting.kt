package net.spartanb312.render.core.setting

open class MutableSetting<T : Any>(
    final override val name: String,
    valueIn: T,
    override val description: String = "",
    visibility: (() -> Boolean),
) : AbstractSetting<T>(){

    init {
        visibilities.add(visibility)
    }

    override val defaultValue = valueIn
    override var value = valueIn
        set(value) {
            if (value != field) {
                val prev = field
                val new = value
                field = new
                valueListeners.forEach { it(prev, field) }
                listeners.forEach { it() }
            }
        }

}