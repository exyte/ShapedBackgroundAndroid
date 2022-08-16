package com.exyte.example

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exyte.shapedbackgroundcompose.RoundedBackgroundText
import com.exyte.shapedbackgroundcompose.core.BackgroundParams

@Composable
fun Title(
    modifier: Modifier,
    isComposeView: MutableState<Boolean>,
    alignment: MutableState<Int>,
) {
    Column(
        modifier = modifier
    ) {
        RoundedBackgroundText(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            value = if (isComposeView.value) {
                COMPOSE
            } else {
                ANDROID_VIEW
            },
            readOnly = true,
            backgroundParams = BackgroundParams(
                backgroundColor = Zeus
            ),
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                color = Rajah,
                fontSize = 30.sp,
            )
        )
        DefaultTextButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = CHANGE_VIEW_TEXT,
            onClick = {
                isComposeView.value = !isComposeView.value
            }
        )
        DefaultTextButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = composeAlign[alignment.value].toString(),
            onClick = {
                if (alignment.value == 2) {
                    alignment.value = 0
                } else {
                    alignment.value++
                }
            },
        )
    }
}

@Composable
fun DefaultTextButton(
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(backgroundColor = Zeus),
    onClick: () -> Unit = {},
    text: String = ""
) {
    Button(
        modifier = modifier.padding(top = 16.dp),
        colors = colors,
        onClick = onClick,
    ) {
        Text(
            text = text,
            color = Rajah
        )
    }
}

const val ANDROID_VIEW = "Android View"
const val COMPOSE = "Compose"
const val CHANGE_VIEW_TEXT = "change view"