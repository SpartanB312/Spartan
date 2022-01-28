package net.spartanb312.render.core.common

import java.io.Serializable

data class MutablePair<A, B>(
    var first: A,
    var second: B
) : Serializable {
    override fun toString(): String = "($first, $second)"
}