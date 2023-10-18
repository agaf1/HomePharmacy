package pl.aga.repository.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pl.aga.service.domain.Medicine;
import pl.aga.service.domain.Type;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MedicineJpaRepositoryTest {

    @Autowired
    private MedicineJpaRepository medicineRepo;

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate table treatment_entity")
    @Sql(statements = "truncate table family_member")
    @Sql(statements = "truncate table home")
    @Sql(statements = "truncate table medicine")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_save_medicine() {
        Medicine medicine = getMedicine();
        medicine.setAllowedDurationOfUse(Period.of(0, 6, 0));

        medicineRepo.save(medicine);

        assertThat(1).isEqualTo(medicineRepo.count());
    }


    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate table treatment_entity")
    @Sql(statements = "truncate table family_member")
    @Sql(statements = "truncate table home")
    @Sql(statements = "truncate table medicine")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_find_medicine_by_name() {
        Medicine medicine = getMedicine();
        medicine.setAllowedDurationOfUse(Period.of(0, 6, 0));

        medicineRepo.save(medicine);
        Optional<Medicine> result = medicineRepo.findById(1);

        assertThat(Type.PILLS).isEqualTo(result.get().getType());
        assertThat(Period.of(0, 6, 0)).isEqualTo(result.get().getAllowedDurationOfUse());
    }

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate table treatment_entity")
    @Sql(statements = "truncate table family_member")
    @Sql(statements = "truncate table home")
    @Sql(statements = "truncate table medicine")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_not_find_medicine_by_name_and_return_empty_optional() {
        Medicine medicine = getMedicine();

        medicineRepo.save(medicine);
        Optional<Medicine> result = medicineRepo.findById(2);

        assertThat(Optional.empty()).isEqualTo(result);
    }

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate table treatment_entity")
    @Sql(statements = "truncate table family_member")
    @Sql(statements = "truncate table home")
    @Sql(statements = "truncate table medicine")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_return_all_medicines() {
        Medicine medicine1 = getMedicine();
        Medicine medicine2 = getMedicine();
        medicine2.setName("gripex");
        medicine2.setId(2);
        Medicine medicine3 = getMedicine();
        medicine3.setName("ibuprofen");
        medicine3.setId(3);

        medicineRepo.save(medicine1);
        medicineRepo.save(medicine2);
        medicineRepo.save(medicine3);

        List<Medicine> result = medicineRepo.getAll();

        assertThat(3).isEqualTo(result.size());
    }

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate table treatment_entity")
    @Sql(statements = "truncate table family_member")
    @Sql(statements = "truncate table home")
    @Sql(statements = "truncate table medicine")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_delete_medicine() {
        Medicine medicine = getMedicine();

        medicineRepo.save(medicine);
        medicineRepo.delete(medicine);

        assertThat(0).isEqualTo(medicineRepo.count());
    }

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate table treatment_entity")
    @Sql(statements = "truncate table family_member")
    @Sql(statements = "truncate table home")
    @Sql(statements = "truncate table medicine")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_not_delete_when_medicine_is_not_exist() {
        Medicine medicine = getMedicine();
        medicineRepo.save(medicine);

        Medicine medicine1 = getMedicine();
        medicine1.setName("gripex");
        medicine1.setId(2);

        medicineRepo.delete(medicine1);

        assertThat(1).isEqualTo(medicineRepo.count());
    }


    private static Medicine getMedicine() {
        Medicine medicine = new Medicine();
        medicine.setId(1);
        medicine.setName("Aspirin");
        medicine.setType(Type.PILLS);
        medicine.setContents(50);
        medicine.setTermOfValidity(LocalDate.of(2025, 10, 30));
        medicine.setAllowedDurationOfUse(Period.ZERO);
        return medicine;
    }

}