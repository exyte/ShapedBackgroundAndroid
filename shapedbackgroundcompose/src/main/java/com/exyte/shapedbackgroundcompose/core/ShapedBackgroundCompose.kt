package com.exyte.shapedbackgroundcompose.core

import android.graphics.Path
import android.graphics.RectF
import android.os.Build
import androidx.collection.mutableIntObjectMapOf
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

//workaround https://issuetracker.google.com/issues/318612129
private fun createBackgroundPathApi34(
    textBackgroundResult: TextLayoutResult,
    paddingHorizontal: Dp,
    paddingVertical: Dp,
    density: Density,
): Path {
    val bgShape = Path()
    var index = 0
    val map = mutableIntObjectMapOf<MutableList<RectF>>()

    repeat(textBackgroundResult.lineCount) { lineIndex ->
        if (shouldDrawRect(textBackgroundResult, lineIndex)) {
            val r = createBackgroundRect(
                layout = textBackgroundResult,
                lineIndex = lineIndex,
                paddingHorizontal = paddingHorizontal,
                paddingVertical = paddingVertical,
                density = density,
            )
            var list = map[index]
            if (list == null) {
                list = mutableListOf(r)
                map[index] = list
            } else {
                list.add(r)
            }
        } else {
            index++
        }
    }

    if (map.isEmpty()) return bgShape


    map.forEachValue { rects ->
        if (rects.size == 1) {
            bgShape.addRect(rects.first(), Path.Direction.CCW)
        } else {
            rects.forEachIndexed { lineIndex, r ->
                bgShape.apply {
                    val nr = rects.getOrNull(lineIndex + 1) ?: r //next line rect
                    if (lineIndex == 0) {
                        moveTo(r.left, r.top)
                        lineTo(r.right, r.top)
                        val bottom = when {
                            nr.right > r.right -> nr.top    //next line is wider than current line
                            nr.right < r.right -> r.bottom  //current line is wider than next line
                            else -> r.bottom
                        }
                        lineTo(r.right, bottom)
                    } else {
                        val pr = rects.getOrNull(lineIndex - 1) ?: r //previous line rect
                        val top = when {
                            r.right > pr.right -> r.top
                            r.right < pr.right -> pr.bottom
                            else -> pr.bottom
                        }
                        val bottom = when {
                            r.right > nr.right -> r.bottom
                            r.right < nr.right -> nr.top
                            else -> r.bottom
                        }
                        lineTo(r.right, top)
                        lineTo(r.right, bottom)
                    }
                }
            }
            bgShape.apply {
                for (lineIndex in rects.lastIndex downTo 0) {
                    val r = rects[lineIndex]
                    val pr = rects.getOrNull(lineIndex - 1) ?: r
                    val nr = rects.getOrNull(lineIndex + 1) ?: r
                    val top = when {
                        r.left > pr.left -> pr.bottom
                        r.left < pr.left -> r.top
                        else -> r.top
                    }
                    val bottom = when {
                        r.left < nr.left -> r.bottom
                        r.left > nr.left -> nr.top
                        else -> r.bottom
                    }
                    lineTo(r.left, bottom)
                    lineTo(r.left, top)
                }
            }
        }
        bgShape.close()
    }

    return bgShape
}

private fun createBackgroundPathApiCommon(
    textBackgroundResult: TextLayoutResult,
    paddingHorizontal: Dp,
    paddingVertical: Dp,
    density: Density,
): Path {
    val bgShape = Path()
    repeat(textBackgroundResult.lineCount) { lineIndex ->
        if (shouldDrawRect(textBackgroundResult, lineIndex)) {
            val rect = createBackgroundRect(
                layout = textBackgroundResult,
                lineIndex = lineIndex,
                paddingHorizontal = paddingHorizontal,
                paddingVertical = paddingVertical,
                density = density,
            )
            bgShape.addRect(rect, Path.Direction.CCW)
        }
    }
    return bgShape
}

fun createBackgroundPath(
    textBackgroundResult: TextLayoutResult,
    paddingHorizontal: Dp,
    paddingVertical: Dp,
    density: Density,
): Path {
    if (Build.VERSION.SDK_INT >= 34) {
        return createBackgroundPathApi34(
            textBackgroundResult,
            paddingHorizontal = paddingHorizontal,
            paddingVertical = paddingVertical,
            density = density,
        )
    }
    return createBackgroundPathApiCommon(
        textBackgroundResult,
        paddingHorizontal = paddingHorizontal,
        paddingVertical = paddingVertical,
        density = density,
    )
}

private fun shouldDrawRect(textLayoutResult: TextLayoutResult, lineIndex: Int): Boolean {
    val start = textLayoutResult.getLineStart(lineIndex)
    val end = textLayoutResult.getLineEnd(lineIndex)
    return !(isEndOfLine(textLayoutResult.layoutInput.text, start, end))
}

private fun isEndOfLine(text: CharSequence, start: Int, end: Int) =
    end - start == 1 && text[start] == END_OF_LINE

private fun createBackgroundRect(
    layout: TextLayoutResult,
    lineIndex: Int,
    paddingHorizontal: Dp,
    paddingVertical: Dp,
    density: Density,
): RectF {
    val bgPaddingHorizontal: Float = when {
        paddingHorizontal != Dp.Unspecified -> paddingHorizontal.toPxf(density)
        else -> DEFAULT_BG_PADDING_HORIZONTAL
    }

    val bgPaddingVertical: Float = when {
        paddingVertical != Dp.Unspecified -> paddingVertical.toPxf(density)
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