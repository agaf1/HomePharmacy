package pl.aga.controller;

import lombok.Getter;
import lombok.Setter;
import pl.aga.service.domain.DailyReport;
import pl.aga.service.domain.Dosage;
import pl.aga.service.domain.Medicine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class DailyReportDTO {

    private String nameOfFamilyMember;
    private List<DosageDTO> treatments = new ArrayList<>();
    private List<String> medicineToSupplement = new ArrayList<>();
    private String warning;


    static DailyReportDTO map(DailyReport dailyReport) {
        DailyReportDTO dailyReportDTO = new DailyReportDTO();

        dailyReportDTO.nameOfFamilyMember = dailyReport.getNameOfFamilyMember();

        for (Map.Entry<Medicine, Dosage> treatment : dailyReport.getTodayTreatment().entrySet()) {
            DosageDTO dosageDTO = new DosageDTO();
            dosageDTO.setMedicineName(treatment.getKey().getName());
            dosageDTO.setNumberOfTimesPerDay(treatment.getValue().getNumberOfTimesPerDay());
            dosageDTO.setQuantityPerDose(treatment.getValue().getQuantityPerDose());
            dailyReportDTO.treatments.add(dosageDTO);
        }

        for (Map.Entry<Medicine, String> medicines : dailyReport.getMedicineToSupplement().entrySet()) {
            String message = medicines.getKey().getName() + " - " + medicines.getValue();
            dailyReportDTO.medicineToSupplement.add(message);
        }

        dailyReportDTO.warning = dailyReport.getWarning().orElse("It is okay");

        return dailyReportDTO;
    }
}
