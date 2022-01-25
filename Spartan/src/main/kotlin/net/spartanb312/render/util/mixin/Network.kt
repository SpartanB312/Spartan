package net.spartanb312.render.util.mixin

import net.minecraft.network.play.client.CPacketChatMessage
import net.spartanb312.render.mixin.accessor.network.AccessorCPacketChatMessage

fun CPacketChatMessage.setMessage(message: String) = (this as AccessorCPacketChatMessage).setMessage_spartan(message)