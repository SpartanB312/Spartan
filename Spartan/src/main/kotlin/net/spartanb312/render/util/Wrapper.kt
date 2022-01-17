package net.spartanb312.render.util

import net.minecraft.client.Minecraft
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.multiplayer.WorldClient
import net.spartanb312.render.mixin.accessor.AccessorMinecraft
import java.io.InputStream
import java.nio.ByteBuffer
import java.util.function.BooleanSupplier

interface Helper {
    val mc: Minecraft get() = Minecraft.getMinecraft()
}

val safeCheck = BooleanSupplier { mc.player != null && mc.world != null }

inline val mc: Minecraft get() = Minecraft.getMinecraft()
inline val player: EntityPlayerSP get() = Minecraft.getMinecraft().player
inline val world: WorldClient get() = Minecraft.getMinecraft().world

fun readImageToBuffer(inputStream: InputStream?): ByteBuffer {
    return (Minecraft.getMinecraft() as AccessorMinecraft).readImageToBuffer_spartan(inputStream)
}
