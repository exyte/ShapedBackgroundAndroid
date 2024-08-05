package com.exyte.shapedbackground

import android.graphics.*
import android.os.Build
import android.text.Editable
import android.text.Layout
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.EditText
import android.widget.TextView


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

private fun isLaidOut(view: View): Boolean = if (Build.VERSION.SDK_INT >= 19) {
    view.isLaidOut
} else view.width > 0 && view.height > 0

private fun drawAfterTextChanged(textView: TextView, params: BackgroundParams) {
    textView.apply {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (isWrapContentLayoutParams(textView)) {
                    drawAfterTextMeasured(textView, params)
                } else {
                    drawBackground(textView, params)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {}
        }
        addTextChangedListener(textWatcher)
    }
}

private fun isWrapContentLayoutParams(textView: TextView): Boolean =
    textView.layoutParams.width == ViewGroup.LayoutParams.WRAP_CONTENT

private fun drawAfterTextMeasured(textView: TextView, params: BackgroundParams) {
    textView.viewTreeObserver
        .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                textView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                drawBackground(textView, params)
            }
        })
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

const val END_OF_LINE = '\n'
const val DEFAULT_BG_PADDING_VERTICAL = 0f
const val DEFAULT_BG_PADDING_HORIZONTAL = 30f