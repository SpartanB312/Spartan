package net.spartanb312.render.features.ui.structure.misc

/**
 * Use it to make animation easier
 */
interface AnimationFlag<T : Number> {
    var currentValue: T
    fun reset()
}