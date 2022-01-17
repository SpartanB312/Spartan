package net.spartanb312.render.core.setting.impl.number

import net.spartanb312.render.core.setting.MutableSetting

abstract class NumberSetting<T>(
    name: String,
    value: T,
    val range: ClosedRange<T>,
    val step: T,
    description: String = "",
    visibility: (() -> Boolean) = { true }
) : MutableSetting<T>(name, value, description, visibility)
        where T : Number, T : Comparable<T> {

    abstract fun getDisplay(): String
    abstract fun getPercentBar(): Float
    abstract fun setByPercent(percent: Float)

    fun getMin(): Double {
        return range.start.toDouble()
    }

    fun getMax(): Double {
        return range.endInclusive.toDouble()
    }

}