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
            if (isMainThread) {
                isEnabled = true
                onEnable()
            } else SpartanCore.addTask(SpartanCore.Executors.Main) {
                isEnabled = true
                onEnable()
            }
        }
    }

    fun disable() {
        if (isEnabled) {
            if (isMainThread) {
                isDisabled = true
                onDisable()
            } else SpartanCore.addTask(SpartanCore.Executors.Main) {
                isDisabled = true
                onDisable()
            }
        }
    }

}