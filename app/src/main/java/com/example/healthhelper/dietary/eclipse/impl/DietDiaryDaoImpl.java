package com.example.healthhelper.dietary.eclipse.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import web.dietdiary.vo.DietDiary;
import web.dietdiary.vo.Nutrition;

public class DietDiaryDaoImpl implements DietDiaryDao {

	private DataSource dataSource;

	public DietDiaryDaoImpl(DataSource dataSource) throws NamingException {
		this.dataSource = dataSource;
		if (this.dataSource == null) {
			this.dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/iHealth");
		}
	}

	public Connection getConnection() throws SQLException {
		return this.dataSource.getConnection();
	}

	private ArrayList<DietDiary> resultSetToObjects(ResultSet resultSet) throws SQLException {
		ArrayList<DietDiary> dietDiaries = new ArrayList<DietDiary>();
		while (resultSet.next()) {

			int diaryId = resultSet.getInt("diaryID");
			int userId = resultSet.getInt("userID");
			Date createDate = resultSet.getDate("createdate");
			Time createTime = resultSet.getTime("createtime");
			Double totalFat = resultSet.getDouble("totalfat");
			Double totalCarbon = resultSet.getDouble("totalcarbon");
			Double totalProtein = resultSet.getDouble("totalprotein");
			Double totalFiber = resultSet.getDouble("totalfiber");
			Double totalSugar = resultSet.getDouble("totalsugar");
			Double totalSodium = resultSet.getDouble("totalsodium");
			Double totalCalories = resultSet.getDouble("totalcalories");

			DietDiary dietDiary = new DietDiary();

			dietDiary.setDiaryId(diaryId);
			dietDiary.setUserId(userId);
			dietDiary.setCreateDate(createDate);
			dietDiary.setCreateTime(createTime);
			dietDiary.setTotalFat(totalFat);
			dietDiary.setTotalCarbon(totalCarbon);
			dietDiary.setTotalProtein(totalProtein);
			dietDiary.setTotalFiber(totalFiber);
			dietDiary.setTotalSugar(totalSugar);
			dietDiary.setTotalSodium(totalSodium);
			dietDiary.setTotalCalories(totalCalories);

			dietDiaries.add(dietDiary);
		}

		return dietDiaries;
	}

	@Override
	public String insert(DietDiary dietDiary) {
		String sqlCommand = "INSERT INTO fooddiary "
				+ "(diaryID,userID,createdate,createtime,totalFat,totalCarbon,totalFiber,totalSugar,totalSodium,totalProtein,totalCalories) "
				+ " VALUES " + "(?,?,?,?,?,?,?,?,?,?,?)";

		try (Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);) {
			preparedStatement.setInt(1, dietDiary.getDiaryId());
			preparedStatement.setInt(2, dietDiary.getUserId());
			preparedStatement.setDate(3, dietDiary.getCreateDate());
			preparedStatement.setTime(4, dietDiary.getCreateTime());
			preparedStatement.setDouble(5, dietDiary.getTotalFat());
			preparedStatement.setDouble(6, dietDiary.getTotalCarbon());
			preparedStatement.setDouble(7, dietDiary.getTotalFiber());
			preparedStatement.setDouble(8, dietDiary.getTotalSugar());
			preparedStatement.setDouble(9, dietDiary.getTotalSodium());
			preparedStatement.setDouble(10, dietDiary.getTotalProtein());
			preparedStatement.setDouble(11, dietDiary.getTotalCalories());

			int affectedRow = preparedStatement.executeUpdate();

			if (affectedRow != 1) {
				throw new Exception("Unknown error during execution of sql statement.");
			}
			return "";

		} catch (Exception e) {
			e.printStackTrace();
			return e.getStackTrace().toString();
		}
	}

	@Override
	public ArrayList<DietDiary> selectByTime(int userId, Time time) {
		String sqlCommand = "SELECT * FROM fooddiary WHERE" + " userId = ? AND createtime = ? ;";
		try (Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);) {
			preparedStatement.setInt(1, userId);
			preparedStatement.setTime(2, time);

			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSetToObjects(resultSet);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public ArrayList<DietDiary> selectByDate(int userId, Date date) {
		String sqlCommand = "SELECT * FROM fooddiary WHERE" + " userId = ? AND createdate = ? ;";
		try (Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);) {

			preparedStatement.setInt(1, userId);
			preparedStatement.setDate(2, date);

			ResultSet resultSet = preparedStatement.executeQuery();

			ArrayList<DietDiary> dietDiaries = resultSetToObjects(resultSet);

			if (dietDiaries == null) {
				throw new Exception("Unknown error during execution of sql statement.");
			}

			return dietDiaries;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<DietDiary> selectByDateAndTime(int userId, Date date, Time time) {
		String sqlCommand = "SELECT * FROM fooddiary WHERE" + " userId = ? AND createdate = ? AND createtime = ? ;";
		try (Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);) {

			preparedStatement.setInt(1, userId);
			preparedStatement.setDate(2, date);
			preparedStatement.setTime(3, time);

			ResultSet resultSet = preparedStatement.executeQuery();

			ArrayList<DietDiary> dietDiaries = resultSetToObjects(resultSet);

			if (dietDiaries == null) {
				throw new Exception("Unknown error during execution of sql statement.");
			}

			return dietDiaries;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public DietDiary selectByDiaryIdAndDate(int diaryId, Date date) {
		String sqlCommand = "SELECT * FROM fooddiary WHERE" + " diaryID = ? AND createdate = ? ;";
		try (Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);) {

			preparedStatement.setInt(1, diaryId);
			preparedStatement.setDate(2, date);

			ResultSet resultSet = preparedStatement.executeQuery();

			ArrayList<DietDiary> dietDiaries = resultSetToObjects(resultSet);

			if (dietDiaries == null || dietDiaries.size() != 1) {
				throw new Exception("Unknown error during execution of sql statement.");
			}
			return dietDiaries.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int updateByDiaryId(DietDiary dietDiary) {
		String sqlCommand = "UPDATE fooddiary SET " + "totalfat = ? , " + "totalcarbon = ? , " + "totalprotein = ? , "
				+ "totalfiber = ? , " + "totalsugar = ? ," + "totalsodium = ? " + "WHERE" + " diaryID = ? AND "
				+ "createdate = ? ;";

		try (Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);) {

			preparedStatement.setDouble(1, dietDiary.getTotalFat());
			preparedStatement.setDouble(2, dietDiary.getTotalCarbon());
			preparedStatement.setDouble(3, dietDiary.getTotalProtein());
			preparedStatement.setDouble(4, dietDiary.getTotalFiber());
			preparedStatement.setDouble(5, dietDiary.getTotalSugar());
			preparedStatement.setDouble(6, dietDiary.getTotalSodium());

			preparedStatement.setInt(7, dietDiary.getDiaryId());
			preparedStatement.setDate(8, dietDiary.getCreateDate());

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
}
