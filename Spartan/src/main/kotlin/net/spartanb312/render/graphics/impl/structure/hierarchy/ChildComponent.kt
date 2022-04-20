package net.spartanb312.render.graphics.impl.structure.hierarchy

import net.spartanb312.render.graphics.impl.structure.Component

interface ChildComponent : Component {
    val father: FatherComponent
    val asFather: FatherComponent? get() = if (this is FatherComponent) this else null
    val grandsons: List<ChildComponent>? get() = asFather?.children
}