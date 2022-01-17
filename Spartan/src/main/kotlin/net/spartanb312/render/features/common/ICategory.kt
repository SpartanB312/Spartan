package net.spartanb312.render.features.common

import net.spartanb312.render.core.common.interfaces.DisplayEnum

enum class Category(
    override val displayName: String,
    val subcategory: Subcategory = Subcategory.Undefined
) : DisplayEnum {

    //3D Renderer
    Player("Player", Subcategory.Renderer),
    World("World", Subcategory.Renderer),

    //2D HUD
    Combat("Combat", Subcategory.HUD),
    Information("Information", Subcategory.HUD),
    Spartan("Spartan", Subcategory.HUD),

    //Other
    Hidden("Hidden", Subcategory.Undefined),
    Setting("Setting", Subcategory.Setting);

    enum class Subcategory(override val displayName: CharSequence) : DisplayEnum {
        Undefined("Other"),
        Setting("Setting"),
        Command("Command"),
        Renderer("Renderer"),
        HUD("HUD"),
        Screen("Screen"),
        Theme("Theme")
    }

    val idHUD get() = subcategory == Subcategory.HUD
    val isRenderer get() = subcategory == Subcategory.Renderer
    val isScreen get() = subcategory == Subcategory.Screen

}