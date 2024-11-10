package com.example.healthhelper.person

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.healthhelper.R
import com.example.healthhelper.person.personVM.WeightViewModel
import com.example.healthhelper.person.widget.CustomTopBar
import com.example.healthhelper.person.widget.DeleteDataDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeightReviseScreen(
    navController: NavHostController,
    weightViewModel: WeightViewModel,
    recordId: String?,
) {
    val context = LocalContext.current
    val weightData = recordId?.toInt()
        ?.let { weightViewModel.filterWeightDataByRecordId(it).firstOrNull() }
    var recordDate = weightData?.recordDate ?: ""
    var height by remember { mutableStateOf(weightData?.height?.toString() ?: "") }
    var weight by remember { mutableStateOf(weightData?.weight?.toString() ?: "") }
    var bodyFat by remember { mutableStateOf(weightData?.bodyFat?.toString() ?: "") }

    var errMsg by remember { mutableStateOf("") }
    var isShowDeleteDialog by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopBar(
                title = "",
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(colorResource(R.color.backgroundcolor)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(recordDate, fontSize = 24.sp)
            Box(
                modifier = Modifier
                    .height(270.dp)
                    .width(412.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = stringResource(R.string.height), fontSize = 24.sp)
                        Spacer(modifier = Modifier.padding(end = 10.dp))
                        OutlinedTextField(
                            value = height,
                            onValueChange = { height = it },
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp),
                            trailingIcon = {
                                Text(text = stringResource(R.string.centermeter), fontSize = 18.sp)
                            },
                            textStyle = TextStyle(fontSize = 24.sp),
                            shape = RoundedCornerShape(8.dp),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = stringResource(R.string.weight), fontSize = 24.sp)
                        Spacer(modifier = Modifier.padding(end = 10.dp))
                        OutlinedTextField(
                            value = weight,
                            onValueChange = { weight = it },
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp),
                            trailingIcon = {
                                Text(text = stringResource(R.string.kilogram), fontSize = 18.sp)
                            },
                            textStyle = TextStyle(fontSize = 24.sp),
                            shape = RoundedCornerShape(8.dp),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text =  stringResource(R.string.body_fat), fontSize = 24.sp)
                        Spacer(modifier = Modifier.padding(end = 10.dp))
                        OutlinedTextField(
                            value = bodyFat,
                            onValueChange = { bodyFat = it },
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp),
                            trailingIcon = {
                                Text(text = stringResource(R.string.percentage), fontSize = 18.sp)
                            },
                            textStyle = TextStyle(fontSize = 24.sp),
                            shape = RoundedCornerShape(8.dp),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { isShowDeleteDialog = true },
                    modifier = Modifier
                        .width(100.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.primarycolor)),
                ) {
                    Text(
                        stringResource(R.string.delete),
                        color = Color.White,
                        fontSize = 24.sp
                    )
                }
                Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                Button(
                    onClick = {
                        val heightValue = height.toDoubleOrNull()
                        val weightValue = weight.toDoubleOrNull()
                        val fatValue = bodyFat.toDoubleOrNull() ?: 0.0

                        if (heightValue != null && weightValue != null && heightValue > 0 && weightValue > 0) {
                            val bmi = weightViewModel.calculateBMI(heightValue, weightValue)
                            if (bmi != 0.0) {
                                coroutineScope.launch {
                                    if(recordId!=null){
                                        val result = weightViewModel.updateBodyDataJson(
                                            recordId.toInt(), heightValue, weightValue, fatValue, recordDate, bmi
                                        )
                                        if (result) navController.navigateUp()
                                    }
                                }
                            }
                        } else {
                            errMsg = context.getString(R.string.failValueHeightWeight)
                        }
                    },
                    modifier = Modifier
                        .width(100.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.primarycolor)),
                ) {
                    Text(
                        stringResource(R.string.save),
                        color = Color.White,
                        fontSize = 24.sp
                    )
                }

            }
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Text(text = errMsg, color = Color.Red)

        }
        Text(text = errMsg, color = Color.Red)
        if (isShowDeleteDialog) {
            DeleteDataDialog(
                title = stringResource(R.string.deleteData),
                text = stringResource(R.string.deleteContent),
                onDismissRequest = {
                    isShowDeleteDialog = false
                },
                onConfirm = {
                    isShowDeleteDialog = false
                    coroutineScope.launch {
                        val result = weightViewModel.deleteBodyDataJson(recordId?:"")
                        if (result) navController.navigate(PersonScreenEnum.weightScreen.name)
                    }
                },
                onDismiss = {
                    isShowDeleteDialog = false
                }
            )
        }
    }

}


//@Preview(showBackground = true)
//@Composable
//fun WeightRevisePreview() {
//    WeightReviseScreen(rememberNavController())
//}