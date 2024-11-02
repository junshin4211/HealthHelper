package com.example.healthhelper.dietary.eclipse.impl;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.naming.NamingException;

import web.dietdiary.dao.impl.DietDiaryDao;
import web.dietdiary.dao.impl.DietDiaryDaoImpl;
import web.dietdiary.dao.impl.FoodDao;
import web.dietdiary.dao.impl.FoodDaoImpl;
import web.dietdiary.dao.impl.FoodItemDao;
import web.dietdiary.dao.impl.FoodItemDaoImpl;
import web.dietdiary.dao.impl.NutritionDao;
import web.dietdiary.dao.impl.NutritionDaoImpl;
import web.dietdiary.handler.impl.NutritionHandler;
import web.dietdiary.handler.impl.NutritionHandlerImpl;
import web.dietdiary.vo.DietDiary;
import web.dietdiary.vo.Food;
import web.dietdiary.vo.FoodItem;
import web.dietdiary.vo.Nutrition;

public class DietDiaryServiceImpl implements DietDiaryService {

	private DietDiaryDao dietDiaryDao;
	private FoodItemDao foodItemDao;
	private FoodDao foodDao;
	private NutritionDao nutritionDao;
	private NutritionHandler nutritionHandler;

	public DietDiaryServiceImpl(DietDiaryDao dietDiaryDao) throws NamingException {
		this.dietDiaryDao = new DietDiaryDaoImpl(null);
		this.foodItemDao = new FoodItemDaoImpl(null);
		this.foodDao = new FoodDaoImpl(null);
		this.nutritionDao = new NutritionDaoImpl();
		this.nutritionHandler = new NutritionHandlerImpl();
	}

	@Override
	public String insert(DietDiary dietDiary) {
		String errorMessage = "";
		boolean duplicatedDate = false;
		ArrayList<DietDiary> dietDiaries = new ArrayList<DietDiary>();
		
		dietDiaries = this.search(dietDiary, 1);
		if(dietDiaries==null) {
			errorMessage = "Unknown error during execution of searching data!!!";
			return errorMessage;
		}
		if(!dietDiaries.isEmpty()) {
			errorMessage = "Existing error!!! The userId and createDate in foodDiary table has already exists. Can not insert it!!!";
			return errorMessage;
		}
		
		errorMessage = this.dietDiaryDao.insert(dietDiary);
		return errorMessage;
	}

	@Override
	public ArrayList<DietDiary> search(DietDiary dietDiary, int mode) {
		if (mode == 1) {
			return this.searchByDate(dietDiary);
		} else if (mode == 2) {
			return this.searchByTime(dietDiary);
		} else if (mode == 3) {
			return this.searchByDateAndTime(dietDiary);
		}
		return null;
	}

	@Override
	public ArrayList<DietDiary> searchByDate(DietDiary dietDiary) {
		int userId = dietDiary.getUserId();
		Date createDate = dietDiary.getCreateDate();
		return this.dietDiaryDao.selectByDate(userId, createDate);
	}

	@Override
	public ArrayList<DietDiary> searchByTime(DietDiary dietDiary) {
		int userId = dietDiary.getUserId();
		Time createTime = dietDiary.getCreateTime();
		return this.dietDiaryDao.selectByTime(userId, createTime);
	}

	@Override
	public ArrayList<DietDiary> searchByDateAndTime(DietDiary dietDiary) {
		int userId = dietDiary.getUserId();
		Date createDate = dietDiary.getCreateDate();
		Time createTime = dietDiary.getCreateTime();
		return this.dietDiaryDao.selectByDateAndTime(userId, createDate, createTime);
	}

	@Override
	public DietDiary plusNutrition(DietDiary dietDiary, Nutrition nutrition) {
		DietDiary result = new DietDiary();

		result.setUserId(dietDiary.getUserId());
		result.setDiaryId(dietDiary.getDiaryId());
		result.setCreateDate(dietDiary.getCreateDate());
		result.setCreateTime(dietDiary.getCreateTime());
		result.setTotalFat(dietDiary.getTotalFat() + nutrition.getFat());
		result.setTotalFiber(dietDiary.getTotalFiber() + nutrition.getFiber());
		result.setTotalProtein(dietDiary.getTotalProtein() + nutrition.getProtein());
		result.setTotalCarbon(dietDiary.getTotalCarbon() + nutrition.getCarbon());
		result.setTotalSugar(dietDiary.getTotalSugar() + nutrition.getSugar());
		result.setTotalSodium(dietDiary.getTotalSodium() + nutrition.getSodium());
		result.setTotalCalories(dietDiary.getTotalCalories() + nutrition.getCalories());

		return result;
	}

	@Override
	public String updateDietDiary(int foodId, Date date) {
		try {
			FoodItem foodItem = foodItemDao.select(foodId);
			Food food = foodDao.selectByFoodId(foodId);
			Nutrition nutrition = nutritionDao.getNutritionFromFood(food);
			int diaryId = foodItem.getDiaryId();
			Double grams = foodItem.getGrams();
			
			DietDiary dietDiary = dietDiaryDao.selectByDiaryIdAndDate(diaryId, date);
			if (dietDiary == null) {
				throw new Exception("Unknown error!!!");
			}

			nutrition = nutritionHandler.multiply(nutrition, grams);
					
			DietDiary updatedDietDiary = this.plusNutrition(dietDiary, nutrition);
			this.dietDiaryDao.updateByDiaryId(updatedDietDiary);
			
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getStackTrace().toString();
		}
	}

	@Override
	public ArrayList<DietDiary> sort(ArrayList<DietDiary> dietDiaries, int mode, boolean isAscending) {
		if (mode == 1) {
			return this.sortByDate(dietDiaries,isAscending);
		}
		return null;
	}

	@Override
	public ArrayList<DietDiary> sortByDate(ArrayList<DietDiary> dietDiaries, boolean isAscending) {
		DietDiary[] newDietDiaries = (DietDiary[]) dietDiaries.clone();
		if(isAscending) {
			Arrays.sort(newDietDiaries);
		}else {
			Arrays.sort(newDietDiaries);
			Collections.reverse(Arrays.asList(newDietDiaries));
		}
		ArrayList<DietDiary> result = (ArrayList<DietDiary>) Arrays.asList(newDietDiaries);
		return result;
	}
}
