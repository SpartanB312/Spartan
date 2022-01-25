package net.spartanb312.render.features.manager

import net.spartanb312.render.core.common.KeyBind
import net.spartanb312.render.features.event.client.InputEvent
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse
import java.util.concurrent.CopyOnWriteArrayList

object InputManager {

    private val listeners = CopyOnWriteArrayList<KeyBind>()

    fun KeyBind.register() = listeners.add(this)
    fun KeyBind.unregister() = listeners.remove(this)

    @JvmStatic
    fun onKey(key: Int) {
        listeners.forEach {
            if (it.key.size > 1) {
                if (it.key.any { it2 ->
                        it2 == key
                    } && it.key.all { it3 ->
                        Keyboard.isKeyDown(it3)
                    }) it.action.invoke()
            } else if (it.key.size == 1 && it.key[0] == key) {
                it.action.invoke()
            }
        }
    }

    @JvmStatic
    fun onKeyInput() = InputEvent.Keyboard(Keyboard.getEventKey(), Keyboard.getEventKeyState()).post()

    @JvmStatic
    fun onMouseInput() = InputEvent.Mouse(Mouse.getEventButton(), Mouse.getEventButtonState()).post()

}