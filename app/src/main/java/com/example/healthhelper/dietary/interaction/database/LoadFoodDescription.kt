package com.example.healthhelper.dietary.interaction.database

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.healthhelper.R
import com.example.healthhelper.dietary.dataclasses.vo.DiaryDescriptionVO
import com.example.healthhelper.dietary.repository.DiaryDescriptionRepository
import com.example.healthhelper.dietary.viewmodel.DiaryDescriptionViewModel

@Composable
fun LoadFoodDescription(
    context: Context,
    diaryDescriptionViewModel: DiaryDescriptionViewModel = viewModel(),
){
    val TAG = "tag_LoadFoodDescription"

    val currentDiaryDescriptionVO by diaryDescriptionViewModel.data.collectAsState()
    LaunchedEffect(Unit) {
        val currentDiaryId = currentDiaryDescriptionVO.diaryID
        val currentMealCategoryId = currentDiaryDescriptionVO.mealCategoryID
        val targetDiaryDescriptionVO = DiaryDescriptionVO(diaryID = currentDiaryId, mealCategoryID = currentMealCategoryId)
        Log.e(TAG,"LoadFoodDescription function, LaunchedEffect(Unit) blocked was called.targetDiaryDescriptionVO:${targetDiaryDescriptionVO}")
        val queriedDiaryDescriptionVOs = diaryDescriptionViewModel.loadDiaryInfo(targetDiaryDescriptionVO)
        Log.e(TAG,"LoadFoodDescription function, LaunchedEffect(Unit) blocked was called.queriedDiaryDescriptionVOs:${queriedDiaryDescriptionVOs}")
        if(queriedDiaryDescriptionVOs.isEmpty()){ // load data failed as it is empty.
            DiaryDescriptionRepository.clearData()
            Toast.makeText(context,context.getString(R.string.load_diary_description_failed), Toast.LENGTH_LONG).show()
            return@LaunchedEffect
        }
        // only set first elem of the array into repo -- DiaryDescriptionRepository.
        DiaryDescriptionRepository.setData(queriedDiaryDescriptionVOs[0])
        Toast.makeText(context,context.getString(R.string.load_diary_description_successfully),
            Toast.LENGTH_LONG).show()
    }
}