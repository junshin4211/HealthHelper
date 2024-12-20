package com.example.healthhelper.person.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthhelper.R

@Composable
fun CustomTabRow(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    textColor: Color,
    selectedTextColor: Color,
    labels: List<String>,
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Button(
            onClick = { onTabSelected(0) },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedTab == 0) colorResource(R.color.primarycolor) else Color.Transparent
            ),
            modifier = Modifier.padding(vertical = 6.dp).size(90.dp, 40.dp),
            shape = RoundedCornerShape(15.dp),
            border = BorderStroke(1.dp, colorResource(R.color.primarycolor)),
            contentPadding = PaddingValues(6.dp)
        ) {
            Text(
                labels[0],
                color = if (selectedTab == 0) selectedTextColor else textColor,
                fontSize = 14.sp
            )
        }

        Button(
            onClick = { onTabSelected(1) },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedTab == 1) colorResource(R.color.primarycolor) else Color.Transparent
            ),
            modifier = Modifier.padding(vertical = 6.dp).size(90.dp, 40.dp),
            shape = RoundedCornerShape(15.dp),
            border = BorderStroke(1.dp, colorResource(R.color.primarycolor)),
            contentPadding = PaddingValues(6.dp)
        ) {

            Text(
                labels[1],
                color = if (selectedTab == 1) selectedTextColor else textColor,
                fontSize = 16.sp
            )
        }

        Button(
            onClick = { onTabSelected(2) },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedTab == 2) colorResource(R.color.primarycolor) else Color.Transparent
            ),
            modifier = Modifier.padding(vertical = 6.dp).size(90.dp, 40.dp),
            shape = RoundedCornerShape(15.dp),
            border = BorderStroke(1.dp, colorResource(R.color.primarycolor)),
            contentPadding = PaddingValues(6.dp)
        ) {


            Text(
                labels[2],
                color = if (selectedTab == 2) selectedTextColor else textColor,
                fontSize = 14.sp
            )
        }
    }
}
