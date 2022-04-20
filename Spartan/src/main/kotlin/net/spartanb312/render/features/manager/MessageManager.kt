package net.spartanb312.render.features.manager

import net.minecraft.network.play.client.CPacketChatMessage
import net.minecraft.util.text.TextComponentString
import net.spartanb312.render.Spartan.MOD_NAME
import net.spartanb312.render.core.common.graphics.ColorUtils.BOLD
import net.spartanb312.render.core.common.graphics.ColorUtils.DARK_RED
import net.spartanb312.render.core.common.graphics.ColorUtils.GOLD
import net.spartanb312.render.core.common.graphics.ColorUtils.GRAY
import net.spartanb312.render.core.common.graphics.ColorUtils.RESET
import net.spartanb312.render.core.event.inner.listener
import net.spartanb312.render.features.event.client.ChatEvent
import net.spartanb312.render.features.event.network.PacketEvent
import net.spartanb312.render.features.manager.CommandManager.runCommand
import net.spartanb312.render.util.Helper
import net.spartanb312.render.util.dsl.runSafe
import net.spartanb312.render.util.mixin.setMessage
import net.spartanb312.render.util.player.ChatFilter
import net.spartanb312.render.util.player.ChatModifier

object MessageManager : Helper {

    private val filters = mutableSetOf<ChatFilter>()
    private val modifiers = mutableSetOf<ChatModifier>()

    init {
        listener<PacketEvent.Send>(Int.MAX_VALUE, true) {
            if (it.packet is CPacketChatMessage) {
                var message = it.packet.message
                modifiers.forEach { cm -> message = cm.modifier(message) }
                if (message.runCommand()) it.cancel()
                ChatEvent(message).let { event ->
                    event.post()
                    if (event.cancelled
                        || filters.toList().any { cf -> cf.isEnabled && !cf.filter.invoke(message) }
                    ) it.cancel()
                    else it.packet.setMessage(event.message)
                }
            }
        }
    }

    fun ChatFilter.addFilter(): Boolean = filters.add(this)
    fun ChatModifier.addModifier(): Boolean = modifiers.add(this)
    fun ChatFilter.removeFilter(): Boolean = filters.remove(this)
    fun ChatModifier.removeModifier(): Boolean = modifiers.remove(this)
    fun addFilter(filter: (String) -> Boolean): Boolean = filters.add(ChatFilter(filter = filter))
    fun removeModifier(modifier: (String) -> String): Boolean = modifiers.add(ChatModifier(modifier = modifier))


    const val DELETE_ID = 69420

    @JvmStatic
    fun printRawMessage(message: String) {
        mc.addScheduledTask {
            if (mc.player != null && mc.world != null) {
                mc.ingameGUI.chatGUI.printChatMessage(TextComponentString(message))
            }
        }
    }

    @JvmStatic
    fun printInfo(message: String) =
        printRawMessage("${GRAY}[${GOLD}${MOD_NAME}${GRAY}] ${RESET}$message")

    @JvmStatic
    fun printWarning(message: String) =
        printRawMessage("${GRAY}[${GOLD}${BOLD}Warning${GRAY}] ${RESET}$message")

    @JvmStatic
    fun printError(message: String) =
        printRawMessage("${GRAY}[${DARK_RED}${BOLD}Error${GRAY}] ${RESET}$message")

    @JvmStatic
    fun printDebug(message: String) =
        printRawMessage("${GRAY}[${GRAY}${BOLD}Debug${GRAY}] ${RESET}$message")


    /**
     * No spam message
     */
    @JvmStatic
    fun printRawNoSpamMessage(message: String, deleteId: Int = DELETE_ID) {
        mc.addScheduledTask {
            runSafe {
                mc.ingameGUI.chatGUI.printChatMessageWithOptionalDeletion(TextComponentString(message), deleteId)
            }
        }
    }

    @JvmStatic
    fun printNoSpamInfo(message: String, deleteId: Int = DELETE_ID) =
        printRawNoSpamMessage("${GRAY}[${GOLD}${MOD_NAME}${GRAY}] ${RESET}$message", deleteId)

    @JvmStatic
    fun printNoSpamWarning(message: String, deleteId: Int = DELETE_ID) =
        printRawNoSpamMessage("${GRAY}[${GOLD}${BOLD}Warning${GRAY}] ${RESET}$message", deleteId)

    @JvmStatic
    fun printNoSpamError(message: String, deleteId: Int = DELETE_ID) =
        printRawNoSpamMessage("${GRAY}[${DARK_RED}${BOLD}Error${GRAY}] ${RESET}$message", deleteId)

    @JvmStatic
    fun printNoSpamDebug(message: String, deleteId: Int = DELETE_ID) =
        printRawNoSpamMessage("${GRAY}[${GRAY}${BOLD}Debug${GRAY}] ${RESET}$message", deleteId)

}