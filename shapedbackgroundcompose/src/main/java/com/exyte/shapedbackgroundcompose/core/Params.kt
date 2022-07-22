package com.exyte.shapedbackgroundcompose.core

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.util.ArrayList

data class BackgroundParams(
    var paddingHorizontal: Dp = Dp.Unspecified,
    var paddingVertical: Dp = Dp.Unspecified,
    var gradient: ArrayList<Color> = arrayListOf(),
    var backgroundColor: Color = Color.Transparent,
    var cornerRadius: Dp = Dp.Unspecified,
    var shadow: ShadowParams? = null,
) {
    fun shadow(init: ShadowParams.() -> Unit) {
        shadow = ShadowParams().apply(init)
    }
}

data class ShadowParams(
    var radius: Dp = DEFAULT_SHADOW_RADIUS,
    var dx: Dp = DEFAULT_SHADOW_DX,
    var dy: Dp = DEFAULT_SHADOW_DY,
)

val DEFAULT_SHADOW_RADIUS = 5.dp
val DEFAULT_SHADOW_DX = 1.dp
val DEFAULT_SHADOW_DY = 3.dp