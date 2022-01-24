package net.spartanb312.render.graphics.api.texture

inline fun <T : AbstractTexture> T.useTexture(
    force: Boolean = false,
    block: AbstractTexture.() -> Unit
): T {
    if (force || available) {
        bindTexture()
        block()
        unbindTexture()
    }
    return this
}