package com.example.healthhelper.dietary.eclipse.impl;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import web.dietdiary.vo.DietDiary;
import web.dietdiary.vo.Nutrition;

public interface DietDiaryDao {
	String insert(DietDiary dietDiary);
	
	ArrayList<DietDiary> selectByTime(int userId,Time time);
	ArrayList<DietDiary> selectByDate(int userId,Date date);
	ArrayList<DietDiary> selectByDateAndTime(int userId,Date date,Time time);
		
	int updateByDiaryId(DietDiary dietDiary);
	
	DietDiary selectByDiaryIdAndDate(int diaryId, Date date);
}
