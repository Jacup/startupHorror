package people;

import lombok.Getter;

import people.enums.ContractorType;

public class Contractor extends Human{

    @Getter
    private final ContractorType contractorType;

    public Contractor(String firstName, String lastName, ContractorType contractorType) {
        super(firstName, lastName);
        this.contractorType = contractorType;
    }
}
