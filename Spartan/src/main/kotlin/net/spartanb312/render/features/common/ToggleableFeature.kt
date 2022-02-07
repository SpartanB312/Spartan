package net.spartanb312.render.features.common

import net.spartanb312.render.features.SpartanCore

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
            SpartanCore.onMain {
                isEnabled = true
                onEnable()
            }
        }
    }

    fun disable() {
        if (isEnabled) {
            SpartanCore.onMain {
                isDisabled = true
                onDisable()
            }
        }
    }

}