package com.example.healthhelper.dietary.eclipse.vo;

import java.sql.Time;

public class MealTimeRangeCategory {
	private int userId;
	private Time breakfastStartTime;
	private Time breakfastEndTime;
	private Time lunchStartTime;
	private Time lunchEndTime;
	private Time dinnerStartTime;
	private Time dinnerEndTime;
	private Time supperStartTime;
	private Time supperEndTime;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Time getBreakfastStartTime() {
		return breakfastStartTime;
	}
	public void setBreakfastStartTime(Time breakfastStartTime) {
		this.breakfastStartTime = breakfastStartTime;
	}
	public Time getBreakfastEndTime() {
		return breakfastEndTime;
	}
	public void setBreakfastEndTime(Time breakfastEndTime) {
		this.breakfastEndTime = breakfastEndTime;
	}
	public Time getLunchStartTime() {
		return lunchStartTime;
	}
	public void setLunchStartTime(Time lunchStartTime) {
		this.lunchStartTime = lunchStartTime;
	}
	public Time getLunchEndTime() {
		return lunchEndTime;
	}
	public void setLunchEndTime(Time lunchEndTime) {
		this.lunchEndTime = lunchEndTime;
	}
	public Time getDinnerStartTime() {
		return dinnerStartTime;
	}
	public void setDinnerStartTime(Time dinnerStartTime) {
		this.dinnerStartTime = dinnerStartTime;
	}
	public Time getDinnerEndTime() {
		return dinnerEndTime;
	}
	public void setDinnerEndTime(Time dinnerEndTime) {
		this.dinnerEndTime = dinnerEndTime;
	}
	public Time getSupperStartTime() {
		return supperStartTime;
	}
	public void setSupperStartTime(Time supperStartTime) {
		this.supperStartTime = supperStartTime;
	}
	public Time getSupperEndTime() {
		return supperEndTime;
	}
	public void setSupperEndTime(Time supperEndTime) {
		this.supperEndTime = supperEndTime;
	}
	
	@Override
	public String toString() {
		return "MealTimeRangeCategory [userId=" + userId + ", breakfastStartTime=" + breakfastStartTime
				+ ", breakfastEndTime=" + breakfastEndTime + ", lunchStartTime=" + lunchStartTime + ", lunchEndTime="
				+ lunchEndTime + ", dinnerStartTime=" + dinnerStartTime + ", dinnerEndTime=" + dinnerEndTime
				+ ", supperStartTime=" + supperStartTime + ", supperEndTime=" + supperEndTime + "]";
	}
	
}
