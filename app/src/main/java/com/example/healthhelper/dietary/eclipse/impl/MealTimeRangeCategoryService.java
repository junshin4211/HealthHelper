package com.example.healthhelper.dietary.eclipse.impl;

import java.util.ArrayList;

import web.dietdiary.vo.MealTimeRangeCategory;

public interface MealTimeRangeCategoryService {
	String change(MealTimeRangeCategory mealTimeRangeCategory);
	String insert(MealTimeRangeCategory mealTimeRangeCategory);
	String update(MealTimeRangeCategory mealTimeRangeCategory);
	ArrayList<MealTimeRangeCategory> select(MealTimeRangeCategory mealTimeRangeCategory);
}
