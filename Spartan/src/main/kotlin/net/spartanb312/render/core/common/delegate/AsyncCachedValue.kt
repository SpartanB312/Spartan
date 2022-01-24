package net.spartanb312.render.core.common.delegate

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.spartanb312.render.core.common.timming.TickTimer
import net.spartanb312.render.core.common.timming.TimeUnit
import net.spartanb312.render.core.event.inner.SpartanScope
import kotlin.coroutines.CoroutineContext
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class AsyncCachedValue<T>(
    private val updateTime: Long,
    timeUnit: TimeUnit = TimeUnit.MILLISECONDS,
    private val context: CoroutineContext = Dispatchers.Default,
    private val block: () -> T
) : ReadWriteProperty<Any?, T> {
    private var value: T? = null
    private val timer = TickTimer(timeUnit)

    fun get(): T {
        val cached = value

        return when {
            cached == null -> {
                block().also { value = it }
            }
            timer.tickAndReset(updateTime) -> {
                SpartanScope.launch(context) {
                    value = block()
                }
                cached
            }
            else -> {
                cached
            }
        }
    }

    fun update() {
        timer.reset()
        SpartanScope.launch(context) {
            value = block()
        }
    }

    override operator fun getValue(thisRef: Any?, property: KProperty<*>) = get()

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }
}