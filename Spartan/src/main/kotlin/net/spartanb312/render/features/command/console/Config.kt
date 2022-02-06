package net.spartanb312.render.features.command.console

import net.spartanb312.render.features.command.Command
import net.spartanb312.render.features.command.ExecutionScope
import net.spartanb312.render.features.command.match
import net.spartanb312.render.features.common.Category
import net.spartanb312.render.features.manager.ConfigManager

object Config : Command(
    name = "Config",
    prefix = "config",
    category = Category.ConsoleCommand,
    description = "manage config",
    syntax = "config <load/save/reset>",
) {
    override fun ExecutionScope.onCall() {

        match("load") {
            ConfigManager.loadAll()
        }

        match("save") {
            ConfigManager.saveAll()
        }

        match("reset") {
            ConfigManager.resetAll()
        }

    }
}