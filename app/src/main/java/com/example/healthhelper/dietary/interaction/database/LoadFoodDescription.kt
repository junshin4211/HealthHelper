package com.example.healthhelper.dietary.interaction.database

import android.content.Context
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
    val currentDiaryDescriptionVO by diaryDescriptionViewModel.data.collectAsState()
    LaunchedEffect(Unit) {
        val currentDiaryId = currentDiaryDescriptionVO.diaryID
        val targetDiaryDescriptionVO = DiaryDescriptionVO(diaryID = currentDiaryId)
        val queriedDiaryDescriptionVOs = diaryDescriptionViewModel.fetchDataFromDatabase(targetDiaryDescriptionVO)
        if(queriedDiaryDescriptionVOs.isEmpty()){ // load data failed as it is empty.
            Toast.makeText(context,context.getString(R.string.load_diary_description_failed), Toast.LENGTH_LONG).show()
            return@LaunchedEffect
        }

        // only set first elem of the array into repo -- DiaryDescriptionRepository.
        DiaryDescriptionRepository.setData(queriedDiaryDescriptionVOs[0])
        Toast.makeText(context,context.getString(R.string.load_diary_description_successfully),
            Toast.LENGTH_LONG).show()
    }
}