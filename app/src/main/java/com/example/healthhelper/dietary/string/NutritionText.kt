package com.example.healthhelper.dietary.string

import com.example.healthhelper.dietary.dataclasses.vo.NutritionVO

fun NutritionText(
    nutritionVOS: List<NutritionVO>,
): List<String> {
    val texts: MutableList<String> = mutableListOf()
    nutritionVOS.forEachIndexed { index, nutrition ->
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