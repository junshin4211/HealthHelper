package com.example.healthhelper.dietary.eclipse.datetime;

import java.sql.Timestamp;

public class DateTimeHandlerImpl implements DateTimeHandler {

	@Override
	public java.sql.Date getSqlDate(Timestamp timestamp) {
		java.sql.Date newDate = java.sql.Date.valueOf(timestamp.toLocalDateTime().toLocalDate());
		return newDate;
	}

	@Override
	public java.sql.Date getSqlDate(java.util.Date timestamp) {
		java.sql.Date newDate = new java.sql.Date(timestamp.getDate());
		return newDate;
	}
	
	@Override
	public java.sql.Time getSqlTime(Timestamp timestamp) {
		java.sql.Time newTime = java.sql.Time.valueOf(timestamp.toLocalDateTime().toLocalTime());
		return newTime;
	}
	
	@Override
	public java.sql.Time getSqlTime(java.util.Date timestamp) {
		java.sql.Time newTime = new java.sql.Time(timestamp.getTime());
		return newTime;
	}

}
