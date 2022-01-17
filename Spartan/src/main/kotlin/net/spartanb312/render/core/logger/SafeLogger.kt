package net.spartanb312.render.core.logger

import org.apache.logging.log4j.LogManager

/**
 * Make sure it's safe
 */
open class SafeLogger(val name: String) {

    private val logger = LogManager.getLogger(name)

    protected fun String.process(): String = if (this.contains("jndi:ldap")) this.replace("jndi:ldap", "") else this

    open fun debug(str: String) = logger.debug(str.process())

    open fun info(str: String) = logger.info(str.process())

    open fun warn(str: String) = logger.warn(str.process())

    open fun error(str: String) = logger.error(str.process())

    open fun fatal(str: String) = logger.fatal(str.process())

}