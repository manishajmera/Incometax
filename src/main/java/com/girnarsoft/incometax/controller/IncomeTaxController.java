package com.girnarsoft.incometax.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * menu of income tax through which user can login or sign up
 * 
 * @author gspl
 *
 */
public class IncomeTaxController {
	@Autowired
	// creating a user menu after login or create user
	private IncomeTaxMenu incomeTaxMenu;
	@Autowired
	// validator object to check weather user enter string or int to be of right format
	private Validator validate;

	/**
	 * creating login menu to register or login
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		new IncomeTaxController().executeApp();

	}

	private void executeApp() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
		// creating a bean object of incomeTaxMenu
		incomeTaxMenu = (IncomeTaxMenu) applicationContext.getBean("incomeTaxMenu");
		validate = (Validator) applicationContext.getBean("validator");
		while (true) {
			int choice;
			System.out.println(ConstantMsg.LOGIN_MENU);
			choice = validate.validateInt();
			switch (choice) {
			case 1:
				incomeTaxMenu.menu();
				break;
			case 2:
				incomeTaxMenu.insertMenu();
				break;

			default:
				((ConfigurableApplicationContext) applicationContext).close();
				System.out.println(ConstantMsg.APP_TERMINATED);
				break;

			}
		}
	}
}
