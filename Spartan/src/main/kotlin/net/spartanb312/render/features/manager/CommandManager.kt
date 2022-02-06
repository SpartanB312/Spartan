package net.spartanb312.render.features.manager

import net.spartanb312.render.Spartan.DEFAULT_CONFIG_GROUP
import net.spartanb312.render.core.common.ClassUtils.findTypedClasses
import net.spartanb312.render.core.common.ClassUtils.instance
import net.spartanb312.render.core.common.extension.removeFirstToArray
import net.spartanb312.render.core.config.provider.StandaloneConfigurable
import net.spartanb312.render.features.command.Command
import net.spartanb312.render.features.command.ExecutionScope
import net.spartanb312.render.features.common.AsyncLoadable
import net.spartanb312.render.launch.Logger

object CommandManager : StandaloneConfigurable(
    "${DEFAULT_CONFIG_GROUP}/managers/",
    "CommandManager"
), AsyncLoadable {

    val commands = mutableListOf<Command>()

    var commandPrefix by setting("CommandPrefix", ".")

    override suspend fun init() {
        Logger.info("Initializing CommandManager")
        AsyncLoadable.classes.await().findTypedClasses<Command>().forEach {
            it.instance?.let { instance -> commands.add(instance) }
        }
    }

    override fun postInit() {
        AsyncLoadable.glContextRequiredClasses.findTypedClasses<Command>().forEach {
            it.instance?.let { instance -> commands.add(instance) }
        }
        commands.sortBy { it.name }
    }

    fun String.runCommand(): Boolean {
        val cachedPrefix = commandPrefix
        if (!this.startsWith(cachedPrefix)) return false

        val array = removePrefix(cachedPrefix).split(" ")

        array.getOrNull(0)?.let {
            commands.forEach { command ->
                if (command.prefix.equals(it, true) || command.prefixAlias.any { al -> al.equals(it, true) }) {
                    with(command) {
                        ExecutionScope(array.removeFirstToArray(1), command).onCall()
                    }
                    return true
                }
            }

        }
        return false
    }

}