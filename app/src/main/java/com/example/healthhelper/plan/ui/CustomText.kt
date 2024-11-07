package com.example.healthhelper.plan.ui

import androidx.annotation.ColorRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.healthhelper.R

class CustomText {

    @Composable
    fun TextWithDiffColor(
        @ColorRes setcolor:Int = R.color.black_300,
        text:String = "",
        setsize:TextUnit = 12.sp,
    ){
        Text(
            text = text,
            style = TextStyle(
                fontSize = setsize,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight(600),
                color = colorResource(id = setcolor),
                textAlign = TextAlign.Start,
                letterSpacing = 0.2.sp
            ),
        )
    }

    @Composable
    fun TextWithDiffColor(
        @ColorRes setcolor:Int = R.color.black_300,
        text:String = "",
        setsize:TextUnit = 12.sp,
        maxline:Int = 1
    ){
        Text(
            text = text,
            style = TextStyle(
                fontSize = setsize,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight(600),
                color = colorResource(id = setcolor),
                textAlign = TextAlign.Start,
                letterSpacing = 0.2.sp
            ),
            maxLines = maxline
        )
    }

}

@Preview(showBackground = true)
@Composable
fun TextPreview(){
    CustomText().TextWithDiffColor(R.color.primarycolor,"測試")
}