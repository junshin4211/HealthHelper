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

import web.dietdiary.service.impl.FoodService;
import web.dietdiary.service.impl.FoodServiceImpl;
import web.dietdiary.vo.Food;

@WebServlet("/dietDiary/food/insert")
public class InsertFoodController extends HttpServlet {
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
	protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
		Gson gson = new Gson();
		JsonObject jsonObject = new JsonObject();
		Food food;
		String errorMessage = "";
		
		food = gson.fromJson(req.getReader(), Food.class);
		if(food == null) {
			errorMessage = "NPE!!! foodItem is null";
			jsonObject.addProperty("result", false);
			jsonObject.addProperty("errorMessage", errorMessage);
			resp.getWriter().write(jsonObject.toString());
			return;
		}
		errorMessage = this.foodService.insert(food);
		if(errorMessage!=null) {
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