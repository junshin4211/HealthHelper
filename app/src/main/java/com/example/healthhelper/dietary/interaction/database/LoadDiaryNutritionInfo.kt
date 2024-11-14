package com.example.healthhelper.dietary.interaction.database

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.example.healthhelper.R
import com.example.healthhelper.dietary.dataclasses.vo.DiaryVO
import com.example.healthhelper.dietary.repository.DiaryRepository
import com.example.healthhelper.dietary.viewmodel.DiaryViewModel
import kotlinx.coroutines.launch

fun LoadDiaryNutritionInfo(
    context: Context,
    diaryVO:DiaryVO,
    diaryViewModel: DiaryViewModel,
) {
    val TAG = "tag_LoadDiaryNutritionInfo"

    diaryViewModel.viewModelScope.launch {
        val queriedDiaries = diaryViewModel.selectDiaryByUserIdAndDate(diaryVO)
        if(queriedDiaries.isEmpty()){
            Toast.makeText(
                context,
                context.getString(R.string.load_diary_info_failed),
                Toast.LENGTH_LONG
            ).show()
            return@launch
        }
        Toast.makeText(
            context,
            context.getString(R.string.load_diary_info_successfully),
            Toast.LENGTH_LONG
        ).show()

        DiaryRepository.setData(queriedDiaries[0])
    }
}