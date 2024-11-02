package com.example.healthhelper.dietary.eclipse.impl;

import java.util.ArrayList;

import javax.naming.NamingException;

import web.dietdiary.dao.impl.FoodDao;
import web.dietdiary.dao.impl.FoodDaoImpl;
import web.dietdiary.vo.Food;

public class FoodServiceImpl implements FoodService {

	private FoodDao foodDao;

	public FoodServiceImpl() throws NamingException {
		this.foodDao = new FoodDaoImpl(null);
	}

	@Override
	public ArrayList<Food> listAvailableFoods() {
		return this.foodDao.listAvailableFoods();
	}

	@Override
	public String insert(Food food) {
		try {
			int affectedRows = 0;
			affectedRows = this.foodDao.insert(food);
			if (affectedRows != 1) {
				throw new Exception("Unknown error!!!");
			}
			return "";
		} catch (Exception e) {
			return e.getMessage().toString();
		}
	}
}
