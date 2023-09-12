package pl.aga.service.domain;

import lombok.Value;

@Value
public class Dosage {

    private final Type type;
    private final int numberOfTimesPerDay;
    private final double quantityPerDose;

}
