package com.example.healthhelper.dietary.components.combo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.healthhelper.R
import com.example.healthhelper.attr.viewmodel.DefaultColorViewModel

@Composable
fun SaveGraphAndTextRecordButton(
    outerButtonModifier: Modifier = Modifier,
    saveGraph:()->Unit,
    saveTextRecord:()->Unit,
){
    MultipleTextButton(
        outerButtonModifier = outerButtonModifier,
        outerButtonColor = DefaultColorViewModel.buttonColors,
        leftTextButtonColors = DefaultColorViewModel.buttonColors,
        leftTextButtonOnClick = saveGraph,
        leftTextButtonText = stringResource(R.string.save_graph),
        rightTextButtonColors = DefaultColorViewModel.buttonColors,
        rightTextButtonOnClick = saveTextRecord,
        rightTextButtonText = stringResource(R.string.save_text_record)
    )
}