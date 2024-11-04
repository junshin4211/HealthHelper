package com.example.healthhelper.dietary.components.combo

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthhelper.R
import com.example.healthhelper.attr.viewmodel.DefaultColorViewModel

@Composable
fun SaveGraphAndTextRecordButton(
    outerButtonModifier: Modifier = Modifier,
    saveGraph:()->Unit,
    saveTextRecord:()->Unit,
){
    MultipleTextButton(
        outerButtonModifier = outerButtonModifier.padding(0.dp),
        outerButtonColor = DefaultColorViewModel.buttonColors,
        leftTextButtonColors = DefaultColorViewModel.buttonColors,
        leftTextButtonOnClick = saveGraph,
        leftTextButtonTextFontSize = 10.sp,
        leftTextButtonText = stringResource(R.string.save_graph),
        rightTextButtonColors = DefaultColorViewModel.buttonColors,
        rightTextButtonOnClick = saveTextRecord,
        rightTextButtonText = stringResource(R.string.save_text_record),
        rightTextButtonTextFontSize = 10.sp,
    )
}