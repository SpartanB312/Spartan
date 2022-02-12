package net.spartanb312.render.features.ui.item

import net.spartanb312.render.features.ui.item.font.EffectFont

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

fun <T : EffectFont> T.checkClick(block: EffectFont.(mouseX: Int, mouseY: Int, button: Int) -> Boolean): T =
    this.also { it.action = block }

inline fun <T : EffectFont> T.onClick(crossinline block: EffectFont.(mouseX: Int, mouseY: Int, button: Int) -> Unit): T =
    checkClick { mouseX, mouseY, button ->
        block(mouseX, mouseY, button)
        true
    }

inline fun <T : EffectFont> T.withButton(buttonId: Int = 0, crossinline block: EffectFont.() -> Unit): T =
    checkClick { mouseX, mouseY, button ->
        if (isHoovered(mouseX, mouseY) && button == buttonId) {
            block()
            true
        } else false
    }