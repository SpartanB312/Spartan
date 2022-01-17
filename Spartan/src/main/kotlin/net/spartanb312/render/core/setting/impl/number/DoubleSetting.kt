package net.spartanb312.render.core.setting.impl.number

class DoubleSetting(
    name: String,
    value: Double,
    range: ClosedFloatingPointRange<Double>,
    step: Double,
    description: String = "",
    visibility: (() -> Boolean) = { true }
) : NumberSetting<Double>(name, value, range, step, description, visibility) {

    override fun setByPercent(percent: Float) {
        value = range.start + ((range.endInclusive - range.start) * percent / step).toInt() * step
    }

    override fun getDisplay(): String {
        return String.format("%.2f", value)
    }

    override fun getPercentBar(): Float {
        return ((value - range.start) / (range.endInclusive - range.start)).toFloat()
    }

}