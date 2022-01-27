package net.spartanb312.render.features.manager.ingame

import net.minecraft.block.state.IBlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.SoundCategory
import net.minecraft.util.SoundEvent
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorldEventListener
import net.minecraft.world.World
import net.spartanb312.render.features.event.world.BlockBreakEvent
import net.spartanb312.render.features.event.world.WorldUpdateEvent

object WorldListener : IWorldEventListener {

    override fun notifyBlockUpdate(
        worldIn: World,
        pos: BlockPos,
        oldState: IBlockState,
        newState: IBlockState,
        flags: Int
    ) {
        if (flags and 3 != 0) {
            WorldUpdateEvent.BlockUpdate(pos, oldState, newState).post()
        }
    }

    override fun onEntityAdded(entityIn: Entity) {
        WorldUpdateEvent.EntityUpdate.Add(entityIn).post()
    }

    override fun onEntityRemoved(entityIn: Entity) {
        WorldUpdateEvent.EntityUpdate.Remove(entityIn).post()
    }

    override fun sendBlockBreakProgress(breakerId: Int, pos: BlockPos, progress: Int) {
        BlockBreakEvent(breakerId, pos, progress).post()
    }

    override fun notifyLightSet(pos: BlockPos) {
        WorldUpdateEvent.NotifyLightSet(pos).post()
    }

    override fun markBlockRangeForRenderUpdate(x1: Int, y1: Int, z1: Int, x2: Int, y2: Int, z2: Int) {
        WorldUpdateEvent.RenderUpdate(x1, y1, z1, x2, y2, z2).post()
    }

    override fun playSoundToAllNearExcept(
        player: EntityPlayer?,
        soundIn: SoundEvent,
        category: SoundCategory,
        x: Double,
        y: Double,
        z: Double,
        volume: Float,
        pitch: Float
    ) {
        WorldUpdateEvent.PlaySound(player, soundIn, category, x, y, z, volume, pitch).post()
    }

    override fun playRecord(soundIn: SoundEvent, pos: BlockPos) {
        WorldUpdateEvent.PlayRecord(soundIn, pos).post()
    }

    override fun spawnParticle(
        particleID: Int,
        ignoreRange: Boolean,
        xCoord: Double,
        yCoord: Double,
        zCoord: Double,
        xSpeed: Double,
        ySpeed: Double,
        zSpeed: Double,
        vararg parameters: Int
    ) {

    }

    override fun spawnParticle(
        id: Int,
        ignoreRange: Boolean,
        minimiseParticleLevel: Boolean,
        x: Double,
        y: Double,
        z: Double,
        xSpeed: Double,
        ySpeed: Double,
        zSpeed: Double,
        vararg parameters: Int
    ) {

    }

    override fun broadcastSound(soundID: Int, pos: BlockPos, data: Int) {

    }

    override fun playEvent(player: EntityPlayer?, type: Int, blockPosIn: BlockPos, data: Int) {

    }

}