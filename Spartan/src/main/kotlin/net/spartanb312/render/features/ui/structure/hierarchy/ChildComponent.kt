package net.spartanb312.render.features.ui.structure.hierarchy

import net.spartanb312.render.features.ui.structure.Component

interface ChildComponent : Component {
    val father: FatherComponent
    val asFather: FatherComponent? get() = if (this is FatherComponent) this else null
    val grandsons: List<ChildComponent>? get() = asFather?.children
}