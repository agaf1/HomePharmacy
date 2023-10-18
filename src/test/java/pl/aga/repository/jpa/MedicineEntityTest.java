package pl.aga.repository.jpa;

import org.junit.jupiter.api.Test;
import pl.aga.service.domain.Medicine;
import pl.aga.service.domain.Type;

import java.time.LocalDate;
import java.time.Period;

import static org.assertj.core.api.Assertions.assertThat;

class MedicineEntityTest {

    MedicineEntity medicineEntity = new MedicineEntity();

    @Test
    public void should_map_PERIOD_to_String() {
        Period period = Period.of(1, 2, 5);
        String result = MedicineEntity.setStringFromPeriod(period);

        assertThat(result).isEqualTo("1:2:5");
    }

    @Test
    public void should_map_Medicine_to_MedicineEntity() {
        Medicine medicine = new Medicine();
        medicine.setName("aspirin");
        medicine.setType(Type.PILLS);
        medicine.setContents(100);
        medicine.setTermOfValidity(LocalDate.of(2025, 10, 30));
        medicine.setAllowedDurationOfUse(Period.of(1, 2, 5));

        MedicineEntity entity = MedicineEntity.of(medicine);

        assertThat(entity.getName()).isEqualTo("aspirin");
        assertThat(entity.getType()).isEqualTo("PILLS");
        assertThat(entity.getAllowedDurationOfUse()).isEqualTo("1:2:5");
    }

}