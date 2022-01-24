package net.spartanb312.render.graphics.api.font

import java.awt.Font

enum class Style(val code: String, val codeChar: Char, val styleConst: Int) {
    REGULAR("§r", 'r', Font.PLAIN),
    BOLD("§l", 'l', Font.BOLD),
    ITALIC("§o", 'o', Font.ITALIC)
}