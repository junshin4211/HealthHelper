package com.example.healthhelper.person.screen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.healthhelper.R
import com.example.healthhelper.person.PersonScreenEnum
import com.example.healthhelper.person.personVM.AlarmViewModel
import com.example.healthhelper.person.widget.CustomTimePickerDialog
import com.example.healthhelper.person.widget.CustomTopBar
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.text.DateFormat
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun AlarmManagerScreen(
    navController: NavHostController,
    alarmViewModel: AlarmViewModel,
    context: Context,
) {
    var timePickerDialogShown by remember { mutableStateOf(false) }
    var showPermissionDialog by remember { mutableStateOf(false) }
    val alarms by alarmViewModel.alarms.collectAsState()

    val hasExactAlarmPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as android.app.AlarmManager
        alarmManager.canScheduleExactAlarms()
    } else {
        false
    }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val locationPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(
            Manifest.permission.POST_NOTIFICATIONS
        )
    } else {
        null
    }

    LaunchedEffect(Unit) {
        if (locationPermission != null) {
            if (!locationPermission.status.isGranted) {
                locationPermission.launchPermissionRequest()
            }
        }
    }
    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .background(
                colorResource(R.color.backgroundcolor)
            ),
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.dayNotification),
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigate(PersonScreenEnum.personScreen.name) },
                scrollBehavior = scrollBehavior,
                actions = { Spacer(modifier = Modifier.size(40.dp)) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = colorResource(R.color.primarycolor), thickness = 2.dp
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.backgroundcolor)),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LazyColumn(modifier = Modifier.padding(16.dp)) {
                        items(alarms) { alarmTime ->
                            AlarmItem(
                                alarmTime = alarmTime,
                                onRemove = { alarmViewModel.removeAlarm(context, alarmTime) })
                        }
                    }
                }

                Button(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                        .width(180.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.primarycolor)
                    ),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { timePickerDialogShown = true }
                ) {
                    Text(
                        stringResource(R.string.addTime),
                        color = colorResource(R.color.backgroundcolor),
                        fontSize = 24.sp
                    )
                }


                if (timePickerDialogShown) {
                    CustomTimePickerDialog(
                        onConfirm = { selectedTime ->
                            if (hasExactAlarmPermission) {
                                val alarmTime = Calendar.getInstance().apply {
                                    set(Calendar.HOUR_OF_DAY, selectedTime.hour)
                                    set(Calendar.MINUTE, selectedTime.minute)
                                    set(Calendar.SECOND, 0)
                                    if (timeInMillis <= System.currentTimeMillis()) {
                                        add(Calendar.DAY_OF_YEAR, 1)
                                    }
                                }
                                alarmViewModel.addAlarm(context, alarmTime)
                                timePickerDialogShown = false
                            } else {
                                showPermissionDialog = true
                            }
                        },
                        onDismiss = { timePickerDialogShown = false }
                    )
                }
                if (showPermissionDialog) {
                    PermissionDialog(
                        onDismiss = { showPermissionDialog = false },
                        onOpenSettings = {
                            openAppSettings(context)
                            showPermissionDialog = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AlarmItem(alarmTime: Calendar, onRemove: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(colorResource(R.color.backgroundcolor)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = DateFormat.getTimeInstance(DateFormat.SHORT).format(alarmTime.time),
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        IconButton(onClick = onRemove) {
            Icon(imageVector = Icons.Filled.Delete, contentDescription = "刪除鬧鐘")
        }
    }
    HorizontalDivider(color = colorResource(R.color.primarycolor))
}


@Composable
fun PermissionDialog(onDismiss: () -> Unit, onOpenSettings: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "請授權以設置鬧鐘提醒", fontSize = 20.sp)
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(stringResource(R.string.cancel))
                    }
                    TextButton(onClick = onOpenSettings) {
                        Text(stringResource(R.string.openSetting))
                    }
                }
            }
        }
    }
}


fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}
