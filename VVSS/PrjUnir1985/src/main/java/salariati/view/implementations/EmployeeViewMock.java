package salariati.view.implementations;

import salariati.controller.interfaces.EmployeeControllerInterface;
import salariati.enumeration.DidacticFunction;
import salariati.model.Employee;
import salariati.validator.EmployeeValidator;
import salariati.view.interfaces.EmployeeViewInterface;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

public class EmployeeViewMock implements EmployeeViewInterface {
    private final EmployeeControllerInterface ctrl;

    public EmployeeViewMock(EmployeeControllerInterface ctrl) {
        this.ctrl = ctrl;
    }

    @Override
    public void run() throws IOException {
        while(true) {
            for (Employee _employee : this.ctrl.getEmployeesList())
                System.out.println(_employee.toString());
            System.out.println("-----------------------------------------");

            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));


            System.out.println("Enter a new employee");

            System.out.println("First name:");
            String first = console.readLine();

            System.out.println("Last name:");
            String last = console.readLine();;

            System.out.println("CNP:");
            String cnp = console.readLine();

            System.out.println("Didactic function(0-2):");
            DidacticFunction function;
            Integer didactic = Integer.parseInt(console.readLine());
            try {
                function = DidacticFunction.values()[didactic];
            }catch (Exception e) {
                System.out.println("INVALID DATA");
                continue;
            }

            System.out.println("Salary:");
            String salary = console.readLine();
            Employee employee = new Employee(first, last, cnp, function, salary);
       
            EmployeeValidator validator = new EmployeeValidator();
            if(!validator.isValid(employee))
                System.out.println("INVALID DATA");
            else {
                System.out.println("OK!");
                ctrl.addEmployee(employee);
            }
        }
    }
}
