package salariati.repository.implementations;

import org.junit.Test;
import salariati.controller.interfaces.EmployeeControllerInterface;
import salariati.enumeration.DidacticFunction;
import salariati.model.Employee;
import salariati.repository.interfaces.EmployeeRepositoryInterface;

import java.io.File;

import static org.junit.Assert.*;

public class EmployeeRepositoryImplTest {
    @Test
    public void addValidEmployeeInvalidFile() throws Exception {
        EmployeeRepositoryInterface repo = new EmployeeRepositoryImpl("employeeDB/employees.txt");
        Employee validEmpl = new Employee("gelu", "george", "1951207060028", DidacticFunction.ASISTENT, "1200");

        assertFalse(repo.addEmployee(validEmpl));
    }

    @Test
    public void addValidEmployee() throws Exception {
        File tempFile = File.createTempFile("employee", "txt");

        EmployeeRepositoryInterface repo = new EmployeeRepositoryImpl(tempFile.getAbsolutePath());
        Employee validEmpl = new Employee("gelu", "george", "1951207060028", DidacticFunction.ASISTENT, "1200");
        assertTrue(repo.addEmployee(validEmpl));

        tempFile.delete();
    }

    @Test
    public void addInvalidEmployee() throws Exception {
        File tempFile = File.createTempFile("employee", "txt");

        EmployeeRepositoryInterface repo = new EmployeeRepositoryImpl(tempFile.getAbsolutePath());
        Employee validEmpl = new Employee("gelu", "george", "195120706008", DidacticFunction.ASISTENT, "1200");
        assertFalse(repo.addEmployee(validEmpl));

        tempFile.delete();
    }

}