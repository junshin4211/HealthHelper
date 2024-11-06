package com.example.healthhelper.dietary.components.button

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.healthhelper.R
import com.example.healthhelper.attr.color.defaultcolor.DefaultColorViewModel

@Composable
fun DownloadButton(
    context:Context,
    onClick: () -> Unit,
    iconButtonColors : IconButtonColors = DefaultColorViewModel.iconButtonColors,
){
    IconButton(
        colors = DefaultColorViewModel.iconButtonColors,
        onClick = onClick
    ) {
        Image(
            painter = painterResource(R.drawable.download),
            contentDescription = stringResource(R.string.download_icon),
        )
    }
}