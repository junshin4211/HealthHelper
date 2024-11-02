package com.example.healthhelper.dietary.eclipse.impl;

import java.util.ArrayList;

import javax.naming.NamingException;

import web.dietdiary.dao.impl.FoodNameAndGramsDao;
import web.dietdiary.dao.impl.FoodNameAndGramsDaoImpl;
import web.dietdiary.vo.FoodNameAndGrams;

public class FoodNameAndGramsServiceImpl implements FoodNameAndGramsService{

	private FoodNameAndGramsDao foodNameAndGramsDao ;

	public FoodNameAndGramsServiceImpl() throws NamingException {
		this.foodNameAndGramsDao = new FoodNameAndGramsDaoImpl(null);
	}
	
	@Override
	public ArrayList<FoodNameAndGrams> listAvailableFoodsNameAndGrams() {
		return this.foodNameAndGramsDao.listAvailableFoodsNameAndGrams();
	}
}
