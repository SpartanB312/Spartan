package net.spartanb312.render.core.setting.impl.number

class FloatSetting(
    name: String,
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    step: Float,
    description: String = "",
    visibility: (() -> Boolean) = { true }
) : NumberSetting<Float>(name, value, range, step, description, visibility) {

    override fun setByPercent(percent: Float) {
        value = range.start + ((range.endInclusive - range.start) * percent / step).toInt() * step
    }

    override fun getDisplay(): String {
        return String.format("%.2f", value)
    }

    override fun getPercentBar(): Float {
        return ((value - range.start) / (range.endInclusive - range.start))
    }

}