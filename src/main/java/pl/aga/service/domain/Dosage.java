package pl.aga.service.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.Period;

@EqualsAndHashCode
@Getter
@Setter
@ToString
public class Dosage {

    private int numberOfTimesPerDay;
    private double quantityPerDose;
    private Period lengthOfTreatment;

    private LocalDate startOfTreatment;

    public Dosage() {
    }

    public Dosage(int numberOfTimesPerDay, double quantityPerDose, Period lengthOfTreatment) {
        this.numberOfTimesPerDay = numberOfTimesPerDay;
        this.quantityPerDose = quantityPerDose;
        this.lengthOfTreatment = lengthOfTreatment;
        startOfTreatment = LocalDate.now();
    }

}
