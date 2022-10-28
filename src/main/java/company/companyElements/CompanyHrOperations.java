package company.companyElements;

import company.Company;
import gameplay.Game;

public class CompanyHrOperations {

    private final Company company;

    private final CompanyEmployeeManager companyEmployeeManager;

    public CompanyHrOperations(Company company, CompanyEmployeeManager companyEmployeeManager) {
        this.company = company;
        this.companyEmployeeManager = companyEmployeeManager;
    }

    public void paySalaryToWorkers() {
        // TODO: calculate salary per day, because if someone is hired at the end of the month, he shouldn't get full salary.
        for (var employee : companyEmployeeManager.getHiredEmployees()) {
            Double salary = employee.getSalary();

            if (company.getCash() > salary) {
                company.setCash(company.getCash() - salary);
            } else {
                System.out.println("Whoops! It looks like you don't have enough money to pay salary to " + employee.getName());
                Game.lostGame();
            }
        }
    }

}
