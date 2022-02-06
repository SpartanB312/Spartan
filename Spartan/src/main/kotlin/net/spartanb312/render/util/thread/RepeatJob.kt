package net.spartanb312.render.util.thread

import net.spartanb312.render.core.common.timming.TickTimer
import net.spartanb312.render.features.SpartanCore

class RepeatJob(
    private val mode: SpartanCore.Executors,
    private val delayTime: Int,
    task: () -> Unit
) : SpartanJob(task) {

    private val timer = TickTimer()

    fun suspend() {
        if (state != State.Suspended && state != State.Stopped) state = State.Suspended
    }

    fun resume() {
        if (state == State.Suspended) state = State.Waiting
    }

    fun stop() {
        state = State.Stopped
    }

    //Return : should remove
    fun tryRun(): Boolean {
        if (state == State.Stopped) return true
        if (state == State.Finished) state = State.Waiting
        if (timer.tick(delayTime) && state == State.Waiting) {
            launch()
            timer.reset()
            SpartanCore.addTask(mode, this)
        }
        return false
    }

}