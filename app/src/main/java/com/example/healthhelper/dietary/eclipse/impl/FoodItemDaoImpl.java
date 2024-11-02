package com.example.healthhelper.dietary.eclipse.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import web.dietdiary.vo.FoodItem;

public class FoodItemDaoImpl implements FoodItemDao {

	private DataSource dataSource;
	
	public FoodItemDaoImpl(DataSource dataSource) throws NamingException {
		this.dataSource = dataSource;
		if (this.dataSource == null) {
			this.dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/iHealth");
		}
	}
	
	public Connection getConnection() throws SQLException {
		return this.dataSource.getConnection();
	}
	
	private ArrayList<FoodItem> resultSetToObjects(ResultSet resultSet) throws SQLException{
		ArrayList<FoodItem> foodItems = new ArrayList<FoodItem>();
		while(resultSet.next()) {
			
			int diaryId = resultSet.getInt("diaryID");
			int foodId = resultSet.getInt("foodID");
			Double grams = resultSet.getDouble("grams");
			
			FoodItem foodItem = new FoodItem();
			
			foodItem.setDiaryId(diaryId);
			foodItem.setFoodId(foodId);
			foodItem.setGrams(grams);
			
			foodItems.add(foodItem);
		}	
		return foodItems;
	}

	@Override
	public int insert(FoodItem foodItem) {
		String sqlCommand = "INSERT INTO fooditem "
				+ " (diaryId,foodId,grams)"
				+ " VALUES "
				+ "(?,?,?);";
		try(
				Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
		){
			preparedStatement.setInt(1,foodItem.getDiaryId());
			preparedStatement.setInt(2,foodItem.getFoodId());
			preparedStatement.setDouble(3,foodItem.getGrams());
			
			return preparedStatement.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	@Override
	public FoodItem select(int foodId) {
		String sqlCommand = "SELECT * FROM fooditem WHERE foodID = ? ;";
		try(
				Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
		){
				preparedStatement.setInt(1,foodId);
				ResultSet resultSet = preparedStatement.executeQuery();
				ArrayList<FoodItem> foodItems = this.resultSetToObjects(resultSet);
				System.out.println("foodItems:"+foodItems);
				if(foodItems == null || foodItems.isEmpty()) {
					throw new Exception("Unknown error during execution of sql statement.");
				}
				return foodItems.get(0);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public int delete(FoodItem foodItem) {
		String sqlCommand = "DELETE FROM fooditem WHERE foodId = ? ;";
		try(
				Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
		){
			preparedStatement.setInt(1,foodItem.getFoodId());
			return preparedStatement.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int update(FoodItem foodItem) {
		String sqlCommand = "UPDATE fooditem SET diaryId = ?, grams = ? WHERE foodId = ? ;";
		try(
				Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
		){
			preparedStatement.setInt(1, foodItem.getDiaryId());
			preparedStatement.setDouble(2, foodItem.getGrams());
			preparedStatement.setInt(3,foodItem.getFoodId());
			return preparedStatement.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

}
