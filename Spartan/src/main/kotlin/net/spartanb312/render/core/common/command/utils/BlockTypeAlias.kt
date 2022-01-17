package net.spartanb312.render.core.common.command.utils

import net.spartanb312.render.core.common.command.CommandBuilder
import net.spartanb312.render.core.common.command.args.AbstractArg
import net.spartanb312.render.core.common.command.args.ArgIdentifier
import net.spartanb312.render.core.common.command.execute.IExecuteEvent

/**
 * Type alias for a block used for execution of a argument combination
 *
 * @param E Type of [IExecuteEvent], can be itself or its subtype
 *
 * @see CommandBuilder.execute
 */
typealias ExecuteBlock<E> = suspend E.() -> Unit

/**
 * Type alias for a block used for Argument building
 *
 * @param T Type of argument
 *
 * @see CommandBuilder
 */
typealias BuilderBlock<T> = AbstractArg<T>.(ArgIdentifier<T>) -> Unit
