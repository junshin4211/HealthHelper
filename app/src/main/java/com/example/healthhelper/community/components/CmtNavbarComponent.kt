package com.example.healthhelper.community.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.healthhelper.R
import com.example.healthhelper.community.CmtScreenEnum

@Composable
fun CmtNavbarComponent(navController: NavHostController) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        IconButton(
            onClick = {
                navController.navigate(CmtScreenEnum.CmtMainScreen.name)
            },
            modifier = Modifier,

            ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_back_ios_new),
                contentDescription = "arrowBack",
                tint = colorResource(id = R.color.primarycolor)
            )
        }
        Text(
            text = "飲食社群",
            fontSize = 24.sp,
            color = colorResource(id = R.color.primarycolor),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight(600),
        )
        IconButton(
            onClick = {
                navController.navigate(CmtScreenEnum.MyPostsScreen.name)
            },
            modifier = Modifier,

            ) {
            Icon(
                painter = painterResource(id = R.drawable.tabler_receipt),
                contentDescription = "arrowBack",
                tint = colorResource(id = R.color.primarycolor)
            )
        }
    }
    HorizontalDivider(
        thickness = 2.dp,
        modifier = Modifier
            .fillMaxWidth(),
        color = colorResource(id = R.color.primarycolor)
    )
}
