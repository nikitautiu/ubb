package salariati.controller.interfaces;

import salariati.model.Employee;

import java.util.List;

public interface EmployeeControllerInterface {
    void addEmployee(Employee employee);

    List<Employee> getEmployeesList();

    void modifyEmployee(Employee oldEmployee, Employee newEmployee);

    void deleteEmployee(Employee employee);
}
