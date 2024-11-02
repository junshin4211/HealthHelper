package com.example.healthhelper.dietary.eclipse.impl;

import web.dietdiary.vo.Food;
import web.dietdiary.vo.Nutrition;

public interface NutritionDao {
	Nutrition getNutritionFromFood(Food food);
}
