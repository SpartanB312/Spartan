package net.spartanb312.render.graphics.impl.structure.misc

/**
 * Use it to make animation easier
 */
interface AnimationFlag<T : Number> {
    var currentValue: T
    fun reset()
}