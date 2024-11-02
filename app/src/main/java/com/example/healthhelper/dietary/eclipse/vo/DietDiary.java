package com.example.healthhelper.dietary.eclipse.vo;

import java.sql.Date;
import java.sql.Time;

public class DietDiary {
	private int diaryId;
	private int userId;
	private Date createDate;
	private Time createTime;
	private Double totalFat;
	private Double totalCarbon;
	private Double totalProtein;
	private Double totalFiber;
	private Double totalSugar;
	private Double totalSodium;
	private Double totalCalories;
	
	public int getDiaryId() {
		return diaryId;
	}
	public void setDiaryId(int diaryId) {
		this.diaryId = diaryId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Time getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Time createTime) {
		this.createTime = createTime;
	}
	public Double getTotalFat() {
		return totalFat;
	}
	public void setTotalFat(Double totalFat) {
		this.totalFat = totalFat;
	}
	public Double getTotalCarbon() {
		return totalCarbon;
	}
	public void setTotalCarbon(Double totalCarbon) {
		this.totalCarbon = totalCarbon;
	}
	public Double getTotalProtein() {
		return totalProtein;
	}
	public void setTotalProtein(Double totalProtein) {
		this.totalProtein = totalProtein;
	}
	public Double getTotalFiber() {
		return totalFiber;
	}
	public void setTotalFiber(Double totalFiber) {
		this.totalFiber = totalFiber;
	}
	public Double getTotalSugar() {
		return totalSugar;
	}
	public void setTotalSugar(Double totalSugar) {
		this.totalSugar = totalSugar;
	}
	public Double getTotalSodium() {
		return totalSodium;
	}
	public void setTotalSodium(Double totalSodium) {
		this.totalSodium = totalSodium;
	}
	public Double getTotalCalories() {
		return totalCalories;
	}
	public void setTotalCalories(Double totalCalories) {
		this.totalCalories = totalCalories;
	}
	
	public boolean equals(Object object) {
		if(object instanceof DietDiary) {
			return false;
		}
		
		DietDiary dietDiary = (DietDiary) object;
		
		if(!(this.getDiaryId() == dietDiary.getDiaryId())) {
			return false;
		}
		
		if(!(this.getUserId() == dietDiary.getUserId())) {
			return false;
		}
		
		if(!(this.getCreateDate() == dietDiary.getCreateDate())) {
			return false;
		}
		
		if(!(this.getCreateTime() == dietDiary.getCreateTime())) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		return "DietDiary [diaryId=" + diaryId + ", userId=" + userId + ", createDate=" + createDate + ", createTime="
				+ createTime + ", totalFat=" + totalFat + ", totalCarbon=" + totalCarbon + ", totalProtein="
				+ totalProtein + ", totalFiber=" + totalFiber + ", totalSugar=" + totalSugar + ", totalSodium="
				+ totalSodium + ", totalCalories=" + totalCalories + "]";
	}

	
}
