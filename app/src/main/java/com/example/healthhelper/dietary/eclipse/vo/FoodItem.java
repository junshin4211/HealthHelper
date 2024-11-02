package com.example.healthhelper.dietary.eclipse.vo;

public class FoodItem {
	private int diaryId;
	private int foodId;
	private Double grams;
	
	public int getDiaryId() {
		return diaryId;
	}
	public void setDiaryId(int diaryId) {
		this.diaryId = diaryId;
	}
	public int getFoodId() {
		return foodId;
	}
	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}
	public Double getGrams() {
		return grams;
	}
	public void setGrams(Double grams) {
		this.grams = grams;
	}
	@Override
	public String toString() {
		return "FoodItem [diaryId=" + diaryId + ", foodId=" + foodId + ", grams=" + grams + "]";
	}	
}
