package net.spartanb312.render.features.command.commands

import net.spartanb312.render.features.command.Command
import net.spartanb312.render.features.command.ExecutionScope
import net.spartanb312.render.features.command.double
import net.spartanb312.render.features.manager.MessageManager
import net.spartanb312.render.util.mc

object TP : Command(
    name = "TP",
    prefix = "tp",
    description = "Teleport you to the coords",
    syntax = "tp <x> <y> <z>",
) {
    override fun ExecutionScope.onCall() {
        double { x ->
            double { y ->
                double { z ->
                    mc.player?.setPosition(x, y, z)
                    MessageManager.printInfo("Teleported you to $x $y $z")
                }
            }
        }
    }
}