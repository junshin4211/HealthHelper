package com.example.healthhelper.plan

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.*
import com.example.healthhelper.R
import com.example.healthhelper.ui.theme.HealthHelperTheme
import java.nio.file.WatchEvent

@Composable
fun PlanMain(context: Context = LocalContext.current,
             navcontroller: NavHostController = rememberNavController()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp, bottom = 10.dp, start = 5.dp, end = 5.dp)
            .background(color = colorResource(id = R.color.white))
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .padding(top = 20.dp, start = 10.dp, bottom = 10.dp)
        ) {
            Text(
                text = PlanPage.DietPlan.getPlanTitle(context),
                style = TextStyle(
                    fontSize = 40.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(600),
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.2.sp
                )
            )
        }

        HorizontalDivider(
            color = colorResource(id = R.color.darkgray),
            thickness = 2.dp
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .padding(top = 5.dp, end = 5.dp)
                .fillMaxWidth()
        ) {
            CreateBar(context,navcontroller)
        }


        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 15.dp, start = 10.dp, end = 5.dp)
                .fillMaxWidth()
        ) {

            Text(
                text = PlanPage.MyPlan.getPlanTitle(context),
                style = TextStyle(
                    fontSize = 32.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.2.sp
                )
            )


            Image(
                painter = painterResource(R.drawable.arrow),
                contentDescription = "arrow",
                modifier = Modifier
                    .scale(2.5f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, bottom = 15.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.myplanimg),
                contentDescription = "image description",
                modifier = Modifier
                    .width(350.dp)
                    .height(188.dp)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.FillBounds
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    //放SQL最新計畫的名稱
                    text = "${Bar.HighProtein.getbar(context)}飲食",

                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(600),
                        color = colorResource(R.color.black),
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(start = 8.dp)
                )

                Text(
                    //放SQL最新計畫的時間
                    text = "2024/10/18~2024/10/31",

                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(600),
                        color = colorResource(R.color.black),
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
            }

        }

        HorizontalDivider(
            color = colorResource(id = R.color.darkgray),
            thickness = 2.dp,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 15.dp, start = 10.dp, end = 5.dp)
                .fillMaxWidth()
        ) {

            Text(
                text = PlanPage.CompletedPlan.getPlanTitle(context),
                style = TextStyle(
                    fontSize = 32.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.2.sp
                )
            )


            Image(
                painter = painterResource(R.drawable.arrow),
                contentDescription = "arrow",
                modifier = Modifier
                    .scale(2.5f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, bottom = 15.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.myplanimg),
                contentDescription = "image description",
                modifier = Modifier
                    .width(350.dp)
                    .height(188.dp)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.FillBounds
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    //放SQL最近已完成計畫的名稱
                    text = "${Bar.HighProtein.getbar(context)}飲食",

                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(600),
                        color = colorResource(R.color.black),
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(start = 8.dp)
                )

                Text(
                    //放SQL最近已完成計畫的時間
                    text = "2024/10/18~2024/10/31",

                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(600),
                        color = colorResource(R.color.black),
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
            }

        }
    }

}


@Composable
fun CreateBar(context: Context,navcontroller: NavHostController) {
    var tabindex by remember { mutableIntStateOf(0) }
    TabRow(
        selectedTabIndex = tabindex,
        containerColor = colorResource(id = R.color.white),
        divider = {
            HorizontalDivider(
                color = colorResource(R.color.darkgray), thickness = 2.dp
            )
        }
    ) {
        Bar.entries.forEachIndexed { index, description ->
            Tab(
                text = {
                    Text(
                        text = description.getbar(context),
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight(600)
                    )
                },
                selected = tabindex == index,
                onClick = {
                    tabindex = index
                    navcontroller.navigate(description.name)
                          },
                icon = {
                    when (index) {
                        0 -> Icon(
                            painter = painterResource(R.drawable.protein),
                            contentDescription = description.getbar(context),
                            tint = colorResource(id = R.color.black)
                        )

                        1 -> Icon(
                            painter = painterResource(R.drawable.lowcarb),
                            contentDescription = description.getbar(context),
                            tint = colorResource(id = R.color.black)
                        )

                        2 -> Icon(
                            painter = painterResource(R.drawable.ketone),
                            contentDescription = description.getbar(context),
                            tint = colorResource(id = R.color.black)
                        )

                        3 -> Icon(
                            painter = painterResource(R.drawable.mediterra),
                            contentDescription = description.getbar(context),
                            tint = colorResource(id = R.color.black)
                        )

                        4 -> Icon(
                            painter = painterResource(R.drawable.custom),
                            contentDescription = description.getbar(context),
                            tint = colorResource(id = R.color.black)
                        )
                    }
                }
            )
        }
    }


}

@Preview
@Composable
fun PlanMainScreenPreview() {
    HealthHelperTheme {
        PlanMain()

    }
}