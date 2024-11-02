package com.example.healthhelper.dietary.eclipse.controller;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import web.dietdiary.service.impl.FoodItemService;
import web.dietdiary.service.impl.FoodItemServiceImpl;
import web.dietdiary.util.gson.GsonForSqlDateAndSqlTime;
import web.dietdiary.vo.FoodItem;

@WebServlet("/dietDiary/foodItem/update")
public class UpdateFoodItemController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	private FoodItemService foodItemService;

	@Override
	public void init() throws ServletException {
		try {
			this.foodItemService = new FoodItemServiceImpl();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	@Override 
	protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
		Gson gson = GsonForSqlDateAndSqlTime.gson;
		JsonObject jsonObject = new JsonObject();
		String errorMessage = "";
		FoodItem foodItem = gson.fromJson(req.getReader(), FoodItem.class);
		
		System.out.println("Ready to deserialize.");
		System.out.println("foodItem:"+foodItem);
		
		errorMessage = this.foodItemService.update(foodItem);
		 
		if(errorMessage != "") {
			errorMessage = "Unknown error!!!";
			jsonObject.addProperty("result", false);
			jsonObject.addProperty("errorMessage", errorMessage);
			resp.getWriter().write(jsonObject.toString());
			return;
		}
	
		jsonObject.addProperty("result", true);
		jsonObject.addProperty("errorMessage", errorMessage);
		resp.getWriter().write(jsonObject.toString());
		return;
	}
}
