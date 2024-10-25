package com.example.healthhelper.dietary.string

import com.example.healthhelper.dietary.dataclasses.dao.NutritionDao

fun NutritionText(
    nutritionDaos: List<NutritionDao>,
): List<String> {
    val texts: MutableList<String> = mutableListOf()
    nutritionDaos.forEachIndexed { index, nutrition ->
        val text = "fat:${nutrition.fat}\n" +
                "carbon:${nutrition.carbon}\n" +
                "protein:${nutrition.protein}\n" +
                "fiber:${nutrition.fiber}\n" +
                "sugar:${nutrition.sugar}\n" +
                "sodium:${nutrition.sodium}\n" +
                "calories:${nutrition.calories}\n"
        texts.add(text)
    }
    return texts
}