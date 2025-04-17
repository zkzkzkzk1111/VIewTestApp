package com.kmj.viewtest.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kmj.viewtest.R
import com.kmj.viewtest.model.EditableTextState

@Composable
fun TextScreen(){

    val backgroundHex = remember { mutableStateOf("#FFFFFF") }

    val backgroundColor = try {
        Color(android.graphics.Color.parseColor(backgroundHex.value))
    } catch (e: Exception) {
        Color.White
    }

    Column(
        Modifier.background(color = backgroundColor).fillMaxSize()
    ){
        LazyColumn(
        ) {
            item{
                EditableTextStyleScreen(backgroundHex = backgroundHex)
            }
        }
    }
}

@Composable
fun EditableTextStyleScreen(backgroundHex: MutableState<String>) {
    var bottomsheet by remember { mutableStateOf(false) }

    val textStates = remember {
        mutableStateListOf(
            EditableTextState("Text 1", mutableStateOf("18"), mutableStateOf("600"), mutableStateOf("0"),mutableStateOf("0"),mutableStateOf("ff000000")),
            EditableTextState("Text 2", mutableStateOf("18"), mutableStateOf("400"), mutableStateOf("0"),mutableStateOf("0"),mutableStateOf("ff000000")),
        )
    }
    Column(modifier = Modifier.padding(20.dp)) {
        Spacer(modifier = Modifier.height(40.dp))

        textStates.forEach { state ->

            val fontSize = state.fontSize.value.toIntOrNull() ?: 18
            val paddingStart = state.paddingStart.value.toIntOrNull() ?: 0
            val paddingBottom = state.paddingBottom.value.toIntOrNull() ?: 0
            val color = try {
                Color(android.graphics.Color.parseColor("#${state.fontColor.value}"))
            } catch (e: Exception) {
                Color.Black
            }

            Text(
                text = state.label,
                modifier = Modifier
                    .padding(
                        start = paddingStart.dp,
                        bottom = paddingBottom.dp,
                    ).clickable {
                        bottomsheet=true
                        Log.d("showDialog","true")
                    }
                ,
                style = TextStyle(
                    fontSize = fontSize.sp,
                    fontFamily = getFontFamily(state.fontWeight.value),
                    color = color,
                    letterSpacing = 0.36.sp
                )
            )
        }
    }
    if (bottomsheet) {
        bottomsheet(
            textStates = textStates,
            backgroundstate = backgroundHex,
            closeSheet = { bottomsheet = false }
        )
    }
}


@Composable
fun getFontFamily(weight: String): FontFamily {
    return when (weight) {
        "400" -> FontFamily(Font(R.font.pretendard_400))
        "600" -> FontFamily(Font(R.font.pretendard_600))
        "700" -> FontFamily(Font(R.font.pretendard_700))
        else -> FontFamily.Default
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun bottomsheet(
    textStates: List<EditableTextState>,
    backgroundstate: MutableState<String>,
    closeSheet: () -> Unit
){
    val sheetState = rememberModalBottomSheetState()

   ModalBottomSheet(
       onDismissRequest = closeSheet,
       sheetState = sheetState,
       shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
       containerColor = Color(0xffffffff),
       dragHandle = null,
       scrimColor = Color.Transparent

   ) {
       Column(
           Modifier.padding(top = 24.dp,start=10.dp).imePadding()
       ) {
           Text(text="Background Color")
           OutlinedTextField(
               value = backgroundstate.value,
               onValueChange = { backgroundstate.value = it },
               modifier = Modifier

                   .width(160.dp),
               label = { Text("Background") },
               placeholder = { Text("#FFFFFF") },
               singleLine = true
           )

           Spacer(Modifier.height(10.dp))

           textStates.forEachIndexed { index, state ->
           Text(
               text = "Text ${index + 1}",
               fontWeight = FontWeight.Bold
           )

           Spacer(modifier = Modifier.height(4.dp))

           LazyRow(
               horizontalArrangement = Arrangement.spacedBy(10.dp),
               modifier = Modifier.padding(end=10.dp)
           ){
               item {
                   OutlinedTextField(
                       value = state.fontSize.value,
                       onValueChange = { state.fontSize.value = it },
                       modifier = Modifier.width(100.dp),
                       keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                       singleLine = true,
                       label = { Text("Size") },
                       placeholder = { Text(text = "18") }
                   )
               }
               item {
                   OutlinedTextField(
                       value = state.fontColor.value,
                       onValueChange = { state.fontColor.value = it },
                       modifier = Modifier.width(120.dp),
                       singleLine = true,
                       label = { Text("Color") },
                       placeholder = { Text(text = "FF000000") }
                   )
               }
               item {
                   OutlinedTextField(
                       value = state.fontWeight.value,
                       onValueChange = { state.fontWeight.value = it },
                       modifier = Modifier.width(100.dp),
                       keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                       singleLine = true,
                       label = { Text("Font") },
                       placeholder = { Text(text = "600") }
                   )
               }
               item {
                   OutlinedTextField(
                       value = state.paddingStart.value,
                       onValueChange = { state.paddingStart.value = it },
                       modifier = Modifier.width(100.dp),
                       keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                       singleLine = true,
                       label = { Text("left") },
                       placeholder = { Text(text = "0") }
                   )
               }
               item {
                   OutlinedTextField(
                       value = state.paddingBottom.value,
                       onValueChange = { state.paddingBottom.value = it },
                       modifier = Modifier.width(100.dp),
                       keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                       singleLine = true,
                       label = { Text("bottom") },
                       placeholder = { Text(text = "0") }
                   )
               }
           }
           Spacer(modifier = Modifier.height(16.dp))
        }
      }
   }
}

