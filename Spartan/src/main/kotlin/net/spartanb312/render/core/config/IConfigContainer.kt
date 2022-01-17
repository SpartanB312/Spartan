package net.spartanb312.render.core.config

import net.spartanb312.render.core.setting.AbstractSetting
import net.spartanb312.render.core.setting.SettingRegister

interface IConfigContainer<T> : SettingRegister<T> {

    val containerName: String

    val settings: MutableList<AbstractSetting<*>>

    override fun <S : AbstractSetting<*>> T.setting(setting: S): S = setting.also {
        settings.add(it)
    }

}