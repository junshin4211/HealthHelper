package com.example.healthhelper.plan.ui

import androidx.annotation.ColorRes
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthhelper.R

class CustomButton {
    @Composable
    fun CreateButton(
        text:String,
        @ColorRes color:Int,
        width:Int = 120,
        onClick:()->Unit
        ){
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = color)),
            shape = RoundedCornerShape(15.dp),
            onClick = { onClick() },
            modifier = Modifier
                .width(width.dp)
        ){
            CustomText().TextWithDiffColor(setcolor = R.color.black, text = text, setsize = 20.sp)
        }

    }

    @Composable
    fun CreateButton(
        text:String,
        @ColorRes color:Int,
        width:Int = 120,
        onClick:()->Unit,
        leadIcon:@Composable ()->Unit,
        trailIcon:@Composable ()->Unit,
    ){
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = color)),
            shape = RoundedCornerShape(15.dp),
            onClick = { onClick() },
            modifier = Modifier
                .width(width.dp)
        ){
            leadIcon()
            CustomText().TextWithDiffColor(setcolor = R.color.black, text = text, setsize = 20.sp)
            trailIcon()
        }
    }
}