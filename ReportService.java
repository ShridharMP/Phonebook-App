package com.fis.me.practice;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
/**
 * This <code>ReportService</code> is used to generate the Reports.based on the Different Criteria.
 * Ex. based on Month, Year,category etc...,
 * @author Shridhar Patil
 *
 */
public class ReportService {
/**
 * Declares the Singletone Repository reference 
 */
	Repository repo = Repository.getrepository();
/**
 * This method is used to calculate the Expense based on the Month.
 * Means calculates the Expense of every Month of Particular Year
 * @return return final result as Map
 */
	public Map<String, Float> calculateMonthlyTotal() {
		Map<String, Float> m = new TreeMap<>();

		for (Expense exp : repo.expList) {
			Date expDate = exp.getDate();

			String yearMonth = DateUtil.getYearAndMonth(expDate);
			if (m.containsKey(yearMonth)) {

			Float total = m.get(yearMonth); //When expense already Present for a month
				total = total + exp.getAmount();
				m.put(yearMonth, total);// new Total,replace old total
			} else {
				/**
				 *  This month is not yet Added
				 */
				m.put(yearMonth, exp.getAmount());
			}
		}

		return m;// return final result as Map
	}
	/**
	 * This method is used to calculate the Expense based on the Year.
	 * Means calculates the Expense of every Particular Year
	 * and also TotalExpense of the all the Years
	 * @return return final result as Map
	 */
	public Map<Integer, Float> calculateYearlyTotal() {
		Map<Integer, Float> m = new TreeMap<>();

		for (Expense exp : repo.expList) {
			Date expDate = exp.getDate();

			Integer year = DateUtil.getYear(expDate);
			if (m.containsKey(year)) {

				// When expense already Present for a year
				Float total = m.get(year);
				total = total + exp.getAmount();
				m.put(year, total);// new Total,replace old total
			} else {
				// This year is not yet Added
				m.put(year, exp.getAmount());
			}
		}

		return m;// return final result as Map
	}
	/**
	 * This method is used to calculate the Expense based on the Categoty.
	 * Means calculates the Expense of every Category.
	 * @return return final result as Map
	 */
	public Map<String, Float> calculateCategorizedTotal() {
		Map<String, Float> m = new TreeMap<>();

		for (Expense exp : repo.expList) {
			Long catId= exp.getCategoryId();
			String CategoryName= this.getCategoryNameByID(catId);
			if (m.containsKey(CategoryName)) {

				// When expense already Present for a year
				Float total = m.get(CategoryName);
				total = total + exp.getAmount();
				m.put(CategoryName, total);// new Total,replace old total
			} else {
				// This year is not yet Added
				m.put(CategoryName, exp.getAmount());
			}
		}
		return m;// return final result as Map
	}
	/**
	 * This method is used to get the Category name by using the generate CategoryId
	 * @param categoryID takes the generatd long value.
	 * @return the name of Category.
	 */
	public String getCategoryNameByID(Long categoryID) {
		for (Category c : repo.catList) {
			if (c.getCategoryId().equals(categoryID)) {
				return c.getName();
			}
		}
		return null;// no such category Id present
	}
}
