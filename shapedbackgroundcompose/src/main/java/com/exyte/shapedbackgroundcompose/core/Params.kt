package com.exyte.shapedbackgroundcompose.core

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class BackgroundParams(
    val paddingHorizontal: Dp = Dp.Unspecified,
    val paddingVertical: Dp = Dp.Unspecified,
    val gradient: ListWrapper<Color> = ListWrapper.empty(),
    val backgroundColor: Color = Color.Transparent,
    val cornerRadius: Float = 0f,
    val shadow: ShadowParams? = null,
)

@Immutable
data class ShadowParams(
    val radius: Dp = 5.dp,
    val dx: Dp = 1.dp,
    val dy: Dp = 1.dp,
    val color: Color,
)