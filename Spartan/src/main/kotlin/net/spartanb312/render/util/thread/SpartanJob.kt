package net.spartanb312.render.util.thread

open class SpartanJob(private val task: () -> Unit) {

    @Volatile
    var state = State.Waiting
        protected set

    fun launch() {
        if (state == State.Waiting) state = State.Launched
    }

    fun execute() {
        if (state == State.Launched) {
            state = State.Running
            task.invoke()
            state = State.Finished
        }
    }

    fun launchAndExecute() {
        launch()
        execute()
    }

    open fun reset() {
        if (state == State.Finished) {
            state = State.Waiting
        }
    }

    enum class State {
        Waiting,
        Launched,
        Finished,
        Running,
        Suspended,
        Stopped
    }

}