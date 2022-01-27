package net.spartanb312.render.features.common

import net.spartanb312.render.features.SpartanCore
import net.spartanb312.render.util.thread.isMainThread

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
            if (isMainThread) onEnable()
            else SpartanCore.addTask(SpartanCore.Executors.Main) {
                onEnable()
            }
        }
    }

    fun disable() {
        if (isEnabled) {
            isDisabled = true
            if (isMainThread) onDisable()
            else SpartanCore.addTask(SpartanCore.Executors.Main) {
                onDisable()
            }
        }
    }

}