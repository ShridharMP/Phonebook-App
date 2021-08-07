package com.fis.me.practice;

/**
 * This class is an entry point of execution for personalExpenseManager  Application (PEMApp).
 * @author Shridhar Patil
 *
 */
public class StartApp {
	/**
	 * this method is creating object of <code>PEMService</code> and 
	 * showapp by calling the showMenu() method.
	 * @param args
	 */
	public static void main(String[] args) {
		PEMService service = new PEMService();
		service.showMenu();
	}
}
