package com.exyte.shapedbackgroundcompose.core

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import java.util.Collections

@Stable
fun Float.toDp(density: Density): Dp = with(density) { this@toDp.toDp() }

@Stable
fun Dp.toPxf(density: Density): Float = with(density) { this@toPxf.toPx() }

@Immutable
class ListWrapper<T>(private val innerList: List<T>) : List<T> by innerList {
    companion object {
        private val empty = ListWrapper<Any>(Collections.emptyList())

        @Suppress("UNCHECKED_CAST")
        fun <E> empty() = empty as ListWrapper<E>
    }
}

fun <E> List<E>.wrap() = if (isEmpty()) ListWrapper.empty() else ListWrapper(this)