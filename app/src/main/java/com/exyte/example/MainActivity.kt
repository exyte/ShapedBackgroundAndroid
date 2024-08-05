package com.exyte.example

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.likeinstabackgroundtext.R
import com.exyte.shapedbackground.roundedBackground
import com.exyte.shapedbackgroundcompose.RoundedBackgroundText
import com.exyte.shapedbackgroundcompose.core.BackgroundParams
import com.exyte.shapedbackgroundcompose.core.toPxf

typealias ShadowParamsCompose = com.exyte.shapedbackgroundcompose.core.ShadowParams
typealias ShadowParamsView = com.exyte.shapedbackground.ShadowParams

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                Modifier.fillMaxSize()
            ) {
                val isComposeView = remember { mutableStateOf(true) }
                val alignment = remember { mutableIntStateOf(0) }

                Title(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    isComposeView = isComposeView,
                    alignment = alignment
                )
                Spacer(modifier = Modifier.weight(1f))

                if (isComposeView.value) {
                    AndroidComposeShapedBackground(align = alignment)
                } else {
                    AndroidViewShapedBackground(align = alignment)
                }

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }

    @Composable
    private fun AndroidComposeShapedBackground(
        modifier: Modifier = Modifier,
        align: State<Int>,
    ) {
        var text by remember { mutableStateOf(getString(R.string.lorem_ipsum)) }
        val textAlign = composeAlign[align.value]

        RoundedBackgroundText(
            value = text,
            onValueChange = { text = it },
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            backgroundParams = BackgroundParams(
                cornerRadius = 60f,
                backgroundColor = Zeus,
                shadow = ShadowParamsCompose(
                    dx = 5.dp,
                    dy = 5.dp,
                    radius = 10.dp,
                    color = Color.Black,
                )
            ),
            textStyle = TextStyle(
                fontSize = 30.sp,
                color = Rajah,
                textAlign = textAlign,
            ),
        )
    }

    @Composable
    private fun AndroidViewShapedBackground(
        modifier: Modifier = Modifier,
        align: State<Int>,
    ) {
        val paddingInPx = 16.dp.toPx()
        val textAlign = viewAlign[align.value]
        val cornerSize = 15.dp.toPxf()
        val density = LocalDensity.current

        AndroidView(
            modifier = modifier
                .fillMaxWidth(),
            factory = {
                EditText(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    setPadding(paddingInPx, paddingInPx, paddingInPx, paddingInPx)
                    textSize = 30f
                    setTextColor(Rajah.toArgb())
                    setRoundedBackground(
                        editText = this,
                        textAlign = textAlign,
                        cornerSize = cornerSize,
                        density = density
                    )
                }
            },
            update = {
                setRoundedBackground(
                    editText = it,
                    textAlign = textAlign,
                    cornerSize = cornerSize,
                    density = density
                )
            }
        )
    }

    private fun setRoundedBackground(
        editText: EditText,
        textAlign: Int,
        cornerSize: Float,
        density: Density,
    ) {
        editText.textAlignment = textAlign
        editText.text = SpannableStringBuilder(getString(R.string.lorem_ipsum))
        editText.roundedBackground {
            cornerRadius = cornerSize
            backgroundColor = Zeus.toArgb()
            shadow = ShadowParamsView(
                dx = 5.dp.toPxf(density),
                dy = 5.dp.toPxf(density),
                radius = 10.dp.toPxf(density),
            )
        }
    }
}