package net.spartanb312.render.features.ui.structure.hierarchy

interface FatherComponent {
    var state: Boolean
    val children: List<ChildComponent>
    val asChild: ChildComponent? get() = if (this is ChildComponent) this else null
    val grandpa: FatherComponent? get() = asChild?.father
}