package com.example.healthhelper.dietary.eclipse.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import web.dietdiary.service.impl.FoodNameAndGramsService;
import web.dietdiary.service.impl.FoodNameAndGramsServiceImpl;
import web.dietdiary.vo.FoodNameAndGrams;

@WebServlet("/dietDiary/foodNameAndGramsDao/listAvailableFoodsNameAndGrams")
public class ListAvailableFoodsNameAndGramsController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private FoodNameAndGramsService foodNameAndGramsService;

	@Override
	public void init() throws ServletException {
		try {
			this.foodNameAndGramsService = new FoodNameAndGramsServiceImpl();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");

		JsonObject jsonObject = new JsonObject();
		int affectedRows = 0;
		String errorMessage = "";
		String result = "";
		ArrayList<FoodNameAndGrams> foodNameAndGramses = new ArrayList<FoodNameAndGrams>();
		foodNameAndGramses = this.foodNameAndGramsService.listAvailableFoodsNameAndGrams();
		
		if (foodNameAndGramses == null) {
			errorMessage = "Unknown error";
			jsonObject.addProperty("result", false);
			jsonObject.addProperty("errorMessage", errorMessage);
			resp.getWriter().write(jsonObject.toString());
			return;
		}

		affectedRows = foodNameAndGramses.size();
		if(affectedRows == 0) {
			errorMessage = "No data found";
			result = "[]";
			jsonObject.addProperty("affectedRows", affectedRows);
			jsonObject.addProperty("result", result);
			jsonObject.addProperty("errorMessage", errorMessage);
			resp.getWriter().write(jsonObject.toString());
		}
		
		
		errorMessage = "";
		
		result = "";
		result += "[";
		result += foodNameAndGramses.get(0).toString();
		for(int i=1;i<foodNameAndGramses.size();i++) {
			result += ",";
			result += foodNameAndGramses.get(i).toString();
		}
		result += "]";
		
		jsonObject.addProperty("affectedRows", affectedRows);
		jsonObject.addProperty("result", result);
		jsonObject.addProperty("errorMessage", errorMessage);
		resp.getWriter().write(jsonObject.toString());
		return;
	}
}
