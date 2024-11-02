package com.example.healthhelper.dietary.eclipse.impl;

import java.util.ArrayList;

import web.dietdiary.vo.Food;

public interface FoodService {
	ArrayList<Food> listAvailableFoods();
	String insert(Food food);
}
