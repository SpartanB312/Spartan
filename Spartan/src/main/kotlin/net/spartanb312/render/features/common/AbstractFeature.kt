package net.spartanb312.render.features.common

import net.spartanb312.render.Spartan.DEFAULT_FILE_GROUP
import net.spartanb312.render.core.common.interfaces.Alias
import net.spartanb312.render.core.common.interfaces.Nameable
import net.spartanb312.render.core.config.provider.IStandaloneConfigManagerProvider
import net.spartanb312.render.core.setting.AbstractSetting
import net.spartanb312.render.features.language.text.TextContainer

@Suppress("LeakingThis")
abstract class AbstractFeature(
    final override val name: String,
    override val alias: Array<String> = arrayOf(),
    val category: Category,
    description: String
) : Nameable, Alias, IStandaloneConfigManagerProvider<AbstractFeature> {

    private val description0 =
        TextContainer("${category.displayName}_${category.subcategory.displayName}_${name}_description", description)

    private val displayName0 =
        TextContainer("${category.displayName}_${category.subcategory.displayName}_${name}_name", name)

    val description by description0
    val displayName by displayName0

    override val containerName = name
    override val settings = mutableListOf<AbstractSetting<*>>()
    override val configManager =
        "$DEFAULT_FILE_GROUP/${category.subcategory.displayName}/${category.displayName}/$name".create()

    fun saveConfig() = configManager.saveConfig()
    fun loadConfig() = configManager.loadConfig()

}