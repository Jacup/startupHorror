package people.enums;

import lombok.Getter;

public enum ContractorType {
    RELAXED(0.3, 0, 0.2, 0),
    DEMANDING(0,0 , 0, 0.5),
    MOTHERFUCKER(0.3, 0.05, 0, 1.0);

    @Getter
    double delayPaymentWeekChance;

    @Getter
    double delayPaymentMonthChance;

    @Getter
    double avoidPunishmentForDelayChance;

    @Getter
    double looseContractChance;

    ContractorType(double delayPaymentWeekChance, double delayPaymentMonthChance, double avoidPunishmentForDelayChance, double looseContractChance) {
        this.delayPaymentWeekChance = delayPaymentWeekChance;
        this.delayPaymentMonthChance = delayPaymentMonthChance;
        this.avoidPunishmentForDelayChance = avoidPunishmentForDelayChance;
        this.looseContractChance = looseContractChance;
    }
}
