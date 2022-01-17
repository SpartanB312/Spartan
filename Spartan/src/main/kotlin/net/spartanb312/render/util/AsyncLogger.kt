package net.spartanb312.render.util

import net.spartanb312.render.core.logger.SafeLogger
import net.spartanb312.render.features.SpartanCore
import net.spartanb312.render.launch.InitializationManager

/**
 * Lol wtf?
 */
class AsyncLogger(name: String) : SafeLogger(name) {

    override fun info(str: String) {
        if (InitializationManager.isReady) SpartanCore.addTask {
            super.info(str)
        } else super.info(str)
    }

    override fun fatal(str: String) {
        if (InitializationManager.isReady) SpartanCore.addTask {
            super.fatal(str)
        } else super.fatal(str)
    }

    override fun error(str: String) {
        if (InitializationManager.isReady) SpartanCore.addTask {
            super.error(str)
        } else super.error(str)
    }

    override fun warn(str: String) {
        if (InitializationManager.isReady) SpartanCore.addTask {
            super.warn(str)
        } else super.warn(str)
    }

    override fun debug(str: String) {
        if (InitializationManager.isReady) SpartanCore.addTask {
            super.debug(str)
        } else super.debug(str)
    }

}