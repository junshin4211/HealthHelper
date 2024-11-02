package com.example.healthhelper.dietary.eclipse.impl;

import java.sql.Date;
import java.util.ArrayList;

import web.dietdiary.vo.DietDiary;
import web.dietdiary.vo.Nutrition;

public interface DietDiaryService {
	public ArrayList<DietDiary> search(DietDiary dietDiary, int mode);
	public ArrayList<DietDiary> searchByDate(DietDiary dietDiary);
	public ArrayList<DietDiary> searchByTime(DietDiary dietDiary);
	public ArrayList<DietDiary> searchByDateAndTime(DietDiary dietDiary);
		
	public DietDiary plusNutrition(DietDiary dietDiary, Nutrition nutrition);
	public String updateDietDiary(int foodId, Date date);
	public String insert(DietDiary dietDiary);
	
	public ArrayList<DietDiary> sort(ArrayList<DietDiary> dietDiaries, int mode,boolean isAscending);
	public ArrayList<DietDiary> sortByDate(ArrayList<DietDiary> dietDiaries,boolean isAscending);

}
