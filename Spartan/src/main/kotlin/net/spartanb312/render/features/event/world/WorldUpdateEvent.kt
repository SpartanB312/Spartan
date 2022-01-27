package net.spartanb312.render.features.event.world

import net.minecraft.block.state.IBlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.SoundCategory
import net.minecraft.util.SoundEvent
import net.minecraft.util.math.BlockPos
import net.spartanb312.render.core.event.Cancellable

sealed class WorldUpdateEvent : Cancellable() {

    sealed class EntityUpdate(val entity: Entity) : WorldUpdateEvent() {
        class Add(entity: Entity) : EntityUpdate(entity)
        class Remove(entity: Entity) : EntityUpdate(entity)
    }

    class BlockUpdate(
        val pos: BlockPos,
        val oldState: IBlockState,
        val newState: IBlockState
    ) : WorldUpdateEvent()

    class RenderUpdate(
        val x1: Int,
        val y1: Int,
        val z1: Int,
        val x2: Int,
        val y2: Int,
        val z2: Int
    ) : WorldUpdateEvent()

    class NotifyLightSet(pos: BlockPos) : WorldUpdateEvent()

    class PlaySound(
        val player: EntityPlayer?,
        val soundIn: SoundEvent,
        val category: SoundCategory,
        val x: Double,
        val y: Double,
        val z: Double,
        val volume: Float,
        val pitch: Float
    ) : WorldUpdateEvent()

    class PlayRecord(
        val soundIn: SoundEvent,
        val pos: BlockPos
    ) : WorldUpdateEvent()

}
