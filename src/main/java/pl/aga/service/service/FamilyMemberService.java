package pl.aga.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.aga.repository.FamilyMemberRepository;
import pl.aga.repository.MedicineRepository;
import pl.aga.service.domain.DailyReport;
import pl.aga.service.domain.Dosage;
import pl.aga.service.domain.FamilyMember;
import pl.aga.service.domain.Medicine;
import pl.aga.service.exception.ContentsException;
import pl.aga.service.exception.TermOfValidityException;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FamilyMemberService {

    private final FamilyMemberRepository familyMemberRepository;
    private final MedicineRepository medicineRepository;
    private final MedicineService medicineService;
    private final LocalDateTimeSupplier localDateTimeSupplier;

    public void createFamilyMember(Integer homeId, FamilyMember familyMember) {
        familyMemberRepository.save(homeId, familyMember);
    }

    public void createNewTreatment(Integer familyMemberId, Integer medicineId, Dosage dosage) {

        FamilyMember familyMember = familyMemberRepository.findById(familyMemberId).orElseThrow();

        Medicine medicine = medicineRepository.findById(medicineId).orElseThrow();

        LocalDate startOfTreatment = localDateTimeSupplier.get();
        dosage.setStartOfTreatment(startOfTreatment);

        if (medicine.getAllowedDurationOfUse() != Period.ZERO) {
            if (medicine.getTermOfValidity().isAfter(startOfTreatment.plus(medicine.getAllowedDurationOfUse()))) {
                medicine.setTermOfValidity(startOfTreatment.plus(medicine.getAllowedDurationOfUse()));
            }
        }

        if (startOfTreatment.isBefore(medicine.getTermOfValidity())) {
            familyMember.setTreatment(Map.of(medicine, dosage));
            familyMemberRepository.saveTreatment(familyMemberId, medicineId, familyMember);
        } else {
            throw new IllegalArgumentException("This medicine has already expired. Use new one.");
        }
    }

    public DailyReport createDailyReportForOneFamilyMember(Integer familyMemberId, int days) throws Exception {

        Optional<FamilyMember> member = familyMemberRepository.findById(familyMemberId);
        if (member.isPresent()) {
            FamilyMember actualMember = member.get();
            Map<Medicine, Dosage> treatments = actualMember.getTreatment();
            Optional<String> warning = this.updateContentsOfMedicine(treatments);

            LocalDate today = localDateTimeSupplier.get();
            Map<Medicine, Dosage> todayTreatment = this.updateTreatment(member.orElseThrow(), treatments, today);
            Map<Medicine, String> medicineToSupplement =
                    this.checkListOfMedicineItQuantityAndValidityForNextDayAndWarnAboutIt(todayTreatment, days);

            DailyReport dailyReport = new DailyReport();
            dailyReport.setNameOfFamilyMember(actualMember.getName());
            dailyReport.setTodayTreatment(todayTreatment);
            dailyReport.setMedicineToSupplement(medicineToSupplement);
            dailyReport.setWarning(warning);

            return dailyReport;
        } else {
            throw new Exception("This family member doesn't exist.");
        }
    }

    Map<Medicine, Dosage> updateTreatment(FamilyMember familyMember, Map<Medicine, Dosage> treatments, LocalDate today) {

        Map<Medicine, Dosage> todayTreatment = new HashMap<>();

        for (Map.Entry<Medicine, Dosage> entry : treatments.entrySet()) {
            if (entry.getValue().getStartOfTreatment().plus(entry.getValue().getLengthOfTreatment())
                    .isBefore(today)) {
                familyMemberRepository.deleteTreatment(familyMember, entry.getKey());
            } else {
                todayTreatment.put(entry.getKey(), entry.getValue());
            }
        }
        return todayTreatment;
    }

    Optional<String> updateContentsOfMedicine(Map<Medicine, Dosage> treatments) throws Exception {
        Optional<String> warning = Optional.empty();
        try {
            for (Map.Entry<Medicine, Dosage> entry : treatments.entrySet()) {
                medicineService.updateContentsOfMedicinePackage(entry.getKey(), entry.getValue());
            }
        } catch (ContentsException e) {
            warning = Optional.of(e.getMessage());
        }
        return warning;
    }

    Map<Medicine, String> checkListOfMedicineItQuantityAndValidityForNextDayAndWarnAboutIt(Map<Medicine, Dosage> todayTreatment, int days) throws Exception {

        Map<Medicine, String> medicineToSupplement = new HashMap<>();

        for (Map.Entry<Medicine, Dosage> entry : todayTreatment.entrySet()) {
            try {
                medicineService.updateContentsOfMedicinePackage(entry.getKey(), entry.getValue());
                medicineService.checkTermOfValidity(entry.getKey(), days);
            } catch (ContentsException e) {
                medicineToSupplement.put(entry.getKey(), e.getMessage());
            } catch (TermOfValidityException ex) {
                medicineToSupplement.put(entry.getKey(), ex.getMessage());
            }
        }
        return medicineToSupplement;
    }
}
