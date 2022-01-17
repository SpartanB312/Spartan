package net.spartanb312.render.util.thread

import java.util.concurrent.atomic.AtomicBoolean
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

open class SpartanJob(private val task: () -> Unit) {

    protected val started = AtomicBoolean(false)
    private val finished = AtomicBoolean(false)

    fun execute() {
        if (!started.getAndSet(true)) {
            task.invoke()
            finished.set(true)
        }
    }

    fun reset() {
        if (finished.get() && !started.get()) {
            started.set(false)
            finished.set(false)
        }
    }

    val isStarted get() = started.get()
    val isFinished get() = finished.get()

}