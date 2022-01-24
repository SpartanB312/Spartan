package net.spartanb312.render.graphics.api.font.setting

import net.spartanb312.render.core.config.IConfigContainer
import net.spartanb312.render.core.setting.AbstractSetting

class FontSetting(val name: String = "unknown") : IConfigContainer<FontSetting> {

    override val containerName = name
    override val settings = mutableListOf<AbstractSetting<*>>()

    var size by setting("Size", 1.0f, 0.5f..2.0f, 0.05f)
    var charGap by setting("Char Gap", 0.0f, -10f..10f, 0.5f)
    var lineSpace by setting("Line Space", 0.0f, -10f..10f, 0.05f)
    var baseLineOffset by setting("Baseline Offset", 0.0f, -10.0f..10.0f, 0.05f)
    var lodbias by setting("Lod Bias", 0.0f, -10.0f..10.0f, 0.05f)

    val actualSize get() = size * 0.1425f
    val actualCharGap get() = charGap * 0.5f - 2.05f
    val actualLineSpace get() = actualSize * (lineSpace * 0.05f + 0.77f)
    val actualLodBias get() = lodbias * 0.25f - 0.5375f
    val actualBaselineOffset get() = baseLineOffset * 2.0f - 8.0f

}

fun <T : IConfigContainer<T>> T.copyFrom(container: IConfigContainer<*>) = container.settings.forEach {
    if (!settings.contains(it)) settings.add(it)
}

fun <T : IConfigContainer<T>> T.removeFrom(container: IConfigContainer<*>) = container.settings.forEach {
    settings.remove(it)
}
