package com.example.healthhelper.dietary.components.picker.datepicker

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.healthhelper.dietary.dataclasses.vo.DiaryVO
import com.example.healthhelper.dietary.repository.DiaryRepository
import com.example.healthhelper.dietary.repository.FoodItemRepository
import com.example.healthhelper.dietary.repository.NutritionInfoRepository
import com.example.healthhelper.dietary.repository.SelectedDateRepository
import com.example.healthhelper.dietary.util.dateformatter.DateFormatterPattern
import com.example.healthhelper.dietary.viewmodel.DiaryViewModel
import com.example.healthhelper.dietary.viewmodel.FoodItemViewModel
import com.example.healthhelper.dietary.viewmodel.SelectedDateViewModel
import java.sql.Date
import java.sql.Time
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CustomDatePicker(
    diaryViewModel: DiaryViewModel = viewModel(),
    selectedDateViewModel: SelectedDateViewModel = viewModel(),
    foodItemViewModel: FoodItemViewModel = viewModel(),
) {
    val TAG = "tag_CustomDatePicker"
    val context = LocalContext.current

    val selectedDateVO by selectedDateViewModel.selectedDate.collectAsState()
    val diaryVO by diaryViewModel.data.collectAsState()
    val selectedFoodItemVO by foodItemViewModel.selectedData.collectAsState()

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

    LaunchedEffect(Unit) {
        val currentTimeMillis = System.currentTimeMillis()
        Log.e(TAG, "currentTimeMillis:${currentTimeMillis}")
        datePickerState.selectedDateMillis = currentTimeMillis
    }
    var selectedDateMillis by remember { mutableStateOf(datePickerState.selectedDateMillis) }
    val selectedDate = selectedDateMillis?.let {
        Instant.ofEpochMilli(it)
            .atZone(ZoneId.of("UTC"))
            .toLocalDate()
            .format(DateTimeFormatter.ofPattern(DateFormatterPattern.pattern))
    } ?: stringResource(R.string.noChoose)

    LaunchedEffect(selectedDate) {
        if (selectedDate != context.getString(R.string.noChoose)) {

            // get date by formatting the given String.
            val date = Date.valueOf(selectedDate)

            // set data of repo so that its corresponding view model can access it.
            SelectedDateRepository.setDate(date)

            // set data of repo so that its corresponding view model can access it.
            DiaryRepository.setCreateDate(selectedDateVO.selectedDate.value)

            val queriedDiaryVOs = diaryViewModel.selectDiaryByUserIdAndDate(diaryVO)

            if (queriedDiaryVOs.isEmpty()) {
                val toastMessage = context.getString(R.string.load_diary_info_failed) +
                        context.getString(R.string.insert_diary_id_tip_message)
                Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show()

                val newDiaryVO = DiaryVO()
                newDiaryVO.userID = diaryVO.userID
                newDiaryVO.createDate = date
                newDiaryVO.createTime = Time(0)
                newDiaryVO.totalProtein = -1.0
                newDiaryVO.totalCarbon = -1.0
                newDiaryVO.totalFat = -1.0
                newDiaryVO.totalSugar = -1.0
                newDiaryVO.totalSodium = -1.0
                newDiaryVO.totalFiber = -1.0
                newDiaryVO.totalCalories = -1.0
                val affectedRows = diaryViewModel.insertDiary(newDiaryVO)

                NutritionInfoRepository.setNutritionInfo(newDiaryVO)

                Toast.makeText(context, context.getString(R.string.insert_diary_successfully), Toast.LENGTH_LONG).show()

                FoodItemRepository.setSelectedDiaryId(newDiaryVO.diaryID)

                return@LaunchedEffect
            }

            NutritionInfoRepository.setNutritionInfo(queriedDiaryVOs[0])
            // fetch data successfully.
            Toast.makeText(
                context, context.getString(R.string.fetch_diary_id_successfully),
                Toast.LENGTH_SHORT
            ).show()

            Toast.makeText(
                context, context.getString(R.string.fetch_nutrition_info_successfully),
                Toast.LENGTH_SHORT
            ).show()

            DiaryRepository.setData(queriedDiaryVOs[0])

            FoodItemRepository.setSelectedDiaryId(queriedDiaryVOs[0].diaryID)
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp, 16.dp, 0.dp),
        shape = RoundedCornerShape(15.dp),
    ) {
        DatePicker(

            state = datePickerState,
            showModeToggle = false,
            title = null,
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