package net.spartanb312.render.features.command.management

import net.spartanb312.render.features.command.*
import net.spartanb312.render.features.common.Category
import net.spartanb312.render.features.manager.MessageManager
import net.spartanb312.render.features.manager.ModuleManager

object Utility : Command(
    name = "Utility",
    alias = arrayOf("Setting"),
    prefix = "utility",
    prefixAlias = arrayOf("u"),
    category = Category.ManagementCommand,
    description = "manage utility",
    syntax = "utility <utility name> <setting name/enable/disable/toggle/on/off> <value>",
) {
    override fun ExecutionScope.onCall() {
        string { nameIn ->
            val utility = ModuleManager.getUtility(nameIn)
            if (utility != null) {
                matchAny("enable", "on") {
                    utility.enable()
                }
            } else MessageManager.printNoSpamWarning("Please specify an action to $nameIn!")
            return
        }
        MessageManager.printNoSpamWarning("Please specify a utility!")
    }
}