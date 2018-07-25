package com.girnarsoft.incometax.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.girnarsoft.incometax.dao.DaoInterface;

/**
 * income tax service class which connects controller to database and only
 * access database when user enters a vlid input
 * 
 * @author gspl
 *
 */
@Service("incomeTaxServices")
public class IncomeTaxServices {
	// create instance of income tax dao
	@Autowired
	private DaoInterface incomeTaxDao;

	/**
	 * update salary breakup of user and send return status if it succesfully
	 * reflect into database
	 * 
	 * @param accId
	 * @param baseSalary
	 * @param hra
	 * @param transAllowances
	 * @param specialAllowances
	 * @param medicalAllowances
	 * @return
	 */
	public boolean updateSalaryBreakUp(int accId, int baseSalary, int hra, int transAllowances, int specialAllowances,
			int medicalAllowances) {
		int totalSalary = incomeTaxDao.getSalary(accId);
		if (totalSalary < hra + transAllowances + baseSalary + specialAllowances + medicalAllowances)
			return false;
		else {
			incomeTaxDao.updateSalaryBreakUp(accId, baseSalary, hra, transAllowances, specialAllowances,
					medicalAllowances);
			return true;
		}

	}

	/**
	 * create account of user and return user id
	 * 
	 * @param name
	 * @param password
	 * @param pan
	 * @param salary
	 * @return
	 */
	public int createAccount(String name, String password, String pan, int salary) {
		// insert into database and create an account
		incomeTaxDao.createAccount(name, password, pan, salary);
		return incomeTaxDao.getAccountId(name, password, pan);
	}

	/**
	 * check that pan is already exit in database or not
	 * 
	 * @param pan
	 * @return
	 */
	public boolean checkPan(String pan) {
		// get list of pan cards from database
		List<Map<String, Object>> list = incomeTaxDao.getPan(pan);
		if (list.isEmpty())
			return false;
		else
			return true;

	}

	/**
	 * check user exit or not
	 * 
	 * @param id
	 * @return
	 */
	public boolean containsId(int id) {
		// get list of user which have this id
		List<Map<String, Object>> li = incomeTaxDao.containsId(id);
		if (li.isEmpty())
			return false;
		else
			return true;
	}

	/**
	 * authenticate user
	 * 
	 * @param id
	 * @param password
	 * @return
	 */
	public boolean findAccountId(int id, String password) {
		// get all the values of users after authenticating
		List<Map<String, Object>> li = incomeTaxDao.containsAccount(id, password);

		if (li.isEmpty())
			return false;
		else
			return true;

	}
	/**
	 * calculate taxable income 
	 * @param id
	 * @return
	 */
	public int taxableIncome(int id) {
		int extraTaxablesalary=0;
		int expense;
		List<Map<String, Object>> list = generateItr(id);
		if (list.isEmpty())
			return -1;
		else {
			int salary = incomeTaxDao.getSalary(id);
			if (salary < 100000)
				return 0;
			else
			{
				expense = Integer.parseInt(list.get(0).get("hra").toString())
						+ Integer.parseInt(list.get(0).get("transport_allowances").toString())
						+ Integer.parseInt(list.get(0).get("special_allowances").toString())
						+ Integer.parseInt(list.get(0).get("medical_allowances").toString());
				if(expense>50000) {
					extraTaxablesalary = expense-50000;
					return salary+extraTaxablesalary-50000;
					
				}
			
					return salary-expense;
				
				
					
			}
				
		}
	}

	/**
	 * update pan,password,name in database and return update status
	 * 
	 * @param id
	 * @param updateField
	 * @param updateValue
	 * @return
	 */
	public int update(int id, String updateField, String updateValue) {
		return incomeTaxDao.update(id, updateField, updateValue);
	}

	/**
	 * update salary in database and return status
	 * 
	 * @param id
	 * @param salary
	 * @return
	 */
	public int updateSalary(int id, int salary) {
		return incomeTaxDao.updateSalary(id, salary);
	}

	/**
	 * remove emp from database by putting status =0
	 * 
	 * @param id
	 * @return
	 */
	public int remove(int id) {
		return incomeTaxDao.remove(id);
	}

	/**
	 * get list of salary break up from database and send it to controller class
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> generateItr(int id) {
		return incomeTaxDao.getSalaryExpenses(id);
	}


	/**
	 * return account details to controller class
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getAccountDetails(int id) {
		return incomeTaxDao.showAccountDetails(id);
	}



}
