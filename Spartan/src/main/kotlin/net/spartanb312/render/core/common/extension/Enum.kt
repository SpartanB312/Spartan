package net.spartanb312.render.core.common.extension

import net.spartanb312.render.core.common.interfaces.DisplayEnum

fun <E : Enum<E>> E.next(): E = declaringClass.enumConstants.run {
    get((ordinal + 1) % size)
}

fun <E : Enum<E>> E.last(): E = declaringClass.enumConstants.run {
    get(if (ordinal == 0) size - 1 else ordinal - 1)
}

fun Enum<*>.readableName() = (this as? DisplayEnum)?.displayName
    ?: name.mapEach('_') { it.lowercase().capitalize() }.joinToString(" ")