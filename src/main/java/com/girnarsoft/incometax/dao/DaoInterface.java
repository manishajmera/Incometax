package com.girnarsoft.incometax.dao;

import java.util.List;
import java.util.Map;

public interface DaoInterface {
	List<Map<String, Object>> getPan(String pan);

	List<Map<String, Object>> containsId(int id);

	List<Map<String, Object>> containsAccount(int id, String password);

	void createAccount(String name, String password, String pan, int salary);

	int getAccountId(String name, String password, String pan);

	int update(int id, String updateField, String updateValue);

	int updateSalary(int id, int salary);

	int remove(int id);

	void updateSalaryBreakUp(int accId, int baseSalary, int hra, int transAllowances, int specialAllowances,
			int medicalAllowances);

	int getSalary(int accId);

	List<Map<String, Object>> getSalaryExpenses(int id);

	List<Map<String, Object>> showAccountDetails(int id);

}
