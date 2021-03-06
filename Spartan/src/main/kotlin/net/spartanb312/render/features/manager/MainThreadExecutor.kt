package net.spartanb312.render.features.manager

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.completeWith
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import net.spartanb312.render.util.mc

object MainThreadExecutor {

    private val jobs = ArrayList<MainThreadJob<*>>()
    private val mutex = Mutex()

    fun runJobs() {
        if (jobs.isEmpty()) return

        runBlocking {
            mutex.withLock {
                jobs.forEach {
                    it.run()
                }
                jobs.clear()
            }
        }
    }

    fun <T> add(block: () -> T) = MainThreadJob(block).apply {
        if (mc.isCallingFromMinecraftThread) {
            run()
        } else {
            runBlocking {
                mutex.withLock {
                    jobs.add(this@apply)
                }
            }
        }
    }.deferred

    suspend fun <T> addSuspend(block: () -> T) = MainThreadJob(block).apply {
        if (mc.isCallingFromMinecraftThread) {
            run()
        } else {
            mutex.withLock {
                jobs.add(this)
            }
        }
    }.deferred

    private class MainThreadJob<T>(private val block: () -> T) {
        val deferred = CompletableDeferred<T>()

        fun run() = deferred.completeWith(
            runCatching { block.invoke() }
        )
    }

    fun <T> onMainThread(block: () -> T) = add(block)

    suspend fun <T> onMainThreadSuspend(block: () -> T) = addSuspend(block)

}