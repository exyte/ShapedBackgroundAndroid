package com.exyte.shapedbackground

import android.graphics.Path
import android.graphics.RectF
import android.os.Build
import android.text.Layout
import android.widget.TextView
import androidx.collection.mutableIntObjectMapOf

/*
 * Created by Exyte on 18.03.2024.
 */

private fun createBackgroundPathApi34(
    textView: TextView,
    params: BackgroundParams,
    layout: Layout,
): Path {
    val bgShape = Path()
    var index = 0
    val map = mutableIntObjectMapOf<MutableList<RectF>>()

    repeat(layout.lineCount) { lineIndex ->
        if (shouldDrawRect(layout, lineIndex)) {
            val r = setBackgroundRect(
                layout = layout,
                lineIndex = lineIndex,
                textView = textView,
                params = params,
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
    textView: TextView,
    params: BackgroundParams,
    layout: Layout,
): Path {
    val bgShape = Path()

    repeat(layout.lineCount) { lineIndex ->
        if (shouldDrawRect(layout, lineIndex)) {
            val rect = setBackgroundRect(textView, layout, lineIndex, params)
            bgShape.addRect(rect, Path.Direction.CCW)
        }
    }
    return bgShape
}

internal fun createBackgroundPath(
    textView: TextView,
    params: BackgroundParams,
    layout: Layout,
): Path {
    return if (Build.VERSION.SDK_INT >= 34) {
        createBackgroundPathApi34(textView, params, layout)
    } else {
        createBackgroundPathApiCommon(textView, params, layout)
    }
}

private fun shouldDrawRect(layout: Layout, lineIndex: Int): Boolean {
    val start = layout.getLineStart(lineIndex)
    val end = layout.getLineEnd(lineIndex)
    return !(isEndOfLine(layout.text, start, end) || isLineEmpty(start, end))
}

private fun isEndOfLine(text: CharSequence, start: Int, end: Int) =
    end - start == 1 && text[start] == END_OF_LINE

private fun isLineEmpty(start: Int, end: Int) = start == end

private fun setBackgroundRect(
    textView: TextView,
    layout: Layout,
    lineIndex: Int,
    params: BackgroundParams,
): RectF {
    val rect = RectF()

    setPosition(rect, layout, lineIndex)
    rect.offset(textView.paddingStart.toFloat(), textView.paddingTop.toFloat())
    setPadding(rect, params)
    return rect
}

private fun setPosition(rect: RectF, layout: Layout, lineIndex: Int) {
    rect.set(
        layout.getLineLeft(lineIndex),
        layout.getLineTop(lineIndex).toFloat(),
        layout.getLineRight(lineIndex),
        layout.getLineBottom(lineIndex).toFloat()
    )
}

private fun setPadding(rect: RectF, params: BackgroundParams) {

    val bgPaddingHorizontal: Float = when {
        params.paddingHorizontal != Float.MIN_VALUE -> params.paddingHorizontal
        else -> DEFAULT_BG_PADDING_HORIZONTAL
    }

    val bgPaddingVertical: Float = when {
        params.paddingVertical != Float.MIN_VALUE -> params.paddingVertical
        else -> DEFAULT_BG_PADDING_VERTICAL
    }

    rect.apply {
        set(
            left - bgPaddingHorizontal,
            top - bgPaddingVertical,
            right + bgPaddingHorizontal,
            bottom + bgPaddingVertical,
        )
    }
}