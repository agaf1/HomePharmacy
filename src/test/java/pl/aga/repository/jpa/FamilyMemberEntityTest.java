package pl.aga.repository.jpa;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.aga.service.domain.FamilyMember;

import java.time.LocalDate;
import java.time.Period;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FamilyMemberEntityTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    public void should_create_association_between_FamilyMemberEntity_and_MedicineEntity_with_joinTable_TreatmentEntity() {

        FamilyMemberEntity member = new FamilyMemberEntity();
        member.setName("Igo");

        MedicineEntity medicine = new MedicineEntity();
        medicine.setName("Demo");
        medicine.setType("PILLS");
        medicine.setContents(100);
        medicine.setTermOfValidity(LocalDate.of(2024, 10, 10));
        medicine.setAllowedDurationOfUse(Period.of(0, 6, 0));

        member.addTreatment(medicine, 3, 1, Period.of(0, 1, 0));

        entityManager.persist(member);
        entityManager.flush();

        FamilyMemberEntity readMember = entityManager
                .createQuery("from FamilyMemberEntity f where f.id=" + member.getId(), FamilyMemberEntity.class)
                .getSingleResult();

        Assertions.assertNotNull(readMember.getTreatments());
    }

    @Test
    public void should_map_family_member_entity_to_family_member() {
        FamilyMemberEntity member = new FamilyMemberEntity();
        member.setName("Igo");

        MedicineEntity medicine = new MedicineEntity();
        medicine.setName("Demo");
        medicine.setType("PILLS");
        medicine.setContents(100);
        medicine.setTermOfValidity(LocalDate.of(2024, 10, 10));
        medicine.setAllowedDurationOfUse(Period.of(0, 6, 0));

        member.addTreatment(medicine, 3, 1, Period.of(0, 1, 0));

        FamilyMember familyMember = FamilyMemberEntity.map(member);

        assertThat(familyMember.getTreatment().size()).isEqualTo(member.getTreatments().size());
    }


}
