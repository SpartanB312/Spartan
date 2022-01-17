package net.spartanb312.render.core.common.command.execute

import net.spartanb312.render.core.common.command.AbstractCommandManager
import net.spartanb312.render.core.common.command.Command
import net.spartanb312.render.core.common.command.args.AbstractArg
import net.spartanb312.render.core.common.command.args.ArgIdentifier

/**
 * Event being used for executing the [Command]
 */
interface IExecuteEvent {

    val commandManager: AbstractCommandManager<*>

    /**
     * Parsed arguments
     */
    val args: Array<String>

    /**
     * Maps argument for the [argTree]
     */
    suspend fun mapArgs(argTree: List<AbstractArg<*>>)

    /**
     * Gets mapped value for an [ArgIdentifier]
     *
     * @throws NullPointerException If this [ArgIdentifier] isn't mapped
     */
    val <T : Any> ArgIdentifier<T>.value: T

}
