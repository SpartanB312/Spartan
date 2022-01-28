package net.spartanb312.render.util.player

import net.spartanb312.render.features.common.ToggleableFeature

class ChatModifier(override var isEnabled: Boolean = true, val modifier: (String) -> String) : ToggleableFeature