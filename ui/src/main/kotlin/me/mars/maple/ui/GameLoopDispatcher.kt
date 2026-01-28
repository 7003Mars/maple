package me.mars.maple.ui

import arc.Events
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import mindustry.game.EventType.Trigger
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.coroutines.CoroutineContext

class GameLoopDispatcher() : CoroutineDispatcher() {
    val tasks: ConcurrentLinkedQueue<Runnable> = ConcurrentLinkedQueue()

    init {
        Events.run(Trigger.update) {
            while (tasks.isNotEmpty()) {
                tasks.poll().run()
            }
        }
    }

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        tasks.add(block)
    }
}

val gameLoopDispatcher by lazy { GameLoopDispatcher() }