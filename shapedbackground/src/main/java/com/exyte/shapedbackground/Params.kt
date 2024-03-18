package com.exyte.shapedbackground

import android.graphics.Color
import java.util.ArrayList
import java.util.Collections

data class BackgroundParams(
    var paddingHorizontal: Float = Float.MIN_VALUE,
    var paddingVertical: Float = Float.MIN_VALUE,
    var gradient: List<Int> = emptyList(),
    var backgroundColor: Int = Color.TRANSPARENT,
    var cornerRadius: Float = 0f,
    var shadow: ShadowParams? = null,
)

data class ShadowParams(
    var radius: Float = DEFAULT_SHADOW_RADIUS,
    var dx: Float = DEFAULT_SHADOW_DX,
    var dy: Float = DEFAULT_SHADOW_DY,
)

const val DEFAULT_SHADOW_RADIUS = 20f
const val DEFAULT_SHADOW_DX = 2f
const val DEFAULT_SHADOW_DY = 6f
