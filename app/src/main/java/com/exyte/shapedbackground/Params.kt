package com.example.likeinstabackgroundtext

import android.graphics.Color
import java.util.ArrayList

data class BackgroundParams(
    var paddingHorizontal: Float = Float.MIN_VALUE,
    var paddingVertical: Float = Float.MIN_VALUE,
    var gradient: ArrayList<Int> = arrayListOf() ,
    var backgroundColor: Int = Color.TRANSPARENT,
    var cornerRadius: Float = Float.MIN_VALUE,
    var shadow: ShadowParams? = null,
) {
    fun shadow(init: ShadowParams.() -> Unit) {
        shadow = ShadowParams().apply(init)
    }
}

data class ShadowParams(
    var radius: Float = DEFAULT_SHADOW_RADIUS,
    var dx: Float = DEFAULT_SHADOW_DX,
    var dy: Float = DEFAULT_SHADOW_DY,
)

const val DEFAULT_SHADOW_RADIUS = 20f
const val DEFAULT_SHADOW_DX = 2f
const val DEFAULT_SHADOW_DY = 6f