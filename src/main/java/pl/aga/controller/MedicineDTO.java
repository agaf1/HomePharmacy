package pl.aga.controller;

import lombok.Getter;
import lombok.Setter;
import pl.aga.service.domain.Medicine;
import pl.aga.service.domain.Type;

import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
public class MedicineDTO {

    private String name;
    private String type;
    private double contents;
    private LocalDate termOfValidity;
    private String allowedDurationOfUse;

    static Medicine map(MedicineDTO medicineDTO) {
        Medicine medicine = new Medicine();
        medicine.setName(medicineDTO.getName());
        medicine.setType(Type.valueOf(medicineDTO.getType()));
        medicine.setContents(medicineDTO.contents);
        medicine.setTermOfValidity(medicineDTO.getTermOfValidity());
        medicine.setAllowedDurationOfUse(medicineDTO.setPeriodFromString(medicineDTO.getAllowedDurationOfUse()));
        return medicine;
    }

    private Period setPeriodFromString(String allowedDurationOfUse) {
        if (allowedDurationOfUse != null) {
            String[] period = allowedDurationOfUse.split(":");
            return Period.of(Integer.valueOf(period[0]), Integer.valueOf(period[1]), Integer.valueOf(period[2]));
        }
        return Period.ZERO;
    }

}
