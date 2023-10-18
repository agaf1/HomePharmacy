package pl.aga.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DosageDTO {

    private String medicineName;
    private int numberOfTimesPerDay;
    private double quantityPerDose;
}
