package net.spartanb312.render.util.thread

import net.spartanb312.render.core.common.timming.TickTimer
import net.spartanb312.render.features.SpartanCore
import java.util.concurrent.atomic.AtomicBoolean

class RepeatJob(
    private val mode: SpartanCore.Executors,
    private val delayTime: Int,
    task: () -> Unit
) : SpartanJob(task) {

    private val timer = TickTimer()
    private val available = AtomicBoolean(true)
    private val repeating = AtomicBoolean(true)
    private val stopped = AtomicBoolean(false)

    fun suspend() = repeating.set(false)

    fun resume() = repeating.set(true)

    fun tryRun() {
        if (isFinished) available.set(true)
        if (timer.tick(delayTime) && available.getAndSet(false)) {
            if (!started.getAndSet(true)) {
                SpartanCore.addTask(mode, this)
            }
        } else return
    }

    val isRepeating get() = repeating.get() && !stopped.get()
    val isRunning get() = isRepeating && isStarted && !isFinished

}