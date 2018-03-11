package salariati.main;

import salariati.controller.implementations.EmployeeControllerImpl;
import salariati.controller.interfaces.EmployeeControllerInterface;
import salariati.repository.interfaces.EmployeeRepositoryInterface;
import salariati.repository.mock.EmployeeRepositoryMock;
import salariati.view.implementations.EmployeeViewMock;
import salariati.view.interfaces.EmployeeViewInterface;

//functionalitati
//i.	 adaugarea unui nou angajat (nume, prenume, CNP, functia didactica, salariul de incadrare);
//ii.	 modificarea functiei didactice (asistent/lector/conferentiar/profesor) a unui angajat;
//iii.	 afisarea salariatilor ordonati descrescator dupa salariu si crescator dupa varsta (CNP).
public class Main {

    public static void main(String[] args) {

        EmployeeRepositoryInterface employeesRepository = new EmployeeRepositoryMock();
        EmployeeControllerInterface ctrl = new EmployeeControllerImpl(employeesRepository);
        EmployeeViewInterface view = new EmployeeViewMock(ctrl);

        view.run();
    }

}
