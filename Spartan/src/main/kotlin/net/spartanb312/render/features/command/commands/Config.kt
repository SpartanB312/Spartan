package net.spartanb312.render.features.command.commands

import net.spartanb312.render.features.command.Command
import net.spartanb312.render.features.command.ExecutionScope
import net.spartanb312.render.features.command.match
import net.spartanb312.render.features.manager.ConfigManager

object Config : Command(
    name = "Config",
    prefix = "config",
    description = "manage config",
    syntax = "config <load/save/reset>",
) {
    override fun ExecutionScope.onCall() {
        match("load") {
            ConfigManager.loadAll()
            return
        }

        match("save") {
            ConfigManager.saveAll()
            return
        }

        match("reset"){
            ConfigManager.resetAll()
            return
        }
    }
}