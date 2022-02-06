package net.spartanb312.render.features.common

import net.spartanb312.render.core.common.interfaces.DisplayEnum

enum class Category(
    override val displayName: String,
    val generalCategory: GeneralCategory = GeneralCategory.Undefined
) : DisplayEnum {

    //Utilities
    CombatUtility("Combat", GeneralCategory.Utility),
    MiscUtility("Misc", GeneralCategory.Utility),
    MovementUtility("Movement", GeneralCategory.Utility),
    PlayerUtility("Player", GeneralCategory.Utility),

    //Renderers
    PlayerRenderer("Player", GeneralCategory.Renderer),
    WorldRenderer("World", GeneralCategory.Renderer),

    //HUDs
    CombatHUD("Combat", GeneralCategory.HUD),
    InformationHUD("Information", GeneralCategory.HUD),
    SpartanHUD("Spartan", GeneralCategory.HUD),

    //Settings
    GeneralSetting("General", GeneralCategory.Setting),
    AudioSetting("Audio", GeneralCategory.Setting),
    VideoSetting("Video", GeneralCategory.Setting),
    UISetting("UI", GeneralCategory.Setting),

    //Commands
    ConsoleCommand("Console", GeneralCategory.Command),
    EntityCommand("Entity", GeneralCategory.Command),
    ManagementCommand("Management", GeneralCategory.Command),
    MiscCommand("Misc", GeneralCategory.Command),
    PlayerCommand("Player", GeneralCategory.Command),
    WorldCommand("World", GeneralCategory.Command),

    //Theme
    Theme("Theme", GeneralCategory.Theme),

    //Undefined
    Hidden("Hidden", GeneralCategory.Undefined);

    enum class GeneralCategory(override val displayName: CharSequence) : DisplayEnum {
        Undefined("Other"),
        Setting("Setting"),
        Command("Command"),
        Renderer("Renderer"),
        HUD("HUD"),
        Screen("Screen"),
        Theme("Theme"),
        Utility("Utility")
    }

    val idHUD get() = generalCategory == GeneralCategory.HUD
    val isRenderer get() = generalCategory == GeneralCategory.Renderer
    val isScreen get() = generalCategory == GeneralCategory.Screen

}