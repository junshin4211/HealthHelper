package com.example.healthhelper.dietary.components.combo

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.healthhelper.attr.viewmodel.DefaultColorViewModel

@Composable
fun SaveGraphAndTextRecordButton(
    outerButtonModifier: Modifier = Modifier,
    saveGraph:()->Unit,
    leftTextButtonTextFontSize:TextUnit,
    leftTextButtonText:String,
    saveTextRecord:()->Unit,
    rightTextButtonTextFontSize:TextUnit,
    rightTextButtonText: String,
){
    MultipleTextButton(
        outerButtonModifier = outerButtonModifier.padding(0.dp),
        outerButtonColor = DefaultColorViewModel.buttonColors,
        leftTextButtonColors = DefaultColorViewModel.buttonColors,
        leftTextButtonOnClick = saveGraph,
        leftTextButtonTextFontSize = leftTextButtonTextFontSize,
        leftTextButtonText = leftTextButtonText,
        rightTextButtonColors = DefaultColorViewModel.buttonColors,
        rightTextButtonOnClick = saveTextRecord,
        rightTextButtonTextFontSize = rightTextButtonTextFontSize,
        rightTextButtonText = rightTextButtonText,
    )
}