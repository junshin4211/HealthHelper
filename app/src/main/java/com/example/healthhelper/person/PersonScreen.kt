package com.example.healthhelper.person

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.healthhelper.R

@Composable
fun PersonScreen(
    navController: NavHostController,
) {
    var expanded by remember { mutableStateOf(false) }
    Scaffold(containerColor = colorResource(R.color.backgroundcolor)) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Box(
                    modifier = Modifier.size(250.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.person),
                        contentDescription = "image",
                        modifier = Modifier
                            .size(250.dp),
                        contentScale = ContentScale.Fit
                    )
                    IconButton(
                        onClick = {
                            expanded = true
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(40.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.edit),
                            contentDescription = stringResource(R.string.editPhoto)
                        )
                        PhotoOptionsMenu(expanded = expanded, onDismiss = { expanded = false })
                    }
                }

                Button(
                    modifier = Modifier
                        .width(240.dp)
                        .height(60.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.primarycolor)),
                    onClick = {}
                ) {
                    Text(stringResource(R.string.personData), fontSize = 28.sp)
                }
                Button(
                    modifier = Modifier
                        .width(240.dp)
                        .height(60.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.primarycolor)),
                    onClick = {
                        navController.navigate(PersonScreenEnum.weightScreen.name)
                    }
                ) {
                    Text(stringResource(R.string.myWeight), fontSize = 28.sp)
                }
                Button(
                    modifier = Modifier
                        .width(240.dp)
                        .height(60.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.primarycolor)),
                    onClick = {}
                ) {
                    Text(stringResource(R.string.achievement), fontSize = 28.sp)
                }
                Button(
                    modifier = Modifier
                        .width(240.dp)
                        .height(60.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.primarycolor)),
                    onClick = {}
                ) {
                    Text(stringResource(R.string.logout), fontSize = 28.sp)
                }
            }
        }
    }

}

@Composable
fun PhotoOptionsMenu(expanded: Boolean, onDismiss: () -> Unit) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismiss() },
        modifier = Modifier.background(Color.White)
    ) {
        DropdownMenuItem(
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.baseline_photo_camera_24),
                    contentDescription = stringResource(R.string.takePicture)
                )
            },
            text = { Text(stringResource(R.string.takePicture)) },
            onClick = {
                onDismiss()
            }
        )
        DropdownMenuItem(
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.baseline_photo_24),
                    contentDescription = stringResource(R.string.takePicture)
                )
            },
            text = { Text(stringResource(R.string.choosePicture)) },
            onClick = {
                onDismiss()
            }
        )
    }
}

