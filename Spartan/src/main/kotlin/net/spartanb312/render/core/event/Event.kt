package net.spartanb312.render.core.event

import net.spartanb312.render.core.common.interfaces.DisplayEnum
import net.spartanb312.render.core.event.inner.MainEventBus
import java.util.function.BooleanSupplier

open class Event {
    open var stage: Stage = Stage.PRE

    @JvmOverloads
    open fun post(block: BooleanSupplier? = null) {
        if (block == null || block.asBoolean) MainEventBus.post(this)
    }
}

private interface ICancellable {
    var cancelled: Boolean

    fun cancel() {
        cancelled = true
    }
}

open class Cancellable : ICancellable, Event() {
    override var cancelled = false
        set(value) {
            field = field || value
        }
}

enum class Stage(override val displayName: String) : DisplayEnum {
    PRE("Pre"),
    PERI("PerI"),
    POST("Post")
}