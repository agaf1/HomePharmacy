package pl.aga.repository.collection;

import lombok.Data;
import pl.aga.service.domain.Dosage;

import java.time.LocalDate;
import java.time.Period;

@Data
public class DosageCollection {

    private int numberOfTimesPerDay;
    private double quantityPerDose;
    private Period lengthOfTreatment;
    private LocalDate startOfTreatment;

    DosageCollection of(Dosage dosage) {
        DosageCollection dosageCollection = new DosageCollection();
        dosageCollection.setNumberOfTimesPerDay(dosage.getNumberOfTimesPerDay());
        dosageCollection.setQuantityPerDose(dosage.getQuantityPerDose());
        dosageCollection.setLengthOfTreatment(dosage.getLengthOfTreatment());
        dosageCollection.setStartOfTreatment(dosage.getStartOfTreatment());
        return dosageCollection;
    }

    Dosage map(DosageCollection dosageCollection) {
        Dosage dosage = new Dosage(dosageCollection.numberOfTimesPerDay,
                dosageCollection.quantityPerDose,
                dosageCollection.lengthOfTreatment);
        dosage.setStartOfTreatment(dosageCollection.startOfTreatment);
        return dosage;
    }

}
