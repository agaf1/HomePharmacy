package pl.aga.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.aga.repository.MedicineRepository;
import pl.aga.service.domain.Dosage;
import pl.aga.service.domain.Medicine;
import pl.aga.service.exception.ContentsException;
import pl.aga.service.exception.TermOfValidityException;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private MedicineRepository medicineRepository;
    private LocalDateTimeSupplier localDateTimeSupplier;

    public void createMedicine(Medicine medicine) {
        medicineRepository.save(medicine);
    }

    public void deleteMedicine(Medicine medicine) {
        medicineRepository.delete(medicine);
    }

    public void deleteListOfMedicines(List<Medicine> medicines) {
        for (Medicine medicine : medicines) {
            medicineRepository.delete(medicine);
        }
    }

    public void updateContentsOfMedicinePackage(Medicine medicine, Dosage dosage) throws Exception {
        Optional<Medicine> medicineFromDB = medicineRepository.findById(medicine.getId());
        if (medicineFromDB.isPresent()) {
            double medicineContents = medicineFromDB.get().getContents();
            double dailyConsumption = dosage.getNumberOfTimesPerDay() * dosage.getQuantityPerDose();
            double actualContents = medicineContents - dailyConsumption;

            if (actualContents > 0) {
                medicineFromDB.get().setContents(actualContents);
                medicineRepository.save(medicineFromDB.get());
            } else {
                throw new ContentsException("This medicine is already used up.");
            }
        } else {
            throw new Exception("Not founded Medicine");
        }
    }

    public void checkTermOfValidity(Medicine medicine, int quantityDaysEarly) throws Exception {
        Optional<Medicine> medicineFromDB = medicineRepository.findById(medicine.getId());
        if (medicineFromDB.isPresent()) {
            LocalDate termOfValidity = medicineFromDB.get().getTermOfValidity();
            LocalDate today = localDateTimeSupplier.get();

            if (termOfValidity.minus(Period.of(0, 0, quantityDaysEarly)).isBefore(today)) {
                throw new TermOfValidityException("This medicine is almost expired.");
            }
        } else {
            throw new Exception("Not founded Medicine");
        }
    }

    public List<Medicine> getExpiredAndEmptyMedicines() {
        List<Medicine> medicines = medicineRepository.getAll();

        LocalDate today = localDateTimeSupplier.get();

        List<Medicine> medicinesToThrowAway = medicines
                .stream()
                .filter(m -> m.getTermOfValidity().isBefore(today) || m.getContents() <= 0)
                .collect(Collectors.toList());

        return medicinesToThrowAway;
    }
}
