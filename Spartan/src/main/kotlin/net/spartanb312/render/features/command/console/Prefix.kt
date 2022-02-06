package net.spartanb312.render.features.command.console

import net.spartanb312.render.features.command.Command
import net.spartanb312.render.features.command.ExecutionScope
import net.spartanb312.render.features.command.string
import net.spartanb312.render.features.common.Category
import net.spartanb312.render.features.manager.CommandManager
import net.spartanb312.render.features.manager.MessageManager

object Prefix : Command(
    name = "Prefix",
    prefix = "prefix",
    category = Category.ConsoleCommand,
    description = "Change prefix",
    syntax = "prefix <prefix>",
) {
    override fun ExecutionScope.onCall() {

        string {
            CommandManager.commandPrefix = it
            MessageManager.printInfo("Changed prefix to $it")
        }

    }
}