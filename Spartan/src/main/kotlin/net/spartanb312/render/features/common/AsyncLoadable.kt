package net.spartanb312.render.features.common

import kotlinx.coroutines.async
import net.spartanb312.render.core.common.ClassUtils
import net.spartanb312.render.core.event.inner.SpartanScope
import net.spartanb312.render.launch.InitializationManager.DEFAULT_INIT_SCAN_EXCLUSION
import net.spartanb312.render.launch.InitializationManager.SCAN_GROUP
import net.spartanb312.render.launch.Logger
import kotlin.system.measureTimeMillis

interface AsyncLoadable {
    suspend fun init()

    companion object {
        val classes = SpartanScope.async {
            val list: List<Class<*>>
            val time = measureTimeMillis {
                list = ClassUtils.findClasses(
                    packageName = SCAN_GROUP,
                    predicate = DEFAULT_INIT_SCAN_EXCLUSION
                )
            }
            Logger.info("${list.size} classes found, took ${time}ms")
            list
        }
    }
}