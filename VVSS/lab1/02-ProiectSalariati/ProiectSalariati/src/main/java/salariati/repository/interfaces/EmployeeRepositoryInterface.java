package salariati.repository.interfaces;

import java.util.List;
import salariati.model.Employee;

public interface EmployeeRepositoryInterface {
	
	boolean addEmployee(Employee employee);
	void deleteEmployee(Employee employee);
	void modifyEmployee(Employee oldEmployee, Employee newEmployee);
	List<Employee> getEmployeeList();
	List<Employee> getEmployeeByCriteria(String criteria);

}
