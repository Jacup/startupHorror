package people.enums;

import lombok.Getter;

public enum ContractorType {
    RELAXED(30, 0, 20, 0),
    DEMANDING(0,0 , 0, 50),
    MOTHERFUCKER(30, 5, 0, 100);

    @Getter
    int delayPaymentWeekChance;

    @Getter
    int delayPaymentMonthChance;

    @Getter
    int avoidPunishmentForDelayChance;

    @Getter
    int looseContractChance;

    ContractorType(int delayPaymentWeekChance, int delayPaymentMonthChance, int avoidPunishmentForDelayChance, int looseContractChance) {
        this.delayPaymentWeekChance = delayPaymentWeekChance;
        this.delayPaymentMonthChance = delayPaymentMonthChance;
        this.avoidPunishmentForDelayChance = avoidPunishmentForDelayChance;
        this.looseContractChance = looseContractChance;
    }
}
