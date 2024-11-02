package com.example.healthhelper.dietary.eclipse.impl;

import java.util.ArrayList;


import web.dietdiary.vo.Food;
import web.dietdiary.vo.FoodNameAndGrams;

public interface FoodDao {
	Food selectByFoodName(String name);
	Food selectByFoodId(int foodId);
	ArrayList<Food> listAvailableFoods();

	int insert(Food food);
	int delete(Food food);
}
