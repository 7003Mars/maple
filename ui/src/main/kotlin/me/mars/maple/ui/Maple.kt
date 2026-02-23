package me.mars.maple.ui

import androidx.compose.runtime.BroadcastFrameClock
import androidx.compose.runtime.Composable
import app.cash.redwood.compose.RedwoodComposition
import app.cash.redwood.ui.Cancellable
import app.cash.redwood.ui.OnBackPressedCallback
import app.cash.redwood.ui.OnBackPressedDispatcher
import app.cash.redwood.ui.UiConfiguration
import app.cash.redwood.widget.WidgetSystem
import arc.Events
import arc.scene.Element
import arc.struct.ObjectMap
import arc.struct.Seq
import arc.util.Reflect
import arc.util.Time
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.mars.maple.renderer.elem.Box
import me.mars.maple.renderer.widgets.BoxWidget
import me.mars.maple.schema.compose.BoxScope
import mindustry.game.EventType
import mindustry.mod.Mod

class Maple : Mod() {
    override fun init() {
//        Events.on(EventType.ClientLoadEvent::class.java) {
//            main()
//        }
    }

    companion object {
        fun runMaple(widgetSystem: WidgetSystem<Element>, content: @Composable BoxScope.() -> Unit): MapleUI {
            val root = Box()
            val maple = MapleUI(root, widgetSystem)
            maple.composition.setContent {
                // TODO Ultra cursed stuff, Im not sure if this will even work properly
                (object : BoxScope {}).content()
            }
            return maple
        }
    }
}

class MapleUI(val root: Box, val widgetSystem: WidgetSystem<Element>) {
    private var hasNewFrames: Boolean = false
    private val updateListener = {
        if (hasNewFrames) {
            clock.sendFrame(Time.nanos())
            hasNewFrames = false
        }
    }
    val clock = BroadcastFrameClock { hasNewFrames = true }
    val composeScope = CoroutineScope(gameLoopDispatcher + clock)

    val composition: RedwoodComposition

    init {
        val onBackPressedDispatcher = object : OnBackPressedDispatcher {
            override fun addCallback(onBackPressedCallback: OnBackPressedCallback): Cancellable {
                // TODO
                return object : Cancellable {
                    override fun cancel() {
                        // TODO
                    }
                }
            }
        }
        val uiConfigurations: StateFlow<UiConfiguration> = MutableStateFlow(UiConfiguration())
        composition = RedwoodComposition(
            scope = composeScope,
            container = BoxWidget(root).children,
            onBackPressedDispatcher = onBackPressedDispatcher,
            saveableStateRegistry = null,
            uiConfigurations = uiConfigurations,
            widgetSystem = widgetSystem,
            onChanges = { hasNewFrames = true }
        )

        Events.run(EventType.Trigger.update, updateListener)
    }

    fun cancel() {
        composition.cancel()
        val listeners = Reflect.get<ObjectMap<Any, Seq<Any>>>(Events::class.java, "events").get(EventType.Trigger.update)
        listeners.remove(updateListener)
    }
}