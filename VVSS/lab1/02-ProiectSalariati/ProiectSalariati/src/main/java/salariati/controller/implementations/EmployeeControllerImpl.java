package salariati.controller.implementations;

import java.util.List;

import salariati.controller.interfaces.EmployeeControllerInterface;
import salariati.model.Employee;
import salariati.repository.interfaces.EmployeeRepositoryInterface;

public class EmployeeControllerImpl implements EmployeeControllerInterface {
	
	private EmployeeRepositoryInterface employeeRepository;
	
	public EmployeeControllerImpl(EmployeeRepositoryInterface employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	@Override
	public void addEmployee(Employee employee) {
		employeeRepository.addEmployee(employee);
	}
	
	@Override
	public List<Employee> getEmployeesList() {
		return employeeRepository.getEmployeeList();
	}
	
	@Override
	public void modifyEmployee(Employee oldEmployee, Employee newEmployee) {
		employeeRepository.modifyEmployee(oldEmployee, newEmployee);
	}

	@Override
	public void deleteEmployee(Employee employee) {
		employeeRepository.deleteEmployee(employee);
	}
	
}
