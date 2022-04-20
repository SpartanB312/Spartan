package net.spartanb312.render.features.command.console

import net.spartanb312.render.features.command.Command
import net.spartanb312.render.features.command.ExecutionScope
import net.spartanb312.render.features.command.match
import net.spartanb312.render.features.common.Category
import net.spartanb312.render.features.manager.ConfigManager
import net.spartanb312.render.features.manager.MessageManager

object Config : Command(
    name = "Config",
    prefix = "config",
    category = Category.ConsoleCommand,
    description = "manage config",
    syntax = "config <load/save/reset>",
) {
    override fun ExecutionScope.onCall() {

        match("load") {
            infoNS("Loaded config.")
            ConfigManager.loadAll()
        }

        match("save") {
            infoNS("Saved config.")
            ConfigManager.saveAll()
        }

        match("reset") {
            infoNS("Reset config.")
            ConfigManager.resetAll()
        }

    }
}