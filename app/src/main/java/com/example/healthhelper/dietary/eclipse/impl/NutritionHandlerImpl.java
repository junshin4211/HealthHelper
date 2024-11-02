package com.example.healthhelper.dietary.eclipse.impl;

import web.dietdiary.vo.Nutrition;

public class NutritionHandlerImpl implements NutritionHandler {

	@Override
	public Nutrition multiply(Nutrition nutrition, double grams) {
		Nutrition newNutrition = new Nutrition();
		newNutrition.setFat(nutrition.getFat() * grams);
		newNutrition.setFiber(nutrition.getFiber() * grams);
		newNutrition.setCarbon(nutrition.getCarbon() * grams);
		newNutrition.setProtein(nutrition.getProtein() * grams);
		newNutrition.setSugar(nutrition.getSugar() * grams);
		newNutrition.setSodium(nutrition.getSodium() * grams);
		newNutrition.setCalories(nutrition.getCalories() * grams);		
		return newNutrition;
	}
}
