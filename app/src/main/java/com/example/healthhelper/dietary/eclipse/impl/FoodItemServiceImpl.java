package com.example.healthhelper.dietary.eclipse.impl;

import javax.naming.NamingException;

import web.dietdiary.dao.impl.FoodDao;
import web.dietdiary.dao.impl.FoodDaoImpl;
import web.dietdiary.dao.impl.FoodItemDao;
import web.dietdiary.dao.impl.FoodItemDaoImpl;
import web.dietdiary.vo.FoodItem;

public class FoodItemServiceImpl implements FoodItemService{

	private FoodItemDao foodItemDao;

	public FoodItemServiceImpl() throws NamingException {
		this.foodItemDao = new FoodItemDaoImpl(null);
	}
	
	@Override
	public String insert(FoodItem foodItem) {
		try {
			int affectedRows = 0;
			affectedRows = this.foodItemDao.insert(foodItem);
			if(affectedRows!=1) {
				throw new Exception("Unknown error!!!");
			}
			return "";
		}catch (Exception e) {
			return e.getMessage().toString();
		}
	}

	@Override
	public String update(FoodItem foodItem) {
		try {
			int affectedRows = 0;
			affectedRows = this.foodItemDao.insert(foodItem);
			if(affectedRows!=1) {
				throw new Exception("Unknown error!!!");
			}
			return "";
		}catch (Exception e) {
			return e.getMessage().toString();
		}
	}

}
