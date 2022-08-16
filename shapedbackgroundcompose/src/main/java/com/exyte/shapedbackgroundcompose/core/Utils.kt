package com.exyte.shapedbackgroundcompose.core

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

@Stable
fun Float.toDp(density: Density): Dp = with(density) { this@toDp.toDp() }

@Stable
fun Dp.toPxf(density: Density): Float = with(density) { this@toPxf.toPx() }