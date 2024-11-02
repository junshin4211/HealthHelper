package com.example.healthhelper.dietary.eclipse.impl;

import java.util.ArrayList;

import javax.naming.NamingException;

import web.dietdiary.dao.impl.MealTimeRangeCategoryDao;
import web.dietdiary.dao.impl.MealTimeRangeCategoryDaoImpl;
import web.dietdiary.vo.MealTimeRangeCategory;

public class MealTimeRangeCategoryServiceImpl implements MealTimeRangeCategoryService {

	private MealTimeRangeCategoryDao mealTimeRangeCategoryDao;
	
	public MealTimeRangeCategoryServiceImpl() throws NamingException {
		this.mealTimeRangeCategoryDao = new MealTimeRangeCategoryDaoImpl(null);	
	}
	
	@Override
	public String change(MealTimeRangeCategory mealTimeRangeCategory) {
		String errorMessage = "";
		ArrayList<MealTimeRangeCategory> newMealTimeRangeCategory = new ArrayList<MealTimeRangeCategory>();
		newMealTimeRangeCategory = this.mealTimeRangeCategoryDao.selectByUserId(mealTimeRangeCategory.getUserId());
		if(newMealTimeRangeCategory.isEmpty()) {
			errorMessage = this.insert(mealTimeRangeCategory);
			return errorMessage;
		}
		errorMessage = this.update(mealTimeRangeCategory);
		return errorMessage;
	}

	@Override
	public String insert(MealTimeRangeCategory mealTimeRangeCategory) {
		String errorMessage = "";
		int affectedRows = 1;
		affectedRows = this.mealTimeRangeCategoryDao.insert(mealTimeRangeCategory);
		if(affectedRows != 1) {
			errorMessage = "Unknown error";
			return errorMessage;
		}
		errorMessage = "";
		return errorMessage;
	}

	@Override
	public String update(MealTimeRangeCategory mealTimeRangeCategory) {
		String errorMessage = "";
		int affectedRows = 1;
		affectedRows = this.mealTimeRangeCategoryDao.update(mealTimeRangeCategory);
		if(affectedRows != 1) {
			errorMessage = "Unknown error";
			return errorMessage;
		}
		errorMessage = "";
		return errorMessage;
	}

	@Override
	public ArrayList<MealTimeRangeCategory> select(MealTimeRangeCategory mealTimeRangeCategory) {
		return this.mealTimeRangeCategoryDao.selectByUserId(mealTimeRangeCategory.getUserId());
	}
}
