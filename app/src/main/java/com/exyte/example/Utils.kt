package com.exyte.example

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

@Stable
fun Dp.toPx(density: Density): Int = with(density) { this@toPx.roundToPx() }

@Stable
fun Dp.toPxf(density: Density): Float = with(density) { this@toPxf.toPx() }

@Stable
@Composable
fun Dp.toPx(): Int = toPx(LocalDensity.current)

@Stable
@Composable
fun Dp.toPxf(): Float = toPxf(LocalDensity.current)