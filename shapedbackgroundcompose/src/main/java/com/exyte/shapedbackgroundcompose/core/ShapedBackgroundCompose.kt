package com.exyte.shapedbackgroundcompose.core

import android.graphics.Path
import android.graphics.RectF
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

fun createBackgroundPath(
    textBackgroundResult: TextLayoutResult,
    params: BackgroundParams,
    density: Density,
): Path {
    val bgShape = Path()

    repeat(textBackgroundResult.lineCount) { lineIndex ->
        if (shouldDrawRect(textBackgroundResult, lineIndex)) {
            val rect = createBackgroundRect(textBackgroundResult, lineIndex, params, density)
            bgShape.addRect(rect, Path.Direction.CCW)
        }
    }
    return bgShape
}

fun shouldDrawRect(textLayoutResult: TextLayoutResult, lineIndex: Int): Boolean {
    val start = textLayoutResult.getLineStart(lineIndex)
    val end = textLayoutResult.getLineEnd(lineIndex)
    return !(isEndOfLine(textLayoutResult.layoutInput.text, start, end) || isLineEmpty(start, end))
}

fun isEndOfLine(text: CharSequence, start: Int, end: Int) =
    end - start == 1 && text[start] == END_OF_LINE

fun isLineEmpty(start: Int, end: Int) = start == end

fun createBackgroundRect(
    layout: TextLayoutResult,
    lineIndex: Int,
    params: BackgroundParams,
    density: Density,
): RectF {
    val bgPaddingHorizontal: Float = when {
        params.paddingHorizontal != Dp.Unspecified -> params.paddingHorizontal.toPxf(density)
        else -> DEFAULT_BG_PADDING_HORIZONTAL
    }

    val bgPaddingVertical: Float = when {
        params.paddingVertical != Dp.Unspecified -> params.paddingVertical.toPxf(density)
        else -> DEFAULT_BG_PADDING_VERTICAL
    }
    return RectF(
        (layout.getLineLeft(lineIndex) - bgPaddingHorizontal),
        (layout.getLineTop(lineIndex) - bgPaddingVertical),
        (layout.getLineRight(lineIndex) + bgPaddingHorizontal),
        (layout.getLineBottom(lineIndex) + bgPaddingVertical)
    )
}

const val END_OF_LINE = '\n'
const val DEFAULT_BG_PADDING_VERTICAL = 0f
const val DEFAULT_BG_PADDING_HORIZONTAL = 30f