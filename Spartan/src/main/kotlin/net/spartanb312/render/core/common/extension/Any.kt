package net.spartanb312.render.core.common.extension

import java.io.File

inline fun <reified T : Any> Any?.ifType(block: (T) -> Unit) {
    if (this is T) block(this)
}

inline fun <T : Any> T?.notNull(block: T.() -> Unit) = this?.block()

fun File.isNotExist(): Boolean {
    return !this.exists()
}

inline fun Any.runSafeTask(ignoreException: Boolean = false, function: () -> Unit): Boolean {
    return try {
        function.invoke()
        true
    } catch (exception: Exception) {
        if (!ignoreException) exception.printStackTrace()
        false
    }
}

fun (() -> Unit).runSafeTask(ignoreException: Boolean = true): Boolean {
    return try {
        this.invoke()
        true
    } catch (exception: Exception) {
        if (!ignoreException) exception.printStackTrace()
        false
    }
}

inline fun runIf(flag: Boolean, block: (Boolean) -> Unit) {
    if (flag) block(flag)
}