package pl.aga.controller;

import lombok.Getter;
import lombok.Setter;
import pl.aga.service.domain.Dosage;

import java.time.Period;

@Getter
@Setter
public class TreatmentDTO {

    private Integer familyMemberId;

    private Integer medicineId;

    private int numberOfTimesPerDay;
    private double quantityPerDose;
    private String lengthOfTreatment;


    public Dosage getDosage() {
        String[] period = lengthOfTreatment.split(":");
        Period length = Period.of(Integer.valueOf(period[0]), Integer.valueOf(period[1]), Integer.valueOf(period[2]));
        Dosage dosage = new Dosage(numberOfTimesPerDay, quantityPerDose, length);
        return dosage;
    }

}
