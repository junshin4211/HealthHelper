package com.example.healthhelper.dietary.eclipse.datetime;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public interface DateTimeHandler {
	Date getSqlDate(Timestamp timestamp);
	Time getSqlTime(Timestamp timestamp);
	Date getSqlDate(java.util.Date timestamp);
	Time getSqlTime(java.util.Date timestamp);
}
