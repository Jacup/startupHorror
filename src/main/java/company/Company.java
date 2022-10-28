package company;

import company.companyElements.CompanyEmployeeManager;
import company.companyElements.CompanyHrOperations;
import gameplay.Game;
import helpers.console.Randomizer;
import jobs.Project;

import lombok.Getter;
import lombok.Setter;

import people.employees.Developer;
import people.employees.Sales;
import people.employees.Tester;

import java.util.LinkedList;

public class Company {

    @Getter
    private final String name;

    @Getter
    @Setter
    private Double cash;

    @Getter
    private final LinkedList<Project> actualProjects = new LinkedList<>();

    @Getter
    private Integer daysSpendOnTaxes;

    private final CompanyEmployeeManager companyEmployeeManager;

    //for now it's have own getter, to delete when I refactor game
    @Getter
    private final CompanyHrOperations companyHrOperations;

    public Company(String name) {
        this.cash = generateRandomCashAmount();
        this.name = name;
        this.daysSpendOnTaxes = 0;
        companyEmployeeManager = new CompanyEmployeeManager();
        companyHrOperations = new CompanyHrOperations(this, companyEmployeeManager);
    }

    private Double generateRandomCashAmount() {
        return (double) Randomizer.generateRandomValue(10000, 20000);
    }

    public LinkedList<Developer> getHiredDevelopers() {
        return companyEmployeeManager.getHiredDevelopers();
    }

    public LinkedList<Tester> getHiredTesters() {
        return companyEmployeeManager.getHiredTesters();
    }

    public LinkedList<Sales> getHiredSales() {
        return companyEmployeeManager.getHiredSales();
    }

    public void performWork() {
        sendDevelopersToWork();
        sendTestersToWork();
        sendSalesToWork();
    }

    private void sendDevelopersToWork() {
        var developers = getHiredDevelopers();
        var projects = getActualProjects();

        if (projects != null) {
            for (var developer : developers) {
                System.out.println("> Developer " + developer.getName() + ":");
                if (developer.isSick()) {
                    System.out.println(Game.TAB + getName() + " is sick today.");
                    continue;
                }

                var validProject = developer.getFirstValidProject(projects);
                if (validProject != null) developer.goToWork(validProject);
            }
            System.out.println();
        }
    }

    private void sendTestersToWork() {
        var testers = getHiredTesters();
        if (testers == null) return;

        var projects = getActualProjects();
        if (projects == null) return;

        for (var tester : testers) {
            System.out.println("> Tester " + tester + ":");
            if (tester.isSick()) {
                System.out.println(Game.TAB + getName() + " is sick today.");
            }
        }
    }

    private void sendSalesToWork() {
        var sales = getHiredSales();

        for (var salesman : sales) {
            salesman.goToWork(actualProjects.getFirst());
        }
    }

    public void resetTaxDays() {
        daysSpendOnTaxes = 0;
    }

}
