package net.spartanb312.render.util.player

import net.spartanb312.render.features.common.ToggleableFeature

class ChatFilter(override var isEnabled: Boolean = true, val filter: (String) -> Boolean) : ToggleableFeature