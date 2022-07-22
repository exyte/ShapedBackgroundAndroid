package com.exyte.shapedbackgroundcompose.modifier

import android.graphics.Color
import android.graphics.CornerPathEffect
import android.graphics.Paint
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import com.exyte.shapedbackgroundcompose.core.*

@Composable
fun RoundedBackground(
    textLayoutResult: TextLayoutResult?,
    backgroundParams: BackgroundParams,
    textSize: TextUnit
) {
    val density = LocalDensity.current
    androidx.compose.foundation.Canvas(modifier = Modifier) {
        textLayoutResult?.let { textLayoutResult ->
            drawIntoCanvas {
                if (backgroundParams.cornerRadius == Dp.Unspecified) {
                    backgroundParams.cornerRadius =
                        calculateCornerRadius(textSize.toPx(), density)
                }
                val paint = configurePaint(backgroundParams, it, density)
                val path = createBackgroundPath(textLayoutResult, backgroundParams, density)
                it.nativeCanvas.drawPath(path, paint)
            }
        }
    }
}

fun calculateCornerRadius(textSizePx: Float,density: Density) = (textSizePx / DEFAULT_CORNER_DIVIDER).toDp(density)

fun configurePaint(params: BackgroundParams, canvas: Canvas, density: Density): Paint {
    return Paint().apply {
        paintBackground(params, this, canvas.nativeCanvas.height.toFloat())

        params.shadow?.let { shadowParams ->
            setShadow(this, shadowParams, density)
        }

        pathEffect = CornerPathEffect(params.cornerRadius.toPxf(density))
    }
}

private fun paintBackground(params: BackgroundParams, paint: Paint, height: Float) {
    when {
        params.gradient.isNotEmpty() -> {
            paint.shader = LinearGradientShader(
                from = Offset(0f, 0f),
                to = Offset(0f, height),
                params.gradient,
            )
        }
        else -> {
            paint.color = params.backgroundColor.toArgb()
        }
    }
}

private fun setShadow(paint: Paint, shadowParams: ShadowParams, density: Density) {
    paint.setShadowLayer(
        shadowParams.radius.toPxf(density),
        shadowParams.dx.toPxf(density),
        shadowParams.dy.toPxf(density),
        DEFAULT_SHADOW_COLOR
    )
}

const val DEFAULT_SHADOW_COLOR = Color.DKGRAY
const val DEFAULT_CORNER_DIVIDER = 2.25f