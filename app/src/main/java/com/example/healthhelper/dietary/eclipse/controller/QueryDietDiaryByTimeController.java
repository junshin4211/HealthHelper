package com.example.healthhelper.dietary.eclipse.controller;

import java.io.IOException;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import web.dietdiary.constant.SqlDatePattern;
import web.dietdiary.service.impl.DietDiaryService;
import web.dietdiary.service.impl.DietDiaryServiceImpl;
import web.dietdiary.util.datetime.DateTimeHandler;
import web.dietdiary.util.datetime.DateTimeHandlerImpl;
import web.dietdiary.util.gson.GsonForSqlDateAndSqlTime;
import web.dietdiary.vo.DietDiary;

@WebServlet("/dietDiary/query/byTime")
public class QueryDietDiaryByTimeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DietDiaryService dietDiaryService;

	@Override
	public void init() throws ServletException {
		try {
			this.dietDiaryService = new DietDiaryServiceImpl(null);
			
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
		String errorMessage = "";
		String result = "";
		int affectedRow = 0;
		ArrayList<DietDiary> dietDiaries = new ArrayList<DietDiary>();
		DietDiary dietDiary = gson.fromJson(req.getReader(), DietDiary.class);
		
		System.out.println("Ready to deserialize.");
		System.out.println("dietDiary:"+dietDiary);
		
		dietDiaries = this.dietDiaryService.search(dietDiary,3);
		 
		if(dietDiaries == null) {
			errorMessage = "Unknown error!!!";
			affectedRow = -1;
			jsonObject.addProperty("result", false);
			jsonObject.addProperty("affectedRow", affectedRow);
			jsonObject.addProperty("errorMessage", errorMessage);
			resp.getWriter().write(jsonObject.toString());
			return;
		}
		
		if(dietDiaries.isEmpty()){
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
		for(int i=0;i<dietDiaries.size();i++) {
			DietDiary tempDietDiary = dietDiaries.get(i);
			result +=  tempDietDiary.toString();
			result += "\n";
		}
		result += "]";
		result += "\n";
		
		affectedRow = dietDiaries.size(); 
				
		errorMessage = "";
		
		jsonObject.addProperty("result", result);
		jsonObject.addProperty("affectedRow", affectedRow);
		jsonObject.addProperty("errorMessage", errorMessage);
		resp.getWriter().write(jsonObject.toString());
		
		return;
	}
}
