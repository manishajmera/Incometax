package com.girnarsoft.incometax.controller;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.girnarsoft.incometax.services.IncomeTaxServices;

@Component
/**
 * after successful login user can come into this class and have all the options
 * like edit profile,generate itr ,input his salary breakup
 * 
 * @author manish
 *
 */
public class IncomeTaxMenu {

	
	/*
	 * creating an instance of income tax service to check whether user enters a
	 * valid input before reflecting into database
	 */
	@Autowired
	private IncomeTaxServices incomeTaxServices;
	@Autowired
	// checker instance to check that user enters a valid input
	private Validator validater;
	private final Scanner SC = new Scanner(System.in);
	// id of user
	private int id;
	// password of user
	private String password;

	protected void menu() {
		System.out.println(ConstantMsg.INPUT_ID);
		id = validater.validateInt();
		// check that id exit in database or not
		try {
			while (!incomeTaxServices.containsId(id)) {
				System.err.println(ConstantMsg.NOT_CONTAINS);
				id = validater.validateInt();
				if (id == -1)
					return;

			}
		} catch (DataAccessException e) {
			System.out.println(ConstantMsg.DATABASE_ERROR);
			return;
		}
		System.out.println("Enter password");
		password = SC.nextLine();
		validater.checkLength("password", password);
		// check that password or id is correct
		try {
			while (!incomeTaxServices.findAccountId(id, password)) {
				System.err.println(ConstantMsg.ERROR_PASSWORD);
				password = SC.nextLine();
				if (password.equals(-1))
					return;

			}
		} catch (DataAccessException e) {
			System.out.println(ConstantMsg.DATABASE_ERROR);
			return;
		}

		int exitFromLoop = 1;
		// starting of user menu loop
		while (exitFromLoop == 1) {
			// user input choice variable
			int choice;
			System.out.println(ConstantMsg.USER_MENU);
			choice = validater.validateInt();
			switch (choice) {
			// open editing menu of user
			case 1:
				editingDetails(id);
				break;

			// removing user from database and signed out from database
			case 2:
				try {
					incomeTaxServices.remove(id);
					System.out.println(ConstantMsg.SIGN_OUT);
					exitFromLoop = 0;
					break;
				} catch (DataAccessException e) {
					System.out.println(ConstantMsg.DATABASE_ERROR);
					return;
				}
				// salary breakup user input
			case 3:
				// list of map objects to get all the salary breakups of employee from database
				try {
					List<Map<String, Object>> list1 = incomeTaxServices.generateItr(id);
					if (list1.isEmpty()) {
						salaryBreakUpMenu(id);
					}
					// if user salary breakup is already exit then user has choice to update or not
					else {

						System.out.println(
								ConstantMsg.SALARY_BREAKUP_EXIST);
						String check = SC.nextLine();

						while (!(check.equals("Y") || check.equals("N"))) {
							System.err.println(ConstantMsg.WRONG_INPUT);
							check = SC.nextLine();
						}
						if (check.equals("Y")) {
							salaryBreakUpMenu(id);
						}
						

					}
				} catch (DataAccessException e) {
					System.out.println(ConstantMsg.DATABASE_ERROR);
				}

				break;
			// generate itr
			case 4:
				try {
					// getting
					int taxableIncome = incomeTaxServices.taxableIncome(id);

					if (taxableIncome == -1) {
						System.out.println(ConstantMsg.SALARY_BREAKUP_NOT_EXIST);
					}
					// if users salary breakup exist in database then calculate his taxable income
					// and income tax
					else {

						System.out.println("\n\n " + incomeTaxServices.generateItr(id));
						System.out.println("\n\nyour taxable income = " + taxableIncome);
						System.out.println("\n\nyour incometax = " + (taxableIncome * 0.2) + "\n\n");
					}
				} catch (DataAccessException e) {
					System.out.println(ConstantMsg.DATABASE_ERROR);
				}
				break;

			// users account details
			case 5:
				try {
					System.out.println(ConstantMsg.SHOW_ACCOUNT_DETAILS);
					List<Map<String, Object>> li = incomeTaxServices.getAccountDetails(id);
					System.out.println(li);
					break;
				} catch (DataAccessException e) {
					System.out.println(ConstantMsg.DATABASE_ERROR);
				}
			default:
				exitFromLoop = -1;
				break;
			}
		}

	}

	/**
	 * insert menu where user enter name,pass,pan number
	 */
	protected void insertMenu() {
		System.out.println("Enter your name and type -1 to go back");
		String name = validater.validateName(SC.nextLine());
		name = validater.checkLength("name", name);
		if (name.equals("1"))
			return;

		System.out.println("Enter password and type -1 to go back");
		String password = SC.nextLine();

		password = validater.checkLength("password", password);
		if (password.equals("-1"))
			return;
		System.out.println("Enter pan and type -1 to go back menu");

		String pan = SC.nextLine();

		pan = validater.checkLength("pan", pan);
		if (pan.equals("-1"))
			return;
		// check pan number is already exit in database or not
		while (incomeTaxServices.checkPan(pan)) {
			System.err.println(ConstantMsg.ERROR_PAN);
			pan = SC.nextLine();
			validater.checkLength("pan", pan);
		}
		System.out.println("Enter Salary and type -1 to go back");
		int salary = validater.validateInt();
		if (salary == -1)
			return;
		int id = incomeTaxServices.createAccount(name, password, pan, salary);
		System.out.println("\n\nYour newly created account with id is :- " + id);
	}

	/**
	 * editing menu of user where user can change his details
	 * 
	 * @param id
	 */
	protected void editingDetails(int id) {
		// changing editing menu
		int exitFromLoop = 1;
		while (exitFromLoop == 1) {
			System.out.println(ConstantMsg.EDIT_MENU);
			int choice = validater.validateInt();
			switch (choice) {
			// edit name
			case 1:
				try {
					System.out.println(ConstantMsg.ENTER_NAME);
					String name = validater.validateName(SC.nextLine());
					if (name.equals("-1")) {
						break;
					}
					validater.checkLength("name", name);
					if (incomeTaxServices.update(id, "name", name) == 1)
						System.out.println(ConstantMsg.UPDATED);
					else
						System.out.println(ConstantMsg.NOT_UPDATED);
				} catch (DataAccessException e) {
					System.out.println(ConstantMsg.DATABASE_ERROR);
				}
				break;
			// edit password
			case 2:
				try {
					System.out.println(ConstantMsg.ENTER_PASSWORD);
					String password = SC.nextLine();
					validater.checkLength("password", password);
					if (password.equals("-1"))
						break;
					if (incomeTaxServices.update(id, "password", password) == 1)
						System.out.println(ConstantMsg.UPDATED);
					else
						System.out.println(ConstantMsg.NOT_UPDATED);
				} catch (DataAccessException e) {
					System.out.println(ConstantMsg.DATABASE_ERROR);
				}
				break;

			// edit salary
			case 3:
				try {
					System.out.println(ConstantMsg.ENTER_SALARY);
					int salary = validater.validateInt();
					if (salary == -1)
						break;
					if (incomeTaxServices.updateSalary(id, salary) == 1)
						System.out.println(ConstantMsg.UPDATED);
					else
						System.out.println(ConstantMsg.NOT_UPDATED);
				} catch (DataAccessException e) {
					System.out.println(ConstantMsg.DATABASE_ERROR);
				}
				break;
			// exit from editing menu
			default:
				exitFromLoop = -1;
				break;
			}
		}
	}

	/**
	 * salary breakup input from user
	 * 
	 * @param accId
	 */
	protected void salaryBreakUpMenu(int accId) {

		System.out.println(ConstantMsg.ENTER_BASE_SALARY);
		int baseSalary = validater.validateInt();
		if (baseSalary == -1)
			return;
		System.out.println(ConstantMsg.ENTER_HRA);
		int hra = validater.validateInt();
		if (hra == -1)
			return;
		System.out.println(ConstantMsg.ENTER_TA);
		int transAllowances = validater.validateInt();
		if (transAllowances == -1)
			return;
		System.out.println(ConstantMsg.ENTER_SA);
		int specialAllowances = validater.validateInt();
		if (specialAllowances == -1)
			return;
		System.out.println(ConstantMsg.ENTER_MA);
		int medicalAllowances = validater.validateInt();
		if (medicalAllowances == -1)
			return;
		try {
			if (!incomeTaxServices.updateSalaryBreakUp(accId, baseSalary, hra, transAllowances, specialAllowances,
					medicalAllowances))
				System.err.println(ConstantMsg.ERROR_SALARY_BREAKUP);
			else
				System.out.println(ConstantMsg.SALARY_BREAKUP_EXIST);
		} catch (DataAccessException e) {
			System.out.println(ConstantMsg.DATABASE_ERROR);
		}

	}

}
