package net.spartanb312.render.core.setting.impl.number

class LongSetting(
    name: String,
    value: Long,
    range: LongRange,
    step: Long,
    description: String = "",
    visibility: (() -> Boolean) = { true }
) : NumberSetting<Long>(name, value, range, step, description, visibility) {

    override fun setByPercent(percent: Float) {
        value = range.start + ((range.endInclusive - range.start) * percent / step).toInt() * step
    }

    override fun getDisplay(): String {
        return value.toString()
    }

    override fun getPercentBar(): Float {
        return ((value - range.start) / (range.endInclusive - range.start).toDouble()).toFloat()
    }

}