package pl.aga.service.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public class Dosage {

    private final int numberOfTimesPerDay;
    private final double quantityPerDose;
    private final Period lengthOfTreatment;

    private LocalDate startOfTreatment;

}
