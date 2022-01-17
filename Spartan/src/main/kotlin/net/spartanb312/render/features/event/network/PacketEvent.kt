package net.spartanb312.render.features.event.network

import net.minecraft.network.Packet
import net.spartanb312.render.core.event.Cancellable

open class PacketEvent(val packet: Packet<*>) : Cancellable() {
    class Send(packet: Packet<*>) : PacketEvent(packet)
    class Receive(packet: Packet<*>) : PacketEvent(packet)
}
