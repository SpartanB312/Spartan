package net.spartanb312.render.features.command.commands

import net.spartanb312.render.Spartan
import net.spartanb312.render.Spartan.MOD_VERSION
import net.spartanb312.render.core.common.graphics.ColorUtils
import net.spartanb312.render.features.command.Command
import net.spartanb312.render.features.command.ExecutionScope
import net.spartanb312.render.features.command.string
import net.spartanb312.render.features.manager.CommandManager
import net.spartanb312.render.features.manager.MessageManager
import net.spartanb312.render.features.manager.ModuleManager
import net.spartanb312.render.launch.Logger

object Help : Command(
    name = "Help",
    prefix = "help",
    description = "Get helps",
    syntax = "help",
) {
    override fun ExecutionScope.onCall() {

        string { moduleName ->
            ModuleManager.modules.find { it.name.equals(moduleName, true) }?.let {
                MessageManager.printInfo("[${it.name}]${it.description}")
            }
            Logger.error(moduleName)
            return
        }

        MessageManager.printInfo("${ColorUtils.AQUA}${Spartan.MOD_NAME} ${ColorUtils.DARK_AQUA}V${MOD_VERSION}")
        MessageManager.printInfo("${ColorUtils.AQUA}Author ${ColorUtils.DARK_AQUA}B312")
        MessageManager.printInfo("${ColorUtils.AQUA}CommandPrefix ${ColorUtils.DARK_AQUA}${CommandManager.commandPrefix}")
        MessageManager.printInfo("${ColorUtils.GOLD}Available Commands : ")
        CommandManager.commands.forEach {
            MessageManager.printInfo("${ColorUtils.GOLD}${it.name} ${ColorUtils.YELLOW}${it.syntax} ${ColorUtils.GREEN}(${it.description})")
        }

    }
}