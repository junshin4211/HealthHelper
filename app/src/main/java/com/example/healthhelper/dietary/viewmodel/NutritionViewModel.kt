package com.example.healthhelper.dietary.viewmodel

import com.example.healthhelper.dietary.dataclasses.Nutrition

object NutritionViewModel {
    val nutritions = mutableListOf<Nutrition>()

    fun addDiary(
        fat:Float,
        carbon:Float,
        protein:Float,
        fiber:Float,
        sugar:Float,
        sodium:Float,
        calories:Float,
    ){
        val nutrition = Nutrition(
            fat = fat,
            carbon = carbon,
            protein = protein,
            fiber = fiber,
            sugar = sugar,
            sodium = sodium,
            calories = calories,
        )
        nutritions.add(nutrition)
    }

    fun fetchDefaultEntries(): List<Nutrition>{
        return listOf(
            Nutrition(
                fat = 20f,
                carbon = 10f,
                protein = 5f,
                fiber = 2f,
                sugar = 3f,
                sodium = 4f,
                calories = 6f,
            ),
            Nutrition(
                fat = 30f,
                carbon = 20f,
                protein = 15f,
                fiber = 23f,
                sugar = 32f,
                sodium = 42f,
                calories = 1f,
            )
        )
    }
}