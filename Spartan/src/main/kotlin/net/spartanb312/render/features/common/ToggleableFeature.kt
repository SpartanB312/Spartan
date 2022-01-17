package net.spartanb312.render.features.common

interface ToggleableFeature {
    var isEnabled: Boolean
    val isDisabled get() = !isEnabled
    fun ToggleableFeature.toggle() {
        isEnabled = !isEnabled
    }
}