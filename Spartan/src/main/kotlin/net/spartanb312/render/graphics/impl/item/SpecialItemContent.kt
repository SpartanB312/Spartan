package net.spartanb312.render.graphics.impl.item

import net.spartanb312.render.graphics.impl.item.button.EffectButton
import net.spartanb312.render.graphics.impl.item.text.EffectText

/**
 * For resetting all Resettable
 */
interface SpecialItemContent {
    val registered: MutableSet<SpecialItem>
    fun resetAllItems() = registered.forEach(SpecialItem::reset)
    fun onItemsMouseClicked(mouseX: Int, mouseY: Int, button: Int): Boolean {
        for (item in registered) {
            if (item.onMouseClicked(mouseX, mouseY, button)) return true
        }
        return false
    }

    fun <T : SpecialItem> T.register(): T = this.also { registered.add(this) }
}

fun <T : EffectText> T.checkClick(block: EffectText.(mouseX: Int, mouseY: Int, button: Int) -> Boolean): T =
    this.also { it.action = block }

inline fun <T : EffectText> T.onClick(crossinline block: EffectText.(mouseX: Int, mouseY: Int, button: Int) -> Unit): T =
    checkClick { mouseX, mouseY, button ->
        block(mouseX, mouseY, button)
        true
    }

inline fun <T : EffectText> T.withButton(buttonId: Int = 0, crossinline block: EffectText.() -> Unit): T =
    checkClick { mouseX, mouseY, button ->
        if (isHoovered(mouseX, mouseY) && button == buttonId) {
            block()
            true
        } else false
    }

fun <T : EffectButton> T.checkClick(block: EffectButton.(mouseX: Int, mouseY: Int, button: Int) -> Boolean): T =
    this.also { it.action = block }

inline fun <T : EffectButton> T.onClick(crossinline block: EffectButton.(mouseX: Int, mouseY: Int, button: Int) -> Unit): T =
    checkClick { mouseX, mouseY, button ->
        block(mouseX, mouseY, button)
        true
    }

inline fun <T : EffectButton> T.withButton(buttonId: Int = 0, crossinline block: EffectButton.() -> Unit): T =
    checkClick { mouseX, mouseY, button ->
        if (isHoovered(mouseX, mouseY) && button == buttonId) {
            block()
            true
        } else false
    }