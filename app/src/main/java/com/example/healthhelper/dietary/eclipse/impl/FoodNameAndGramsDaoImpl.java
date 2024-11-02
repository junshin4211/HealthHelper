package com.example.healthhelper.dietary.eclipse.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import web.dietdiary.vo.FoodNameAndGrams;

public class FoodNameAndGramsDaoImpl implements FoodNameAndGramsDao{

	private DataSource dataSource;

	public FoodNameAndGramsDaoImpl(DataSource dataSource) throws NamingException {
		this.dataSource = dataSource;
		if (this.dataSource == null) {
			this.dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/iHealth");
		}
	}
	
	public Connection getConnection() throws SQLException {
		return this.dataSource.getConnection();
	}
	
	private ArrayList<FoodNameAndGrams> resultSetToObjects(ResultSet resultSet) throws SQLException{
		ArrayList<FoodNameAndGrams> foodNameAndGramses = new ArrayList<FoodNameAndGrams>();
		while(resultSet.next()) {
			
			String foodName = resultSet.getString("foodName");
			Double grams = resultSet.getDouble("gram");
			
			FoodNameAndGrams foodNameAndGrams = new FoodNameAndGrams();
			
			foodNameAndGrams.setFoodName(foodName);
			foodNameAndGrams.setGrams(grams);
			
			foodNameAndGramses.add(foodNameAndGrams);
		}	
		return foodNameAndGramses;
	}

	
	@Override
	public ArrayList<FoodNameAndGrams> listAvailableFoodsNameAndGrams() {
		String sqlCommand = "SELECT foodname,gram FROM food;";
		try(
				Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
		){
			ResultSet resultSet = preparedStatement.executeQuery();
			return this.resultSetToObjects(resultSet);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
