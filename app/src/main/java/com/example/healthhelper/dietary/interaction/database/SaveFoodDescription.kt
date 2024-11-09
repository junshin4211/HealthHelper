package com.example.healthhelper.dietary.interaction.database

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.healthhelper.R
import com.example.healthhelper.dietary.repository.DiaryDescriptionRepository
import com.example.healthhelper.dietary.viewmodel.DiaryDescriptionViewModel


@Composable
fun SaveFoodDescription(
    navController: NavHostController,
    context: Context,
    foodIconUri: Uri?,
    foodDescription: String,
    diaryDescriptionViewModel: DiaryDescriptionViewModel = viewModel(),
) {
    val TAG = "tag_SaveFoodDescription"

    DiaryDescriptionRepository.setUri(foodIconUri)
    DiaryDescriptionRepository.setDescription(foodDescription)

    val diaryDescriptionVO by diaryDescriptionViewModel.data.collectAsState()

    LaunchedEffect(Unit) {
        Log.e(TAG,"LaunchedEffect(Unit) block in SaveFoodDescription function was called. diaryDescriptionVO:${diaryDescriptionVO}")
        // try to insert diaryDescriptionVO to database. (i.e. when there are no record about given diaryDescriptionVO.diaryId, insert it.
        // Otherwise, update it by given diaryDescriptionVO.diaryId.
        val affectedRows = diaryDescriptionViewModel.tryToInsert(diaryDescriptionVO)
    }

    Toast.makeText(
        context,
        context.getString(R.string.save_food_description_successfully),
        Toast.LENGTH_LONG
    ).show()

    navController.navigateUp()
}
