package com.exyte.shapedbackgroundcompose.modifier

import android.graphics.Color
import android.graphics.CornerPathEffect
import android.graphics.Paint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.Density
import com.exyte.shapedbackgroundcompose.core.BackgroundParams
import com.exyte.shapedbackgroundcompose.core.createBackgroundPath
import com.exyte.shapedbackgroundcompose.core.toPxf

@Composable
fun RoundedBackground(
    textLayoutResult: TextLayoutResult?,
    backgroundParams: BackgroundParams,
) {
    val density = LocalDensity.current
    androidx.compose.foundation.Canvas(modifier = Modifier) {
        textLayoutResult?.let { textLayoutResult ->
            drawIntoCanvas {
                val paint = configurePaint(backgroundParams, it, density)
                val path = createBackgroundPath(
                    textBackgroundResult = textLayoutResult,
                    paddingHorizontal = backgroundParams.paddingHorizontal,
                    paddingVertical = backgroundParams.paddingVertical,
                    density = density,
                )
                it.nativeCanvas.drawPath(path, paint)
            }
        }
    }
}

private fun calculateCornerRadius(textSizePx: Float) = textSizePx / DEFAULT_CORNER_DIVIDER

private fun configurePaint(params: BackgroundParams, canvas: Canvas, density: Density): Paint = Paint().apply {
    paintBackground(params, this, canvas.nativeCanvas.height.toFloat())

    if (params.shadow != null) {
        setShadowLayer(
            params.shadow.radius.toPxf(density),
            params.shadow.dx.toPxf(density),
            params.shadow.dy.toPxf(density),
            Color.DKGRAY,
        )
    }

    val cornerRadius = calculateCornerRadius(params.cornerRadius)
    pathEffect = CornerPathEffect(cornerRadius)
}

private fun paintBackground(params: BackgroundParams, paint: Paint, height: Float) {
    when {
        params.gradient.isNotEmpty() -> paint.shader = LinearGradientShader(
            from = Offset(0f, 0f),
            to = Offset(0f, height),
            colors = params.gradient,
        )

        else -> paint.color = params.backgroundColor.toArgb()
    }
}

private const val DEFAULT_CORNER_DIVIDER = 2.25f