package pl.aga.service.domain;

import lombok.Data;

import java.util.Map;
import java.util.Optional;

@Data
public class DailyReport {

    private String nameOfFamilyMember;
    private Map<Medicine, Dosage> todayTreatment;
    private Map<Medicine, String> medicineToSupplement;
    private Optional<String> warning;


}
