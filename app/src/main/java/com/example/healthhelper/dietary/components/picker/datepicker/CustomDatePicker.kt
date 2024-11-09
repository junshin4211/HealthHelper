package com.example.healthhelper.dietary.components.picker.datepicker

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.healthhelper.R
import com.example.healthhelper.attr.viewmodel.DefaultColorViewModel
import com.example.healthhelper.dietary.repository.FoodDiaryRepository
import com.example.healthhelper.dietary.repository.SelectedDateRepository
import com.example.healthhelper.dietary.util.dateformatter.DateFormatterPattern
import com.example.healthhelper.dietary.viewmodel.DiaryInfoUpdatorViewModel
import com.example.healthhelper.dietary.viewmodel.DiaryViewModel
import com.example.healthhelper.dietary.viewmodel.FoodDiaryViewModel
import com.example.healthhelper.dietary.viewmodel.SelectedDateViewModel
import java.sql.Date
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CustomDatePicker(
    diaryViewModel: DiaryViewModel = viewModel(),
    foodDiaryViewModel: FoodDiaryViewModel = viewModel(),
    selectedDateViewModel: SelectedDateViewModel = viewModel(),
    diaryInfoUpdatorViewModel: DiaryInfoUpdatorViewModel = viewModel(),
) {
    val TAG = "tag_CustomDatePicker"
    val context = LocalContext.current

    val selectedDateVO by selectedDateViewModel.selectedDate.collectAsState()
    val foodDiaryVO by foodDiaryViewModel.data.collectAsState()
    val diaryInfoUpdatorVO by diaryInfoUpdatorViewModel.data.collectAsState()

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

    var selectedDateMillis by remember { mutableStateOf(datePickerState.selectedDateMillis) }
    val selectedDate = selectedDateMillis?.let {
        Instant.ofEpochMilli(it)
            .atZone(ZoneId.of("UTC"))
            .toLocalDate()
            .format(DateTimeFormatter.ofPattern(DateFormatterPattern.pattern))
    } ?: stringResource(R.string.noChoose)

    val scrollState = rememberScrollState()

    LaunchedEffect(selectedDate) {
        if(selectedDate!=context.getString(R.string.noChoose)){
            Log.e(TAG,"LaunchedEffect(selectedDate) was called,selectedDate:${selectedDate}")

            // get date by formatting the given String.
            val date = Date.valueOf(selectedDate)

            // set data of repo so that its corresponding view model can access it.
            SelectedDateRepository.setDate(date)

            // set data of repo so that its corresponding view model can access it.
            FoodDiaryRepository.setCreateDate(selectedDateVO.selectedDate.value)

            val queriedDiaryInfoUpdatorVO = diaryInfoUpdatorViewModel.selectDiaryInfoByUserId(diaryInfoUpdatorVO)
            Log.e(TAG,"In LaunchedEffect(selectedDate) block,queriedDiaryInfoUpdatorVO:${queriedDiaryInfoUpdatorVO}")

            if(queriedDiaryInfoUpdatorVO.isEmpty()){ // fetch data failed.
                Toast.makeText(
                    context, context.getString(R.string.fetch_diary_id_failed),
                    Toast.LENGTH_LONG
                ).show()
                return@LaunchedEffect
            }

            // fetch data successfully.
            Toast.makeText(
                context, context.getString(R.string.fetch_diary_id_successfully),
                Toast.LENGTH_LONG
            ).show()

            // get id of diary by user id and create date of id of diaries.
            val queriedDiaryId = queriedDiaryInfoUpdatorVO[0].diaryID

            // only set first elem of list to repo -- FoodDiaryRepository.
            FoodDiaryRepository.setDiaryId(queriedDiaryId)

            val queriedFoodDiaryVOs = foodDiaryViewModel.selectByUserIdAndCreateDate(foodDiaryVO)

            if(queriedFoodDiaryVOs.isEmpty()) { // fetch data failed.
                Toast.makeText(
                    context, context.getString(R.string.fetch_nutrition_info_failed),
                    Toast.LENGTH_LONG
                ).show()
                return@LaunchedEffect
            }

            // fetch data successfully.
            // only set first elem of list to repo -- FoodDiaryRepository.
            FoodDiaryRepository.setData(queriedFoodDiaryVOs[0])

            Toast.makeText(context,context.getString(R.string.fetch_nutrition_info_successfully),
                Toast.LENGTH_LONG).show()
        }
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(16.dp,16.dp,16.dp,0.dp)
            .verticalScroll(scrollState),
        shape = RoundedCornerShape(15.dp),
    ) {
        DatePicker(

            state = datePickerState,
            showModeToggle = false,
            title = {

            },
            headline = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(start = 16.dp, end = 16.dp, bottom = 4.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    selectedDateMillis = datePickerState.selectedDateMillis
                    Text(
                        text = selectedDate,
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .height(30.dp),
                    )
                }
            },
            colors = DefaultColorViewModel.datePickerColors
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomDatePickerPreview() {
    CustomDatePicker()
}