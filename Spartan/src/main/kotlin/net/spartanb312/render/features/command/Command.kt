package net.spartanb312.render.features.command

import net.spartanb312.render.features.common.AbstractFeature
import net.spartanb312.render.features.common.Category
import net.spartanb312.render.features.manager.MessageManager

abstract class Command(
    name: String,
    alias: Array<String> = arrayOf(),
    category: Category,
    val prefix: String,
    val prefixAlias: Array<String> = arrayOf(),
    description: String = "No description",
    val syntax: String,
) : AbstractFeature(name, alias, category, description) {
    abstract fun ExecutionScope.onCall()
    fun showSyntax() = MessageManager.printNoSpamError("Error while executing command ${name}.Syntax : $syntax")
}