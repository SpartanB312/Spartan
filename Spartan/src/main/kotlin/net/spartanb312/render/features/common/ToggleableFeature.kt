package net.spartanb312.render.features.common

interface ToggleableFeature {

    var isEnabled: Boolean
    var isDisabled
        get() = !isEnabled
        set(value) {
            isEnabled = !value
        }

    fun ToggleableFeature.toggle() {
        isEnabled = !isEnabled
    }

    fun onEnable() {
    }

    fun onDisable() {
    }

    fun enable() {
        if (isDisabled) {
            isEnabled = true
            onEnable()
        }
    }

    fun disable() {
        if (isEnabled) {
            isDisabled = true
            onDisable()
        }
    }

}