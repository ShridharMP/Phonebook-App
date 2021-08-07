package com.fis.me.practice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * This class contains the static method to handle dates.
 * @author Shridhar Patil
 *
 */
public class DateUtil {
	public static final String[] MONTHS = { "January", "February", "March", "April", "May", "June", "July",
			"August", "Septeber", "October", "November", "December" };
	
	/**
	 * This method converts String-date into Date object.
	 * @param dateAsString String formatted date (ex. 15/01/2019 :DD/MM/YYYY)
	 * @return returns a date Object for input date-String
	 */
	public static Date StringToDate(String dateAsString) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			return df.parse(dateAsString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
/**
 * This method converts the Date-date object to String-date object
 * @param date Date object converted to be String.
 * @return String date in DD/MM/YYYY format.
 */
	public static String dateToString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(date);
	}
/**
 * The method returns Year and Month from given date in Year,Month-No format.
 * Ex 2018/02 ;2019/01 so on ......
 * @param date Year and month will be extracted for this Date
 * @return return year and month for input date.
 */
	public static String getYearAndMonth(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy,MM");//Ex 2018/02 ;2019/01 so on

		return df.format(date);

	}
	
	/**
	 * Returns the year for input date.
	 * @param date Year is extracted from the date.
	 * @return  value of the Year.
	 * Ex.2014,2018,2019
	 */
	public static Integer getYear(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy");//Ex 2018/02/10

		return new Integer(df.format(date));

	}
	/**
	 * this method returns Month Name for given Month Number 
	 * 01:January ,02:February...,12:December.
	 * @param monnthNo Month number between the 1 to 12
	 * @return returns the Month name for input Month Number.
	 */
	public static String getmonthName(Integer monnthNo)
	{
		return MONTHS[monnthNo-1];
	}
}
