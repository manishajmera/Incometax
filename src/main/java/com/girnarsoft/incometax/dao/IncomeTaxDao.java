package com.girnarsoft.incometax.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * database class through u can access database
 * 
 * @author gspl
 *
 */
@Repository
public class IncomeTaxDao implements DaoInterface {
	// creating instance of jdbc template which create connection to the database
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * @param jdbcTemplate
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * get pan from database
	 * 
	 * @param pan
	 * @return
	 */
	public List<Map<String, Object>> getPan(String pan) {
		// sql query
		String sql = "select pan from account where pan = '" + pan + "'&& status=1";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;

	}

	/**
	 * check whether user exit with id or not in database
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> containsId(int id) {
		// sql query
		String sql = "select id from account where id = " + id + "&& status=1";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;
	}

	/**
	 * authenticate user from id and password
	 * 
	 * @param id
	 * @param password
	 * @return
	 */
	public List<Map<String, Object>> containsAccount(int id, String password) {
		// sql query
		String sql = "select id from account where id = " + id + "&&password='" + password + "'&&status=1";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;
	}

	/**
	 * create account by setting all the fields
	 * 
	 * @param name
	 * @param password
	 * @param pan
	 * @param salary
	 */
	public void createAccount(String name, String password, String pan, int salary) {
		// sql query
		String sql = "insert into account(name,password,pan,salary) " + "values('" + name + "','" + password + "','"
				+ pan + "'," + salary + ")";
		jdbcTemplate.execute(sql);
	}

	/**
	 * @param name
	 * @param password
	 * @param pan
	 * @return
	 */
	public int getAccountId(String name, String password, String pan) {

		String sql = "select id from accou" + "nt where name= '" + name + "'&&password='" + password + "'&&pan='" + pan
				+ "'";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return Integer.parseInt(list.get(0).get("id").toString());
	}

	/**
	 * @param id
	 * @param updateField
	 * @param updateValue
	 * @return
	 */
	public int update(int id, String updateField, String updateValue) {
		String sql = "update account set " + updateField + " = '" + updateValue + "' where id= " + id;
		int status = jdbcTemplate.update(sql);
		return status;
	}

	/**
	 * @param id
	 * @param salary
	 * @return
	 */
	public int updateSalary(int id, int salary) {
		String sql = "update account set salary = " + salary + " where id= " + id;
		jdbcTemplate.execute(sql);
		int status = jdbcTemplate.update(sql);
		return status;
	}

	/**
	 * @param id
	 * @return
	 */
	public int remove(int id) {
		String sql = "update account set status = 0 where id= " + id;
		int status = jdbcTemplate.update(sql);
		return status;

	}

	/**
	 * @param accId
	 * @param baseSalary
	 * @param hra
	 * @param transAllowances
	 * @param specialAllowances
	 * @param medicalAllowances
	 */
	public void updateSalaryBreakUp(int accId, int baseSalary, int hra, int transAllowances, int specialAllowances,
			int medicalAllowances) {
		List<Map<String, Object>> li = getSalaryExpenses(accId);
		if (li.isEmpty()) {
			String sql = "insert into salary(account_id,base_salary,hra,transport_allowances,special_allowances,medical_allowances) values("
					+ accId + "," + baseSalary + "," + hra + "," + transAllowances + "," + specialAllowances + ","
					+ medicalAllowances + ");";
			jdbcTemplate.execute(sql);
		} else {
			String sql = "update table salary set base_salary=" + baseSalary + ",hra=" + hra + ",transport_allowances="
					+ transAllowances + ",special_allowances=" + specialAllowances + ",medical_allowances="
					+ medicalAllowances + ";";
			jdbcTemplate.execute(sql);
		}

	}

	/**
	 * @param accId
	 * @return
	 */
	public int getSalary(int accId) {
		String sql = "select salary from account where id=" + accId + ";";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return Integer.parseInt(list.get(0).get("salary").toString());

	}

	/**
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getSalaryExpenses(int id) {
		String sql = "select base_salary,hra,transport_allowances,special_allowances,medical_allowances,"
				+ "salary from salary inner join account on salary.account_id = account.id where salary.account_id="
				+ id + ";";

		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;

	}

	/**
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> showAccountDetails(int id) {
		String sql = "select id,name,password,pan,salary from account where id = " + id + ";";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;
	}

}
