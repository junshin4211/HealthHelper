package com.example.healthhelper.person

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.healthhelper.R
import com.example.healthhelper.person.widget.CustomTopBar
import com.example.healthhelper.person.widget.MeasurementRow
import com.example.healthhelper.person.widget.SaveButton
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeightSettingScreen(navController: NavHostController) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var showDatePickerDialog by remember { mutableStateOf(false) }
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
    var selectDate by remember { mutableStateOf(LocalDate.now().format(dateFormatter)) }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var fat by remember { mutableStateOf("") }


    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopBar(
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
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(selectDate, fontSize = 24.sp)
                IconButton(
                    onClick = {
                        showDatePickerDialog = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = stringResource(R.string.calendar)
                    )
                }
            }

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
                    MeasurementRow(
                        stringResource(R.string.height),
                        height,
                        stringResource(R.string.centermeter)
                    ) { height = it }
                    Spacer(modifier = Modifier.height(8.dp))
                    MeasurementRow(
                        stringResource(R.string.weight),
                        weight,
                        stringResource(R.string.kilogram)
                    ) { weight = it }
                    Spacer(modifier = Modifier.height(8.dp))
                    MeasurementRow(
                        stringResource(R.string.body_fat),
                        fat,
                        stringResource(R.string.percentage)
                    ) { fat = it }
                }
            }
            SaveButton(
                onClick = { navController.navigateUp() }
            )
        }
        if (showDatePickerDialog) {
            CustomDatePickerDialog(
                onDismissRequest = {
                    showDatePickerDialog = false
                },
                onConfirm = { utcTimeMillis ->
                    selectDate = "${
                        utcTimeMillis?.let {
                            Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC"))
                                .toLocalDate().format(dateFormatter)
                        } ?: "no selection"
                    }"
                    showDatePickerDialog = false
                },
                onDismiss = {
                    showDatePickerDialog = false
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (Long?) -> Unit,
    onDismiss: () -> Unit,
) {
    val today = LocalDate.now()
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val selectedDate = Instant.ofEpochMilli(utcTimeMillis)
                    .atZone(ZoneId.of("UTC"))
                    .toLocalDate()
                return !selectedDate.isAfter(today)
            }
        }
    )
    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(datePickerState.selectedDateMillis)
                },
                colors = ButtonDefaults.buttonColors(colorResource(R.color.primarycolor))
            ) {
                Text(stringResource(R.string.confirm), color = Color.White)
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(colorResource(R.color.primarycolor))
            ) {
                Text(stringResource(R.string.cancel), color = Color.White)
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = colorResource(R.color.backgroundcolor)
        )

        ) {
        DatePicker(
            state = datePickerState,
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
                containerColor = colorResource(R.color.backgroundcolor),
                titleContentColor = colorResource(R.color.backgroundcolor),
            )
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun WeightSettingPreview() {
    WeightSettingScreen(rememberNavController())
}