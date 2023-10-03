package pl.aga.repository.jpa;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pl.aga.service.domain.Dosage;
import pl.aga.service.domain.FamilyMember;
import pl.aga.service.domain.Medicine;
import pl.aga.service.domain.Type;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
class FamilyMemberJpaRepositoryTest {

    @Autowired
    private FamilyMemberJpaRepository repo;

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate table treatment_entity")
    @Sql(statements = "truncate table family_member")
    @Sql(statements = "truncate table home")
    @Sql(statements = "truncate table medicine")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_save_family_member() {
        FamilyMember familyMember = new FamilyMember("Aga");
        repo.save(familyMember);

        FamilyMember familyMember1 = new FamilyMember("Luke");
        repo.save(familyMember1);

        Assertions.assertThat(2).isEqualTo(repo.count());
    }

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate table treatment_entity")
    @Sql(statements = "truncate table family_member")
    @Sql(statements = "truncate table home")
    @Sql(statements = "truncate table medicine")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_save_family_member_with_map_of_medicine_and_dosage() {
        FamilyMember familyMember = new FamilyMember("Aga");
        Medicine medicine1 = new Medicine();
        medicine1.setName("Aspirin");
        medicine1.setType(Type.PILLS);
        medicine1.setContents(20);
        medicine1.setTermOfValidity(LocalDate.of(2025, 12, 12));
        medicine1.setAllowedDurationOfUse(Period.ZERO);
        Medicine medicine2 = new Medicine();
        medicine2.setName("Gripex");
        medicine2.setType(Type.PILLS);
        medicine2.setContents(40);
        medicine2.setTermOfValidity(LocalDate.of(2024, 5, 5));
        medicine2.setAllowedDurationOfUse(Period.ZERO);
        Dosage dosage = new Dosage(3, 1, Period.of(0, 0, 10));
        dosage.setStartOfTreatment(LocalDate.of(2023, 9, 25));
        familyMember.getTreatment().put(medicine1, dosage);
        familyMember.getTreatment().put(medicine2, dosage);

        repo.save(familyMember);
        List<FamilyMemberEntity> readList = repo.findAll();

        Assertions.assertThat(1).isEqualTo(repo.count());
        Assertions.assertThat(2).isEqualTo(readList.get(0).getTreatments().size());
    }

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate table treatment_entity")
    @Sql(statements = "truncate table family_member")
    @Sql(statements = "truncate table home")
    @Sql(statements = "truncate table medicine")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_find_family_member_by_name() {
        FamilyMember familyMember1 = new FamilyMember("Aga");
        FamilyMember familyMember2 = new FamilyMember("Luke");
        FamilyMember familyMember3 = new FamilyMember("Igo");

        repo.save(familyMember1);
        repo.save(familyMember2);
        repo.save(familyMember3);

        Optional<FamilyMember> result = repo.findByName(familyMember2);

        Assertions.assertThat("Luke").isEqualTo(result.get().getName());
    }

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate table treatment_entity")
    @Sql(statements = "truncate table family_member")
    @Sql(statements = "truncate table home")
    @Sql(statements = "truncate table medicine")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_not_find_family_member_by_name_and_return_empty_optional() {
        FamilyMember familyMember1 = new FamilyMember("Aga");
        FamilyMember familyMember2 = new FamilyMember("Luke");
        FamilyMember familyMember3 = new FamilyMember("Igo");

        repo.save(familyMember1);
        repo.save(familyMember2);

        Optional<FamilyMember> result = repo.findByName(familyMember3);

        Assertions.assertThat(Optional.empty()).isEqualTo(result);
    }

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate table treatment_entity")
    @Sql(statements = "truncate table family_member")
    @Sql(statements = "truncate table home")
    @Sql(statements = "truncate table medicine")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_find_all_treatment_for_Aga() {
        FamilyMember familyMember = new FamilyMember("Aga");
        Medicine medicine1 = new Medicine();
        medicine1.setName("Aspirin");
        medicine1.setType(Type.PILLS);
        medicine1.setContents(20);
        medicine1.setTermOfValidity(LocalDate.of(2025, 12, 12));
        medicine1.setAllowedDurationOfUse(Period.ZERO);
        Medicine medicine2 = new Medicine();
        medicine2.setName("Gripex");
        medicine2.setType(Type.PILLS);
        medicine2.setContents(40);
        medicine2.setTermOfValidity(LocalDate.of(2024, 5, 5));
        medicine2.setAllowedDurationOfUse(Period.ZERO);
        Dosage dosage = new Dosage(3, 1, Period.of(0, 0, 10));
        dosage.setStartOfTreatment(LocalDate.of(2023, 9, 25));
        familyMember.getTreatment().put(medicine1, dosage);
        familyMember.getTreatment().put(medicine2, dosage);

        FamilyMember familyMember2 = new FamilyMember("Luke");
        familyMember2.getTreatment().put(medicine1, dosage);

        repo.save(familyMember);
        repo.save(familyMember2);

        Set<TreatmentEntity> result = repo.findAllByFamilyMemberName(familyMember);

        Assertions.assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate table treatment_entity")
    @Sql(statements = "truncate table family_member")
    @Sql(statements = "truncate table home")
    @Sql(statements = "truncate table medicine")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_delete_treatment_from_list_of_family_member() {
        FamilyMember familyMember = new FamilyMember("Aga");
        Medicine medicine1 = new Medicine();
        medicine1.setName("Aspirin");
        medicine1.setType(Type.PILLS);
        medicine1.setContents(20);
        medicine1.setTermOfValidity(LocalDate.of(2025, 12, 12));
        medicine1.setAllowedDurationOfUse(Period.ZERO);
        Medicine medicine2 = new Medicine();
        medicine2.setName("Gripex");
        medicine2.setType(Type.PILLS);
        medicine2.setContents(40);
        medicine2.setTermOfValidity(LocalDate.of(2024, 5, 5));
        medicine2.setAllowedDurationOfUse(Period.ZERO);
        Dosage dosage = new Dosage(3, 1, Period.of(0, 0, 10));
        dosage.setStartOfTreatment(LocalDate.of(2023, 9, 25));
        familyMember.getTreatment().put(medicine1, dosage);
        familyMember.getTreatment().put(medicine2, dosage);

        repo.save(familyMember);
        List<FamilyMemberEntity> readList1 = repo.findAll();

        Assertions.assertThat(2).isEqualTo(readList1.get(0).getTreatments().size());

        repo.deleteTreatment(familyMember, medicine1);
        List<FamilyMemberEntity> readList2 = repo.findAll();

        Assertions.assertThat(1).isEqualTo(readList2.get(0).getTreatments().size());
    }
}