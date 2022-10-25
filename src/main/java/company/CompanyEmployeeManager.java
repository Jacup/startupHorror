package company;

import lombok.Getter;
import people.employees.Developer;
import people.employees.Employee;
import people.employees.Sales;
import people.employees.Tester;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class CompanyEmployeeManager {

    @Getter
    private final LinkedList<Employee> hiredEmployees = new LinkedList<>();

    public LinkedList<Developer> getHiredDevelopers() {
        return hiredEmployees.stream().filter(e -> e instanceof Developer).map(Developer.class::cast).collect(Collectors.toCollection(LinkedList::new));
    }

    public LinkedList<Tester> getHiredTesters() {
        return hiredEmployees.stream().filter(e -> e instanceof Tester).map(Tester.class::cast).collect(Collectors.toCollection(LinkedList::new));
    }

    public LinkedList<Sales> getHiredSales() {
        return hiredEmployees.stream().filter(e -> e instanceof Sales).map(Sales.class::cast).collect(Collectors.toCollection(LinkedList::new));
    }

}
