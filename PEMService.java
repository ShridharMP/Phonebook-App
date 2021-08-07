package com.fis.me.practice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This class contains the most of the operation related to PEM Application.
 * <p>
 * this class prepares a menu and various methods are present to handle the user
 * action the class make use of <code>Repository</code> to store the data. and
 * also using <code>ReportService</code> to generate different required reports
 * </p>
 * 
 * @author Shridhar Patil
 *
 */
public class PEMService {
	/**
	 * declare a reference of a repository by calling a static method which returns
	 * a singletone repository reference object
	 */
	Repository repo = Repository.getrepository();
	/**
	 * Declare a reference of Reportsrvice to call different method to calculate
	 * repots based on Month,year,Category etc..
	 */
	ReportService reportService = new ReportService();
	/**
	 * Declare a <code>Scanner</code> Object to take standard input from the
	 * keyBoard.
	 */
	private Scanner in = new Scanner(System.in);
	/**
	 * This variable is to store the value of menu-Choice
	 */
	private int choice;

	/**
	 * Call this constructor to create <code>PEMService</code> object with default
	 * details.
	 */
	public PEMService() {
		/**
		 * Delete this method call after testing is completed prepareSampleData();
		 */
		restoredetailsFromtheFile();
	}

	boolean value = true;

	/**
	 * This method prepares a PEMApp menu using the Switch case and while loop ,also
	 * ask for User choice
	 */
	public void showMenu() {
		while (value) {
			printmenu();
			switch (choice) {
			case 1:
				onAddCategory();
				pressAnykeyToContnue();
				break;

			case 2:
				onCategoryList();
				pressAnykeyToContnue();
				break;
			case 3:
				onAddExpensentry();
				pressAnykeyToContnue();
				break;
			case 4:
				onAddExpensList();
				pressAnykeyToContnue();
				break;
			case 5:
				onMonthlyExpenseList();
				pressAnykeyToContnue();
				break;

			case 6:
				onYearlyExpenseList();
				pressAnykeyToContnue();
				break;
			case 7:
				onCategorizedExpenseList();
				pressAnykeyToContnue();
				break;
			case 0:
				onExit();
				break;
			}
		}
	}

	/**
	 * This method prints a menu (CUI/CLI menu)
	 */
	public void printmenu() {
		System.out.println("--------------Print Menu--------------------");
		System.out.println("1. Add Category");
		System.out.println("2. Category List");
		System.out.println("3. Expense Entry");
		System.out.println("4. Expense List");
		System.out.println("5. Monthly Expense List");
		System.out.println("6. Yearly Expense List");
		System.out.println("7. Categorizd Expense List");
		System.out.println("0. Exit");
		System.out.println("----------------------------------------------");
		System.out.print("Enter your Choice: ");
		choice = in.nextInt();
	}

	/**
	 * This method is used to take the expense category name as input to add new
	 * category in the system.
	 */
	public void onAddCategory() {
		in.nextLine();// new line char is used herewhichis already present in stream and it's not in
						// use for now
		System.out.println("Adding Category....");
		String catName = in.nextLine();
		Category cat = new Category(catName);
		repo.catList.add(cat);
		System.out.println("Catgory added successfully.......");
	}

	/**
	 * call this method to to print existing category list.
	 */
	private void onCategoryList() {
		System.out.println(" Category List.....");
		List<Category> cList = repo.catList;
		for (int i = 0; i < cList.size(); i++) {
			Category c = cList.get(i);
			System.out.println((i + 1) + " . " + c.getName() + " ," + c.getCategoryId());
		}
	}

	/**
	 * call this method to enter the expense detail.The entered details will be
	 * added to the repository.
	 */
	private void onAddExpensentry() {
		System.out.println("Enter Details for  Expense ....");
		onCategoryList();
		System.out.println("Choose Category: ");
		int catChoice = in.nextInt();
		Category selectedCat = repo.catList.get(catChoice - 1);
		System.out.println("Enter the Amount");
		float amount = in.nextFloat();
		System.out.println("Enter the Remark: ");
		in.nextLine();
		String remark = in.nextLine();
		/*
		 * Date can be taken from the user input
		 */
		System.out.println("Enter date (DD/MM/YYYY)");
		String dateAsString = in.nextLine();
		Date date = DateUtil.StringToDate(dateAsString);

		// Add ExpenseDeatil in Expense object
		Expense expense = new Expense();
		expense.setCategoryId(selectedCat.getCategoryId());
		expense.setAmount(amount);
		expense.setRemark(remark);
		expense.setDate(date);
		// Store Expense Object in Repository
		repo.expList.add(expense);
		System.out.println("Success: Expense Added ");
	}

	/**
	 * this method prints the all entered expenses.
	 */

	private void onAddExpensList() {
		System.out.println("Adding the Expense List...");
		List<Expense> expenseList = repo.expList;
		for (int i = 0; i < expenseList.size(); i++) {
			Expense exp = expenseList.get(i);
			String dateString = DateUtil.dateToString(exp.getDate());
			String catName = reportService.getCategoryNameByID(exp.getCategoryId());
			System.out.println((i + 1) + " ." + catName + ", " + exp.getAmount() + ", " + "," + exp.getRemark() + ","
					+ dateString);
		}
	}

	/**
	 * This method is called from menu to prepare monthly-wise expense total. Its
	 * using the <code>ReportService</code> to calculate report The returned result
	 * will be printed by this method. Means this method invokes a call to generat
	 * report then result is printed by this method
	 */
	private void onMonthlyExpenseList() {
		System.out.println("Monthly Total Expense ......");
		Map<String, Float> resultMap = reportService.calculateMonthlyTotal();

		Set<String> keys = resultMap.keySet();
		for (String yearMonth : keys) {

			String arr[] = yearMonth.split(",");
			String year = arr[0];
			Integer monthNo = new Integer(arr[1]);
			String monthName = DateUtil.getmonthName(monthNo);
			System.out.println(year + "," + monthName + " : " + resultMap.get(yearMonth));
		}
	}

	/**
	 * This method is called from menu to prepare Yearly-wise expense total. Its
	 * using the <code>ReportService</code> to calculate report The returned result
	 * will be printed by this method. Means this method invokes a call to generat
	 * report then result is printed by this method
	 */
	private void onYearlyExpenseList() {
		System.out.println("Adding yearly Expense ......");
		Map<Integer, Float> resultMap = reportService.calculateYearlyTotal();

		Set<Integer> years = resultMap.keySet();
		Float total = 0.0F;
		for (Integer year : years) {
			Float exp = resultMap.get(year);
			total = total + exp;
			System.out.println(year + ": " + exp);
		}
		System.out.println("-------------------------------------------");
		System.out.println("Total Expense:" + total);

	}

	/**
	 * This method is called from menu to prepare Category-wise expense total. Its
	 * using the <code>ReportService</code> to calculate report The returned result
	 * will be printed by this method. Means this method invokes a call to generat
	 * report then result is printed by this method
	 */
	private void onCategorizedExpenseList() {
		System.out.println("Adding category Expense ......");
		Map<String, Float> resultMap = reportService.calculateCategorizedTotal();
		Set<String> keys = resultMap.keySet();
		Float netTotal = 0.0F;
		for (String category : keys) {
			Float catgorytotal = resultMap.get(category);
			netTotal = netTotal + catgorytotal;
			System.out.println(category + ":" + resultMap.get(category));
		}
		System.out.println("------------------------------");
		System.out.println("NetTotal :" + netTotal);
	}

	/**
	 * This method stops the JVM.means it's closing the PEM Application.
	 */
	private void onExit() {
		storetheDetailsInRepository();
		System.exit(0);
	}

	/**
	 * This method is called to hold output screen after processing the required
	 * task and wait for any char input to continue to the menu.
	 */
	public void pressAnykeyToContnue() {
		System.out.println("press Any key to continue");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is prepares sample data for testing purpose. it should be report
	 * once app is tested OK.
	 */
	public void prepareSampleData() {
		Category catparty = new Category("Party");
		delay();
		Category catShoping = new Category("Shoping");
		delay();
		Category catGift = new Category("Gift");

		repo.catList.add(catparty);
		repo.catList.add(catShoping);
		repo.catList.add(catGift);

		// Jan 2018
		Expense e1 = new Expense(catparty.getCategoryId(), 1000.0F, DateUtil.StringToDate("1/1/2018"), "N/A");
		delay();
		Expense e2 = new Expense(catparty.getCategoryId(), 2000.0F, DateUtil.StringToDate("2/1/2018"), "N/A");
		delay();

		// Feb 2018
		Expense e3 = new Expense(catShoping.getCategoryId(), 2000.0F, DateUtil.StringToDate("8/2/2018"), "N/A");
		delay();
		Expense e4 = new Expense(catparty.getCategoryId(), 5200.0F, DateUtil.StringToDate("2/2/2018"), "N/A");
		delay();

		// Dec 2018
		Expense e5 = new Expense(catGift.getCategoryId(), 4325.0F, DateUtil.StringToDate("2/12/2018"), "N/A");
		delay();

		// Jan 2019
		Expense e6 = new Expense(catShoping.getCategoryId(), 8000.0F, DateUtil.StringToDate("2/1/2019"), "N/A");
		delay();

		repo.expList.add(e1);
		repo.expList.add(e2);
		repo.expList.add(e3);
		repo.expList.add(e4);
		repo.expList.add(e5);
		repo.expList.add(e6);

	}

	/**
	 * The method sleep a thread for 10ms.
	 */
	private void delay() {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void storetheDetailsInRepository() {
		serilize("expenseList.ser", repo.expList);
		serilize("categoryList.ser", repo.catList);
	}

	public void serilize(String fileName, Object obj) {
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);// Store the Expense List in file
			// use finally block to close the File Stream
			oos.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void restoredetailsFromtheFile()
	{
		@SuppressWarnings("unchecked")
		List<Expense> expList=(List<Expense>) deserilize("expenseList.ser");
		@SuppressWarnings("unchecked")
		List<Category>catList=(List<Category>) deserilize("categoryList.ser");
		if(expList!=null)
		{
			//set Existing expenses in the repository
			repo.expList=expList;
		}
		if(catList!=null)
		{
			repo.catList=catList;
		}
	}
	public Object deserilize(String fileName) {
		
		try {
			FileInputStream fis=new FileInputStream(fileName);
			ObjectInputStream ois=new ObjectInputStream(fis);
			Object obj=ois.readObject();
			return obj;
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("No existing file found");
			return null;
		}
		
	
		
	}

}
