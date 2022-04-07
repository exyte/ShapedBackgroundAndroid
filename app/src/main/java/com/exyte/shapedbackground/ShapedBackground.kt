package com.example.likeinstabackgroundtext

import android.graphics.*
import android.os.Build
import android.text.Editable
import android.text.Layout
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.exyte.shapedbackground.ShapeBackgroundDrawable


fun TextView.roundedBackground(init: BackgroundParams.() -> Unit) {
    val params = BackgroundParams().apply(init)
    roundedBackground(params)
}

fun EditText.roundedBackground(init: BackgroundParams.() -> Unit) {
    val params = BackgroundParams().apply(init)
    roundedBackground(params)
}

fun TextView.roundedBackground(params: BackgroundParams) {
    drawOnLayout(this, params)
    drawAfterTextChanged(this, params)
}

fun EditText.roundedBackground(params: BackgroundParams) {
    drawOnLayout(this, params)
    drawAfterTextChanged(this, params)
}

private fun drawOnLayout(view: TextView, params: BackgroundParams) {
    view.apply {
        if (isLaidOut(this) && !isLayoutRequested) {
            drawBackground(this, params)
        } else {
            addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
                override fun onLayoutChange(
                    view: View,
                    left: Int,
                    top: Int,
                    right: Int,
                    bottom: Int,
                    oldLeft: Int,
                    oldTop: Int,
                    oldRight: Int,
                    oldBottom: Int
                ) {
                    view.removeOnLayoutChangeListener(this)
                    drawBackground(this@apply, params)
                }
            })
        }
    }
}

private fun isLaidOut(view: View): Boolean {
    return if (Build.VERSION.SDK_INT >= 19) {
        view.isLaidOut
    } else view.width > 0 && view.height > 0
}

private fun drawAfterTextChanged(textView: TextView, params: BackgroundParams) {
    textView.apply {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                drawBackground(this@apply, params)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {}
        }
        addTextChangedListener(textWatcher)
    }
}

private fun drawBackground(textView: TextView, params: BackgroundParams) {
    textView.apply {
        val shape = buildBackground(this, params)
        background = ShapeBackgroundDrawable(shape, params, context, textSize)
    }
}

private fun buildBackground(textView: TextView, params: BackgroundParams): Path {
    textView.apply {
        val layout = layout ?: return Path().apply {
            addRect(
                0f,
                0f,
                measuredWidth.toFloat(),
                measuredHeight.toFloat(),
                Path.Direction.CCW,
            )
        }
        return createBackgroundPath(this, params, layout)
    }
}

private fun createBackgroundPath(
    textView: TextView,
    params: BackgroundParams,
    layout: Layout
): Path {
    val bgShape = Path()

    repeat(layout.lineCount) { lineIndex ->
        if (shouldDrawRect(textView, lineIndex)) {
            val rect = setBackgroundRect(textView, layout, lineIndex, params)
            bgShape.addRect(rect, Path.Direction.CCW)
        }
    }
    return bgShape
}

private fun shouldDrawRect(textView: TextView, lineIndex: Int): Boolean {
    val layout = textView.layout
    val start = layout.getLineStart(lineIndex)
    val end = layout.getLineEnd(lineIndex)
    return !(isEndOfLine(layout.text, start, end) || isLineEmpty(start, end))
}

private fun isEndOfLine(text: CharSequence, start: Int, end: Int) =
    end - start == 1 && text.substring(start, end) == END_OF_LINE

private fun isLineEmpty(start: Int, end: Int) = start == end

private fun setBackgroundRect(
    textView: TextView,
    layout: Layout,
    lineIndex: Int,
    params: BackgroundParams
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

const val END_OF_LINE = "\n"
const val DEFAULT_BG_PADDING_VERTICAL = 0f
const val DEFAULT_BG_PADDING_HORIZONTAL = 30f