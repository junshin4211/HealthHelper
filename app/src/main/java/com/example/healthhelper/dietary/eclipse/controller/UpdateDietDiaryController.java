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

import web.dietdiary.checker.impl.DietDiaryChecker;
import web.dietdiary.checker.impl.DietDiaryCheckerImpl;
import web.dietdiary.service.impl.DietDiaryService;
import web.dietdiary.service.impl.DietDiaryServiceImpl;
import web.dietdiary.util.gson.GsonForSqlDateAndSqlTime;
import web.dietdiary.vo.DietDiary;

@WebServlet("/dietDiary/update/updateDietDiary")
public class UpdateDietDiaryController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	private DietDiaryService dietDiaryService;
	private DietDiaryChecker dietDiaryChecker;

	@Override
	public void init() throws ServletException {
		try {
			this.dietDiaryService = new DietDiaryServiceImpl(null);
			this.dietDiaryChecker = new DietDiaryCheckerImpl();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
		Gson gson = GsonForSqlDateAndSqlTime.gson;
		JsonObject jsonObject = new JsonObject();
		String errorMessage = "";
		boolean isValidData = true;
		DietDiary dietDiary = gson.fromJson(req.getReader(), DietDiary.class);
		System.out.println("Ready to deserialize.");
		System.out.println("DietDiary:"+dietDiary.toString());
		
		isValidData = dietDiaryChecker.check(dietDiary);
		if(!isValidData) {
			errorMessage = "Invalid Data in DietDiary!!!";
			jsonObject.addProperty("result", false);
			jsonObject.addProperty("errorMessage", errorMessage);
			resp.getWriter().write(jsonObject.toString());	
			return;
		}
		
		errorMessage = this.dietDiaryService.updateDietDiary(dietDiary.getDiaryId(), dietDiary.getCreateDate());
		if(errorMessage != "") {
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
