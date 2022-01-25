package net.spartanb312.render.features.common

import net.spartanb312.render.core.common.interfaces.DisplayEnum

enum class Category(
    override val displayName: String,
    val generalCategory: GeneralCategory = GeneralCategory.Undefined
) : DisplayEnum {

    //3D Renderer
    Player("Player", GeneralCategory.Renderer),
    World("World", GeneralCategory.Renderer),

    //2D HUD
    Combat("Combat", GeneralCategory.HUD),
    Information("Information", GeneralCategory.HUD),
    Spartan("Spartan", GeneralCategory.HUD),

    //Other
    Hidden("Hidden", GeneralCategory.Undefined),
    Setting("Setting", GeneralCategory.Setting);

    enum class GeneralCategory(override val displayName: CharSequence) : DisplayEnum {
        Undefined("Other"),
        Setting("Setting"),
        Command("Command"),
        Renderer("Renderer"),
        HUD("HUD"),
        Screen("Screen"),
        Theme("Theme")
    }

    val idHUD get() = generalCategory == GeneralCategory.HUD
    val isRenderer get() = generalCategory == GeneralCategory.Renderer
    val isScreen get() = generalCategory == GeneralCategory.Screen

}