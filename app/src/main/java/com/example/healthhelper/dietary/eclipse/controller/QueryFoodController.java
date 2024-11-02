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

import web.dietdiary.service.impl.FoodService;
import web.dietdiary.service.impl.FoodServiceImpl;
import web.dietdiary.util.gson.GsonForSqlDateAndSqlTime;
import web.dietdiary.vo.Food;

@WebServlet("/dietDiary/food/query/listAllAvailableFoods")
public class QueryFoodController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private FoodService foodService;

	@Override
	public void init() throws ServletException {
		try {
			this.foodService = new FoodServiceImpl();
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	@Override 
	protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
		JsonObject jsonObject = new JsonObject();
		String errorMessage = "";
		String result = "";
		int affectedRow = 0;
		ArrayList<Food> foods = new ArrayList<Food>();
		
		foods = this.foodService.listAvailableFoods();
			
		if(foods.isEmpty()){
			errorMessage = "";
			result = "not found.";
			affectedRow = 0;
			jsonObject.addProperty("result", result);
			jsonObject.addProperty("affectedRow", affectedRow);
			jsonObject.addProperty("errorMessage", errorMessage);
			resp.getWriter().write(jsonObject.toString());
			return;
		}
		
		result = "";
		
		result += "[";
		result += "\n";
		for(int i=0;i<foods.size();i++) {
			Food tempFood = foods.get(i);
			result +=  tempFood.toString();
			result += "\n";
		}
		result += "]";
		result += "\n";
		
		affectedRow = foods.size(); 
				
		errorMessage = "";
		
		jsonObject.addProperty("result", result);
		jsonObject.addProperty("affectedRow", affectedRow);
		jsonObject.addProperty("errorMessage", errorMessage);
		resp.getWriter().write(jsonObject.toString());
		
		return;
	}
}
