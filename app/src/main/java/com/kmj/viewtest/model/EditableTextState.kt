package com.kmj.viewtest.model

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color

data class EditableTextState(
    val label: String,
    val fontSize: MutableState<String>,
    val fontWeight: MutableState<String>,
    val paddingStart: MutableState<String>,
    val paddingBottom: MutableState<String>,
    val fontColor: MutableState<String>
)