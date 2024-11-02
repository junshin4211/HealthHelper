package com.example.healthhelper.dietary.eclipse.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingException;
import javax.sql.DataSource;

import web.dietdiary.vo.DietDiary;
import web.dietdiary.vo.MealTimeRangeCategory;

public class MealTimeRangeCategoryDaoImpl implements MealTimeRangeCategoryDao{

	private DataSource dataSource;
	
	public MealTimeRangeCategoryDaoImpl(DataSource dataSource) throws NamingException {
		this.dataSource = dataSource;
		if (this.dataSource == null) {
			this.dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/iHealth");
		}
	}
	
	public Connection getConnection() throws SQLException {
		return this.dataSource.getConnection();
	}
	
	private ArrayList<MealTimeRangeCategory> resultSetToObjects(ResultSet resultSet) throws SQLException {
		ArrayList<MealTimeRangeCategory> mealTimeRangeCategories = new ArrayList<MealTimeRangeCategory>();
		while (resultSet.next()) {
			MealTimeRangeCategory mealTimeRangeCategory = new MealTimeRangeCategory();

			int userId = resultSet.getInt("userID");
			Time breakfastStartTime = resultSet.getTime("breakfastStartTime");
			Time breakfastEndTime = resultSet.getTime("breakfastEndTime");
			Time lunchStartTime = resultSet.getTime("lunchStartTime");
			Time lunchEndTime = resultSet.getTime("lunchEndTime");
			Time dinnerStartTime = resultSet.getTime("dinnerStartTime");
			Time dinnerEndTime = resultSet.getTime("dinnerEndTime");
			Time supperStartTime = resultSet.getTime("supperStartTime");
			Time supperEndTime = resultSet.getTime("supperEndTime");
			
			mealTimeRangeCategory.setUserId(userId);
			mealTimeRangeCategory.setBreakfastStartTime(breakfastStartTime);
			mealTimeRangeCategory.setBreakfastEndTime(breakfastEndTime);
			mealTimeRangeCategory.setLunchStartTime(lunchStartTime);
			mealTimeRangeCategory.setLunchEndTime(lunchEndTime);
			mealTimeRangeCategory.setDinnerStartTime(dinnerStartTime);
			mealTimeRangeCategory.setDinnerEndTime(dinnerEndTime);
			mealTimeRangeCategory.setSupperStartTime(supperStartTime);
			mealTimeRangeCategory.setSupperEndTime(supperEndTime);
			
			mealTimeRangeCategories.add(mealTimeRangeCategory);
		}
		
		return mealTimeRangeCategories;
	}
	
	@Override
	public int insert(MealTimeRangeCategory mealTimeRangeCategory) {
		String sqlCommand = "INSERT INTO mealTimeRangeCategory "
				+ "(userId,breakfastStartTime,breakfastEndTime,lunchStartTime,lunchEndTime,dinnerStartTime,dinnerEndTime,supperStartTime,supperEndTime)"
				+ "VALUES "
				+ "(?,?,?,?,?,?,?,?,?);";

		try (Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);) {

			preparedStatement.setInt(1, mealTimeRangeCategory.getUserId());
			preparedStatement.setTime(2, mealTimeRangeCategory.getBreakfastStartTime());
			preparedStatement.setTime(3, mealTimeRangeCategory.getBreakfastEndTime());
			preparedStatement.setTime(4, mealTimeRangeCategory.getLunchStartTime());
			preparedStatement.setTime(5, mealTimeRangeCategory.getLunchEndTime());
			preparedStatement.setTime(6, mealTimeRangeCategory.getLunchEndTime());
			preparedStatement.setTime(7, mealTimeRangeCategory.getDinnerEndTime());
			preparedStatement.setTime(8, mealTimeRangeCategory.getSupperStartTime());
			preparedStatement.setTime(9, mealTimeRangeCategory.getSupperEndTime());

			System.out.println("sqlCommand:"+sqlCommand);
			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows != 1) {
				throw new Exception("Unknown error during execution of sql statement.");
			}

			return affectedRows;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	@Override
	public ArrayList<MealTimeRangeCategory> selectByUserId(int userId) {
		String sqlCommand = "SELECT * FROM mealTimeRangeCategory "
				+ "WHERE userID = ? ;";

		try (Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);) {
			preparedStatement.setInt(1,userId);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<MealTimeRangeCategory> mealTimeRangeCategories = this.resultSetToObjects(resultSet);
			if(mealTimeRangeCategories == null) {
				throw new Exception("Unknown error!!!");
			}
			if(mealTimeRangeCategories.isEmpty()) {
				return mealTimeRangeCategories;
			}
			if(mealTimeRangeCategories.size()!=1) {
				throw new Exception("Error!!! Duplicated record!!!");
			}
			return mealTimeRangeCategories;
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public int update(MealTimeRangeCategory mealTimeRangeCategory) {
		String sqlCommand = "UPDATE mealTimeRangeCategory SET "
				+ "breakfastStartTime = ? ,"
				+ "breakfastEndTime = ? ,"
				+ "lunchStartTime = ? ,"
				+ "lunchEndTime = ? ,"
				+ "dinnerStartTime = ? ,"
				+ "dinnerEndTime = ? ,"
				+ "supperStartTime = ? ,"
				+ "supperEndTime = ? "
				+ "WHERE "
				+ "userID = ? ;";

		try (Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);) {
			
			Time breakfastStartTime = mealTimeRangeCategory.getBreakfastEndTime();
			Time breakfastEndTime = mealTimeRangeCategory.getBreakfastEndTime();
			Time lunchStartTime = mealTimeRangeCategory.getLunchStartTime();
			Time lunchEndTime = mealTimeRangeCategory.getLunchEndTime();
			Time dinnerStartTime = mealTimeRangeCategory.getDinnerStartTime();
			Time dinnerEndTime = mealTimeRangeCategory.getDinnerEndTime();
			Time supperStartTime = mealTimeRangeCategory.getSupperStartTime();
			Time supperEndTime = mealTimeRangeCategory.getSupperEndTime();
			int userId = mealTimeRangeCategory.getUserId();
			
			preparedStatement.setTime(1,breakfastStartTime);
			preparedStatement.setTime(2,breakfastEndTime);
			preparedStatement.setTime(3,lunchStartTime);
			preparedStatement.setTime(4,lunchEndTime);
			preparedStatement.setTime(5,dinnerStartTime);
			preparedStatement.setTime(6,dinnerEndTime);
			preparedStatement.setTime(7,supperStartTime);
			preparedStatement.setTime(8,supperEndTime);
			preparedStatement.setInt(9,userId);
		
			int affectedRows = preparedStatement.executeUpdate();
			if(affectedRows != 1) {
				throw new Exception("Unknown error!!!");
			}
			return affectedRows;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}