package com.girnarsoft.incometax.controller;

import org.springframework.stereotype.Component;

@Component
public class ConstantMsg {
	public static final String WRONG_INPUT = "\nWrong input try again and to go back type -1";

	public static final String EMPTY_VALUE = "\null value";

	public static final String PASSWORD_LENGTH = "\npassword character length should be less than 25 or greater than 5 !pls Enter again";

	public static final String NAME_LENGTH = "\nname character length should be less than 50 !pls Enter again";

	public static final String PAN_LENGTH = "\npan characters length should be less than 15 !pls Enter again";

	public static final String NOT_CONTAINS = "\nnot exit please try again and type -1 to go back ";

	public static final String ERROR_PASSWORD = "\nPassword not correct please try again and type -1 to go back" ;

	public static final String ERROR_PAN = "pan no. already exit";

	public static final String ERROR_SALARY_BREAKUP = "\nyou entered wrong salary breakup which excced your salary";

	public static final String USER_MENU = "\n 1. Edit your account \n 2. Remove your account "
			+ "\n 3. Enter your salary breakUp\n 4. Generate Itr tax receipt"
			+ "\n 5. show account details  \n Enter any  other number for logout";

	public static final String DATABASE_ERROR = "\nSomething went wrong try again later";

	public static final String SIGN_OUT = "Signing off from main menu";

	public static final String EDIT_MENU = "\n 1. Edit name \n 2. Edit password"
			+ "\n 3. Edit salary  \n Press any other number  to exit from editing menu";

	public static final String NOT_UPDATED = "not updated currently because of technical issues";

	public static final String UPDATED = "your profile is updated";
	
	public static final String LOGIN_MENU = "\nChoose from the following choices "+"\n 1.Login  \n 2.Create an Income Tax Account \n"
			+ " Press other than this number to close the app";
	
	public static final String APP_TERMINATED = "Application terminated";
	
	public static final String INPUT_ID = "Enter id";
	
	public static final String SALARY_BREAKUP_EXIST = "Your Salary breakup salary already exit do you want to update type Y/N ";
	
	public static final String SALARY_BREAKUP_NOT_EXIST = "First Enter your salary breakup\n";
	
	public static final String SHOW_ACCOUNT_DETAILS = "\nshow account details";
	public static final String ENTER_NAME  = "Enter name and to go back type -1";
	public static final String ENTER_PASSWORD  = "Enter password and type -1 to back";
	public static final String ENTER_SALARY = "Enter salary and -1 to go back";
	public static final String ENTER_BASE_SALARY = "Enter base salary and type -1 to go back";
	public static final String ENTER_HRA = "Enter Hra ";
	public static final String ENTER_TA = "Enter Transporttaion allowances";
	public static final String ENTER_SA = "Enter Special Allowances";
	public static final String ENTER_MA = "Eneter Medical Allowances";
	public static final String SALARY_BREAKUP_UPDATED = "your salary breakup is updated";
	
}
