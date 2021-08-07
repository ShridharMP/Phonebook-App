package com.fis.me.practice;

import java.util.ArrayList;
import java.util.List;
/**
 * This class used to create the singletone Object.
 * and used as Database/Repository
 * @author Shridhar Patil
 *
 */
public class Repository {
	/**
	 * This <code>Expense</code> is used to add the expense in <code>List</code> array.
	 */
	public List<Expense> expList = new ArrayList<>();
	/**
	 * This <code>Category</code> is used to add the Category in <code>List</code> array.
	 */
	public List<Category> catList = new ArrayList<>();
	/**
	 * Declares the Static Repository reference.
	 */
	private static Repository repository;
/**
 * This is no-arg constructor
 */
	public Repository() {
	}
/**
 * This logic is used to create singletone object
 * @return returns the reference of the <code>Repository</code>
 */
	public static Repository getrepository()

	{
		if (repository == null) {
			repository = new Repository();
		}
		return repository;
	}
}
