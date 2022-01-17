package net.spartanb312.render.core.setting

import net.spartanb312.render.core.common.FALSE
import net.spartanb312.render.core.common.KeyBind
import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.core.setting.impl.collections.lists.*
import net.spartanb312.render.core.setting.impl.collections.maps.*
import net.spartanb312.render.core.setting.impl.number.DoubleSetting
import net.spartanb312.render.core.setting.impl.number.FloatSetting
import net.spartanb312.render.core.setting.impl.number.IntegerSetting
import net.spartanb312.render.core.setting.impl.number.LongSetting
import net.spartanb312.render.core.setting.impl.other.ActionButton
import net.spartanb312.render.core.setting.impl.other.BindSetting
import net.spartanb312.render.core.setting.impl.other.ColorSetting
import net.spartanb312.render.core.setting.impl.other.ExtendSetting
import net.spartanb312.render.core.setting.impl.primitive.BooleanSetting
import net.spartanb312.render.core.setting.impl.primitive.EnumSetting
import net.spartanb312.render.core.setting.impl.primitive.StringSetting

interface SettingRegister<T> {

    fun T.setting(
        name: String,
        value: Double,
        range: ClosedFloatingPointRange<Double> = Double.MIN_VALUE..Double.MAX_VALUE,
        step: Double = 0.1,
        description: String = "",
        visibility: () -> Boolean = { true },
    ) = setting(DoubleSetting(name, value, range, step, description, visibility))

    fun T.setting(
        name: String,
        value: Float,
        range: ClosedFloatingPointRange<Float> = Float.MIN_VALUE..Float.MAX_VALUE,
        step: Float = 0.1F,
        description: String = "",
        visibility: () -> Boolean = { true },
    ) = setting(FloatSetting(name, value, range, step, description, visibility))

    fun T.setting(
        name: String,
        value: Int,
        range: IntRange = Int.MIN_VALUE..Int.MAX_VALUE,
        step: Int = 1,
        description: String = "",
        visibility: () -> Boolean = { true },
    ) = setting(IntegerSetting(name, value, range, step, description, visibility))

    fun T.setting(
        name: String,
        value: Long,
        range: LongRange = Long.MIN_VALUE..Long.MAX_VALUE,
        step: Long = 1L,
        description: String = "",
        visibility: () -> Boolean = { true },
    ) = setting(LongSetting(name, value, range, step, description, visibility))

    fun T.setting(
        name: String,
        value: Boolean,
        description: String = "",
        visibility: () -> Boolean = { true },
    ) = setting(BooleanSetting(name, value, description, visibility))

    fun <E : Enum<E>> T.setting(
        name: String,
        value: E,
        description: String = "",
        visibility: () -> Boolean = { true },
    ) = setting(EnumSetting(name, value, description, visibility))

    fun T.setting(
        name: String,
        value: String,
        description: String = "",
        visibility: () -> Boolean = { true },
    ) = setting(StringSetting(name, value, description, visibility))

    fun T.setting(
        name: String,
        value: ColorRGB,
        description: String = "",
        visibility: () -> Boolean = { true },
    ) = setting(ColorSetting(name, value, description, visibility))

    fun T.setting(
        name: String,
        value: List<Double>,
        description: String = "",
        visibility: () -> Boolean = { true },
    ) = setting(DoubleListSetting(name, value, description, visibility))

    fun T.setting(
        name: String,
        value: List<Float>,
        description: String = "",
        visibility: () -> Boolean = { true },
    ) = setting(FloatListSetting(name, value, description, visibility))

    fun T.setting(
        name: String,
        value: List<Int>,
        description: String = "",
        visibility: () -> Boolean = { true },
    ) = setting(IntegerListSetting(name, value, description, visibility))

    fun T.setting(
        name: String,
        value: List<Long>,
        description: String = "",
        visibility: () -> Boolean = { true },
    ) = setting(LongListSetting(name, value, description, visibility))

    fun T.setting(
        name: String,
        value: List<String>,
        description: String = "",
        visibility: () -> Boolean = { true },
    ) = setting(StringListSetting(name, value, description, visibility))

    fun T.setting(
        name: String,
        value: Map<String, Boolean>,
        description: String = "",
        visibility: () -> Boolean = { true },
    ) = setting(StringToBooleanMapSetting(name, value, description, visibility))

    fun T.setting(
        name: String,
        value: Map<String, ColorRGB>,
        description: String = "",
        visibility: () -> Boolean = { true },
    ) = setting(StringToColorMapSetting(name, value, description, visibility))

    fun T.setting(
        name: String,
        value: Map<String, Double>,
        description: String = "",
        visibility: () -> Boolean = { true },
    ) = setting(StringToDoubleMapSetting(name, value, description, visibility))

    fun T.setting(
        name: String,
        value: Map<String, Float>,
        description: String = "",
        visibility: () -> Boolean = { true },
    ) = setting(StringToFloatMapSetting(name, value, description, visibility))

    fun T.setting(
        name: String,
        value: Map<String, Int>,
        description: String = "",
        visibility: () -> Boolean = { true },
    ) = setting(StringToIntegerMapSetting(name, value, description, visibility))

    fun T.setting(
        name: String,
        value: Map<String, Long>,
        description: String = "",
        visibility: () -> Boolean = { true },
    ) = setting(StringToLongMapSetting(name, value, description, visibility))

    fun T.setting(
        name: String,
        value: Map<String, String>,
        description: String = "",
        visibility: () -> Boolean = { true },
    ) = setting(StringToStringMapSetting(name, value, description, visibility))

    fun T.setting(
        name: String,
        value: () -> Unit,
        description: String = "",
        visibility: () -> Boolean = { true }
    ) = setting(ActionButton(name, value, description, visibility))

    fun T.setting(
        name: String,
        value: KeyBind,
        description: String = "",
        visibility: () -> Boolean = { true },
    ) = setting(BindSetting(name, value, description, visibility))

    fun <U : Convertable<U>> T.extendSetting(
        name: String,
        value: U,
        description: String = "",
        visibility: () -> Boolean = { true },
    ) = setting(ExtendSetting(name, value, description, visibility))

    fun <U : Convertable<U>> T.extendSetting(
        name: String,
        value: List<U>,
        description: String = "",
        parser: String.() -> U,
        visibility: () -> Boolean = { true },
    ) = setting(ExtendListSetting(name, value, description, parser, visibility))

    fun <U : Convertable<U>> T.extendSetting(
        name: String,
        value: Map<String, U>,
        description: String = "",
        parser: String.() -> U,
        visibility: () -> Boolean = { true },
    ) = setting(StringToExtendMapSetting(name, value, description, parser, visibility))

    fun <S : AbstractSetting<*>> T.setting(setting: S): S

}

fun <T> AbstractSetting<T>.invisible(): AbstractSetting<T> = this.apply {
    visibilities.clear()
    visibilities.add(FALSE)
}

fun <T> AbstractSetting<T>.atValue(value: T): AbstractSetting<T> = this.apply {
    visibilities.add { this.value == value }
}

fun <T> AbstractSetting<T>.whenTrue(setting: AbstractSetting<Boolean>): AbstractSetting<T> = this.apply {
    visibilities.add { setting.value }
}

fun <T> AbstractSetting<T>.whenFalse(setting: AbstractSetting<Boolean>): AbstractSetting<T> = this.apply {
    visibilities.add { !setting.value }
}

fun <T : Enum<T>> AbstractSetting<T>.atMode(setting: AbstractSetting<T> = this, value: Enum<T>) = this.apply {
    visibilities.add { setting.value == value }
}

fun <T : Comparable<T>> AbstractSetting<T>.inRange(range: ClosedRange<T>) = this.apply {
    visibilities.add { this.value in range }
}