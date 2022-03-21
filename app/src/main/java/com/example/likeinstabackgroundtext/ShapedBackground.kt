package com.example.likeinstabackgroundtext

import android.graphics.*
import android.text.Layout
import android.widget.TextView
import androidx.core.view.doOnLayout
import androidx.core.widget.doAfterTextChanged


fun TextView.roundedBackground(init: BackgroundParams.() -> Unit) {
    val params = BackgroundParams().apply(init)
    roundedBackground(params)
}

fun TextView.roundedBackground(params: BackgroundParams) {
    doOnLayout {
        drawBackground(params)
    }
    doAfterTextChanged {
        drawBackground(params)
    }
}

private fun TextView.drawBackground(params: BackgroundParams) {
    val shape = buildBackground(params)
    background = ShapeBackgroundDrawable(shape, params, context, textSize)
}

private fun TextView.buildBackground(params: BackgroundParams): Path {
    val layout = layout ?: return Path().apply {
        addRect(
            0f,
            0f,
            measuredWidth.toFloat(),
            measuredHeight.toFloat(),
            Path.Direction.CCW,
        )
    }
    return createBackgroundPath(params, layout)
}

private fun TextView.createBackgroundPath(params: BackgroundParams, layout: Layout): Path {
    val bgShape = Path()

    repeat(layout.lineCount) { lineIndex ->
        val lineText = getTextFromLine(lineIndex)

        if (shouldDrawRect(lineText)) {
            val rect = setBackgroundRect(layout, lineIndex, params)

            bgShape.addRect(rect, Path.Direction.CCW)
        }
    }
    return bgShape
}

private fun TextView.getTextFromLine(lineIndex: Int): String {
    val start = layout.getLineStart(lineIndex)
    val end = layout.getLineEnd(lineIndex)
    return layout.text.substring(start, end)
}

private fun shouldDrawRect(lineText: String) = lineText != END_OF_LINE && lineText.isNotEmpty()

private fun TextView.setBackgroundRect(
    layout: Layout,
    lineIndex: Int,
    params: BackgroundParams
): RectF {
    val rect = RectF()

    rect.setPosition(layout, lineIndex)
    rect.offset(paddingStart.toFloat(), paddingTop.toFloat())
    rect.setPadding(params)
    return rect
}

private fun RectF.setPosition(layout: Layout, lineIndex: Int) {
    set(
        layout.getLineLeft(lineIndex),
        layout.getLineTop(lineIndex).toFloat(),
        layout.getLineRight(lineIndex),
        layout.getLineBottom(lineIndex).toFloat()
    )
}

private fun RectF.setPadding(params: BackgroundParams) {

    val bgPaddingHorizontal: Float = when {
        params.paddingHorizontal != Float.MIN_VALUE -> params.paddingHorizontal
        else -> DEFAULT_BG_PADDING_HORIZONTAL
    }

    val bgPaddingVertical: Float = when {
        params.paddingVertical != Float.MIN_VALUE -> params.paddingVertical
        else -> DEFAULT_BG_PADDING_VERTICAL
    }

    set(
        left - bgPaddingHorizontal,
        top - bgPaddingVertical,
        right + bgPaddingHorizontal,
        bottom + bgPaddingVertical,
    )
}

const val END_OF_LINE = "\n"
const val DEFAULT_BG_PADDING_VERTICAL = 0f
const val DEFAULT_BG_PADDING_HORIZONTAL = 30f