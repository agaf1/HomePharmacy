package pl.aga.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.aga.service.domain.Medicine;
import pl.aga.service.service.MedicineService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    @PostMapping(path = "/medicines")
    public void createMedicine(@ModelAttribute MedicineDTO medicineDTO) {
        Medicine medicine = MedicineDTO.map(medicineDTO);
        medicineService.createMedicine(medicine);
    }

    @DeleteMapping(path = "/medicines/{medicine-id}")
    public void deleteMedicine(@PathVariable("medicine-id") Integer medicineId) {
        medicineService.deleteMedicine(medicineId);
    }

    @GetMapping(path = "/medicines/unusable")
    public List<Medicine> updateListMedicines() {
        return medicineService.getExpiredAndEmptyMedicines();
    }

    @PutMapping(path = "/medicines/unusable/remove")
    public void deleteListMedicines() {
        List<Medicine> medicines = medicineService.getExpiredAndEmptyMedicines();
        medicineService.deleteListOfMedicines(medicines);
    }

}
