package com.example.healthhelper.dietary.eclipse.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import web.dietdiary.vo.DietDiary;
import web.dietdiary.vo.Food;
import web.dietdiary.vo.FoodItem;
import web.dietdiary.vo.FoodNameAndGrams;

public class FoodDaoImpl implements FoodDao {

	private DataSource dataSource;

	public FoodDaoImpl(DataSource dataSource) throws NamingException {
		this.dataSource = dataSource;
		if (this.dataSource == null) {
			this.dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/iHealth");
		}
	}

	public Connection getConnection() throws SQLException {
		return this.dataSource.getConnection();
	}

	private ArrayList<Food> resultSetToObjects(ResultSet resultSet) throws SQLException {
		ArrayList<Food> foods = new ArrayList<Food>();
		while (resultSet.next()) {
			
			Food food = new Food();

			int foodId = resultSet.getInt("foodID");
			String foodName = resultSet.getString("foodname");
			Double fat = resultSet.getDouble("fat");
			Double carbon = resultSet.getDouble("carbon");
			Double protein = resultSet.getDouble("protein");
			Double fiber = resultSet.getDouble("fiber");
			Double sugar = resultSet.getDouble("sugar");
			Double sodium = resultSet.getDouble("sodium");
			Double calories = resultSet.getDouble("calories");

			food.setFoodId(foodId);
			food.setFoodName(foodName);
			food.setFat(fat);
			food.setCarbon(carbon);
			food.setProtein(protein);
			food.setFiber(fiber);
			food.setSugar(sugar);
			food.setSodium(sodium);
			food.setCalories(calories);
			
			foods.add(food);
		}
		
		return foods;
	}

	@Override
	public Food selectByFoodName(String name) {
		String sqlCommand = "SELECT * FROM food WHERE foodname = ? ;";
		try(
				Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
		){
			preparedStatement.setString(1, name);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<Food> foods = this.resultSetToObjects(resultSet);
			
			if(foods == null || foods.size() <= 0 ) {
				throw new Exception("There are no record found.");
			}
			
			if(foods.size() != 1) {
				throw new Exception("Unknown error!!!\nToo many records found.\nIt should found only one record");
			}
				
			return foods.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Food selectByFoodId(int foodId) {
		String sqlCommand = "SELECT * FROM food WHERE foodID = ? ;";
		try(
				Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
		){
			preparedStatement.setInt(1, foodId);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<Food> foods = this.resultSetToObjects(resultSet);
			
			if(foods == null || foods.size() <= 0 ) {
				throw new Exception("There are no record found.");
			}
			
			if(foods.size() != 1) {
				throw new Exception("Unknown error!!!\nToo many records found.\nIt should found only one record");
			}
				
			return foods.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public ArrayList<Food> listAvailableFoods() {
		String sqlCommand = "SELECT * FROM food ;";
		try(
				Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
		){
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSetToObjects(resultSet);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int insert(Food food) {
		String sqlCommand = "INSERT INTO food "
				+ " (foodname,fat,carbon,protein,fiber,sugar,sodium,calories) "
				+ " VALUES "
				+ "(?,?,?,?,?,?,?,?);";
		try(
				Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
		){
			preparedStatement.setString(1, food.getFoodName());
			preparedStatement.setDouble(2, food.getFat());
			preparedStatement.setDouble(3, food.getCarbon());
			preparedStatement.setDouble(4, food.getProtein());
			preparedStatement.setDouble(5, food.getFiber());
			preparedStatement.setDouble(6, food.getSugar());
			preparedStatement.setDouble(7, food.getSodium());
			preparedStatement.setDouble(8, food.getCalories());
			
			return preparedStatement.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int delete(Food food) {
		String sqlCommand = "DELETE FROM food "
				+ " WHERE foodId = ? ;";
		try(
				Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
		){
			preparedStatement.setInt(1, food.getFoodId());
			
			return preparedStatement.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
