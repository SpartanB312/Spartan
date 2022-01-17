package net.spartanb312.render.core.common.command.args

import net.spartanb312.render.core.common.interfaces.Nameable

/**
 * The ID for an argument
 */
@Suppress("UNUSED")
data class ArgIdentifier<T : Any>(override val name: CharSequence) : Nameable
