package com.girnarsoft.incometax.controller;

import java.util.Scanner;

import org.springframework.stereotype.Component;

/**
 * Checkers class is used for checking that users enter integer value in empId
 * and String name checker authenticate user through his employeeId and password
 * 
 * @author gspl
 *
 */
@Component
public class Validator {

	private final Scanner SC = new Scanner(System.in);

	/**
	 * validate integer to check that user deoesn't enter null value or string
	 */

	public int validateInt() {
		while (true) {
			try {
				int value = Integer.parseInt(SC.nextLine());
				if(value==-1)
					return -1;
				if (value < -1) {
					throw new Exception("\npls enter positive number and type -1 to go back");
				}
				return value;
			} catch (NumberFormatException e) {
				System.err.println(ConstantMsg.WRONG_INPUT);
				continue;
			} catch (Exception e) {
				System.err.println(e.getMessage());
				continue;
			}
		}

	}

	/**
	 * validate name to be of right format
	 * 
	 * @param name
	 * @return
	 */
	public String validateName(String name) {
		// regular exper
		if(name.equals("-1"))
			return "-1";
		if (name.trim().length() == 0) {
			System.err.println(ConstantMsg.EMPTY_VALUE);
			name = validateName(SC.nextLine());
		}

		String regex = "^[a-zA-Z ]*$";
		if (!name.matches(regex)) {
			System.err.println(ConstantMsg.WRONG_INPUT);
			name = validateName(SC.nextLine());
		}
		return name;

	}

	/**
	 * check length of pan,password to be in database limit
	 * 
	 * @param type
	 * @param value
	 * @return
	 */
	public String checkLength(String type, String value) {
		// check of password length to be of 25 character or less
		if (type.equals("password")) {

			while (value.length() > 25 || value.length() < 4) {
				System.err.println(ConstantMsg.PASSWORD_LENGTH);
				value = SC.nextLine();
			}

		}
		// check name length to be of 50 words or less
		else if (type.equals("name")) {
			while (value.length() > 50) {
				System.err.println(ConstantMsg.NAME_LENGTH);
				value = validateName(SC.nextLine());
			}
		}
		// check pan length to be of 15 char or less
		else if (type.equals("pan")) {
			while (value.length() > 15) {
				System.err.println(ConstantMsg.PAN_LENGTH);
				value = SC.nextLine();
			}
		}
		return value;
	}

}
