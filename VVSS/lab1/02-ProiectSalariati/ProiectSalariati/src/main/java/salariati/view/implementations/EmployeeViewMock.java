package salariati.view.implementations;

import salariati.controller.interfaces.EmployeeControllerInterface;
import salariati.enumeration.DidacticFunction;
import salariati.model.Employee;
import salariati.validator.EmployeeValidator;
import salariati.view.interfaces.EmployeeViewInterface;

public class EmployeeViewMock implements EmployeeViewInterface {
    private final EmployeeControllerInterface ctrl;

    public EmployeeViewMock(EmployeeControllerInterface ctrl) {
        this.ctrl = ctrl;
    }

    @Override
    public void run() {
        for(Employee _employee : this.ctrl.getEmployeesList())
            System.out.println(_employee.toString());
        System.out.println("-----------------------------------------");

        Employee employee = new Employee("fist", "LastName", "1234567894321", DidacticFunction.ASISTENT, "2500");
        this.ctrl.addEmployee(employee);

        for(Employee _employee : this.ctrl.getEmployeesList())
            System.out.println(_employee.toString());

        EmployeeValidator validator = new EmployeeValidator();
        System.out.println( validator.isValid(new Employee("last", "LastName", "1234567894322", DidacticFunction.TEACHER, "3400")) );
    }
}
