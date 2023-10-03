package pl.aga.repository.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.aga.service.domain.Dosage;
import pl.aga.service.domain.FamilyMember;
import pl.aga.service.domain.Medicine;
import pl.aga.service.domain.Type;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class FamilyMemberCollectionTest {

    private FamilyMemberCollection familyMemberCollection = new FamilyMemberCollection("");

    @Test
    public void should_save_familyMember_to_collection() {
        FamilyMember familyMember = new FamilyMember("Aga");
        familyMember.setTreatment(Map.of(new Medicine(), new Dosage(1, 2, Period.ZERO)));

        familyMemberCollection.save(familyMember);

        Assertions.assertEquals("Aga", familyMemberCollection.members.get(0).getName());
    }

    @Test
    public void should_find_Family_member_in_Collection() {
        FamilyMemberCollection member1 = new FamilyMemberCollection("Aga");
        FamilyMemberCollection member2 = new FamilyMemberCollection("Luke");
        member1.setMembers(List.of(member1, member2));

        FamilyMember familyMember = new FamilyMember("Aga");

        FamilyMember result = member1.findByName(familyMember).get();

        Assertions.assertEquals("Aga", result.getName());
    }

    @Test
    public void should_return_family_member_whit_empty_name_when_not_find() {
        FamilyMemberCollection member1 = new FamilyMemberCollection("Aga");
        FamilyMemberCollection member2 = new FamilyMemberCollection("Luke");
        member1.setMembers(List.of(member1, member2));

        FamilyMember familyMember = new FamilyMember("Igo");

        Optional<FamilyMember> result = member1.findByName(familyMember);

        Assertions.assertEquals(Optional.empty(), result);
    }

    @Test
    public void should_delete_treatment_from_collection() {
        Medicine medicine = getMedicine();
        MedicineCollection medicineCollection = new MedicineCollection().of(medicine);
        DosageCollection dosageCollection = new DosageCollection().of(new Dosage(2, 1, Period.ZERO));
        FamilyMemberCollection memberAga = new FamilyMemberCollection("Aga");
        memberAga.members.add(memberAga);
        Map<MedicineCollection, DosageCollection> treatmentAga = new HashMap<>();
        treatmentAga.put(medicineCollection, dosageCollection);
        memberAga.setTreatmentsCollection(treatmentAga);

        memberAga.deleteTreatment(new FamilyMember("Aga"), medicine);

        Assertions.assertEquals(0, memberAga.getTreatmentsCollection().size());
    }

    @Test
    public void should_not_delete_treatment_when_it_not_exist_in_Collection() {
        Medicine medicine = getMedicine();
        MedicineCollection medicineCollection = new MedicineCollection().of(medicine);
        DosageCollection dosageCollection = new DosageCollection().of(new Dosage(2, 1, Period.ZERO));
        FamilyMemberCollection memberAga = new FamilyMemberCollection("Aga");
        memberAga.members.add(memberAga);
        Map<MedicineCollection, DosageCollection> treatmentAga = new HashMap<>();
        treatmentAga.put(medicineCollection, dosageCollection);
        memberAga.setTreatmentsCollection(treatmentAga);

        Medicine medicineToDelete = getMedicine();
        medicineToDelete.setName("gripex");

        memberAga.deleteTreatment(new FamilyMember("Aga"), medicineToDelete);

        Assertions.assertEquals(1, memberAga.getTreatmentsCollection().size());
    }

    private static Medicine getMedicine() {
        Medicine medicine = new Medicine();
        medicine.setName("aspirin");
        medicine.setContents(100);
        medicine.setType(Type.PILLS);
        medicine.setTermOfValidity(LocalDate.of(2024, 8, 30));
        medicine.setAllowedDurationOfUse(Period.ZERO);
        return medicine;
    }

}