package com.example.healthhelper.dietary.eclipse.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import web.dietdiary.service.impl.MealTimeRangeCategoryService;
import web.dietdiary.service.impl.MealTimeRangeCategoryServiceImpl;
import web.dietdiary.util.gson.GsonForSqlDateAndSqlTime;
import web.dietdiary.vo.MealTimeRangeCategory;

@WebServlet("/dietDiary/mealTimeRangeCategory/select")
public class SelectMealTimeRangeCategoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MealTimeRangeCategoryService mealTimeRangeCategoryService;

	@Override
	public void init() throws ServletException {
		try {
			this.mealTimeRangeCategoryService = new MealTimeRangeCategoryServiceImpl();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	@Override 
	protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
		Gson gson = GsonForSqlDateAndSqlTime.gson;
		JsonObject jsonObject = new JsonObject();
		String result = "";
		String errorMessage = "";
		int affectedRows = 1;
		ArrayList<MealTimeRangeCategory> mealTimeRangeCategories = new ArrayList<MealTimeRangeCategory>();
		MealTimeRangeCategory mealTimeRangeCategory = gson.fromJson(req.getReader(), MealTimeRangeCategory.class);
		mealTimeRangeCategories = this.mealTimeRangeCategoryService.select(mealTimeRangeCategory);
		if(mealTimeRangeCategories == null) {
			errorMessage = "Unknown error!!!";
			affectedRows = -1;
			jsonObject.addProperty("result", false);
			jsonObject.addProperty("errorMessage", errorMessage);
			jsonObject.addProperty("affectedRows", affectedRows);
			resp.getWriter().write(jsonObject.toString());
			return;
		}
		errorMessage = "";
		affectedRows = mealTimeRangeCategories.size();
		
		result = "";
		
		result += "[";
		for(int i=0;i<mealTimeRangeCategories.size();i++) {
			MealTimeRangeCategory tempMealTimeRangeCategory = mealTimeRangeCategories.get(i);
			result +=  tempMealTimeRangeCategory.toString();
			result += "\n";
		}
		result += "]";
		result += "\n";
		
		jsonObject.addProperty("result", result);
		jsonObject.addProperty("errorMessage", errorMessage);
		jsonObject.addProperty("affectedRows", affectedRows);
		resp.getWriter().write(jsonObject.toString());
		return;
	}
}
