package com.exyte.shapedbackground

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.CornerPathEffect
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.Shader
import android.graphics.drawable.ColorDrawable

class ShapeBackgroundDrawable(
    private val shape: Path,
    private val params: BackgroundParams,
    private val context: Context,
    private val sizeText: Float,
) : ColorDrawable() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        val cornerRadius = calculateCornerRadius(sizeText)

        pathEffect = CornerPathEffect(cornerRadius)

        params.shadow?.let {
            setShadowLayer(
                it.radius,
                it.dx,
                it.dy,
                DEFAULT_SHADOW_COLOR,
            )
        }
    }

    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)
        paintBackground(bottom, top)
    }

    @Deprecated("Deprecated in java", replaceWith = ReplaceWith(""))
    override fun getOpacity(): Int = PixelFormat.TRANSPARENT

    override fun draw(canvas: Canvas) {
        canvas.drawPath(shape, paint)
    }

    private fun calculateCornerRadius(textSize: Float) =
        textSize / context.resources.displayMetrics.scaledDensity * 1.5f

    private fun paintBackground(bottom: Int, top: Int) {
        when {
            params.gradient.isNotEmpty() -> paint.shader = createLinearGradient(bottom, top)
            else -> paint.color = params.backgroundColor
        }
    }

    private fun createLinearGradient(bottom: Int, top: Int): LinearGradient =
        LinearGradient(
            0f,
            0f,
            0f,
            (bottom - top).toFloat(),
            params.gradient.toIntArray(),
            null,
            Shader.TileMode.CLAMP
        )


    companion object {
        const val DEFAULT_SHADOW_COLOR = Color.DKGRAY
    }
}