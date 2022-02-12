package net.spartanb312.render.features.ui.item.button

import net.spartanb312.render.features.ui.item.SpecialItem
import net.spartanb312.render.features.ui.structure.Component

sealed class EffectButton(
    override var x: Float,
    override var y: Float,
    override var width: Float,
    override var height: Float,
    var action: (EffectButton.(mouseX: Int, mouseY: Int, button: Int) -> Boolean)? = null
) : SpecialItem, Component {

    class RectWavePulseButton(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        private val direction: PulseDirection = PulseDirection.Left,
        action: (EffectButton.(mouseX: Int, mouseY: Int, button: Int) -> Boolean)? = null
    ) : EffectButton(x, y, width, height, action) {

        private var lastPulseStartTime = System.currentTimeMillis()

        override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
            TODO("To be implemented")
        }

        override fun reset() {
            lastPulseStartTime = System.currentTimeMillis()
        }

        enum class PulseDirection {
            Left,
            Right
        }

    }

    abstract fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float)

    override fun onMouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int): Boolean =
        action?.invoke(this, mouseX, mouseY, mouseButton) ?: false

}