package com.example.healthhelper.dietary.eclipse.impl;

import web.dietdiary.vo.Food;
import web.dietdiary.vo.Nutrition;

public class NutritionDaoImpl implements NutritionDao {

	@Override
	public Nutrition getNutritionFromFood(Food food) {
		Nutrition nutrition = new Nutrition();
		
		nutrition.setFat(food.getFat());
		nutrition.setProtein(food.getProtein());
		nutrition.setFiber(food.getFiber());
		nutrition.setCarbon(food.getCarbon());
		nutrition.setSodium(food.getSodium());
		nutrition.setSugar(food.getSugar());
		nutrition.setCalories(food.getCalories());
		
		return nutrition;
	}

}
