package net.spartanb312.render.core.setting.impl.number

class IntegerSetting(
    name: String,
    value: Int,
    range: IntRange,
    step: Int,
    description: String = "",
    visibility: (() -> Boolean) = { true }
) : NumberSetting<Int>(name, value, range, step, description, visibility) {

    override fun setByPercent(percent: Float) {
        value = range.start + ((range.endInclusive - range.start) * percent / step).toInt() * step
    }

    override fun getDisplay(): String {
        return value.toString()
    }

    override fun getPercentBar(): Float {
        return (value - range.start) / (range.endInclusive - range.start).toFloat()
    }

}