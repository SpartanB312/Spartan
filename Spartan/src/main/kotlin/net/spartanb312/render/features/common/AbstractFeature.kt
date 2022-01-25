package net.spartanb312.render.features.common

import net.spartanb312.render.Spartan.DEFAULT_CONFIG_GROUP
import net.spartanb312.render.core.common.interfaces.Alias
import net.spartanb312.render.core.common.interfaces.Nameable
import net.spartanb312.render.core.config.provider.IStandaloneConfigManagerProvider
import net.spartanb312.render.core.setting.AbstractSetting
import net.spartanb312.render.features.language.LanguageManager.register
import net.spartanb312.render.features.language.text.TextContainer

@Suppress("LeakingThis")
abstract class AbstractFeature(
    final override val name: String,
    override val alias: Array<String> = arrayOf(),
    val category: Category,
    description: String
) : Nameable, Alias, IStandaloneConfigManagerProvider<AbstractFeature> {

    val description by TextContainer(
        "${category.displayName}_${category.generalCategory.displayName}_${name}_description",
        description
    ).also { it.register() }

    val displayName by TextContainer(
        "${category.displayName}_${category.generalCategory.displayName}_${name}_name",
        name
    ).also { it.register() }

    override val containerName = name
    override val settings = mutableListOf<AbstractSetting<*>>()
    override val configManager =
        "$DEFAULT_CONFIG_GROUP/features/${category.generalCategory.lowerCase}/${category.lowerCase}".create()

}