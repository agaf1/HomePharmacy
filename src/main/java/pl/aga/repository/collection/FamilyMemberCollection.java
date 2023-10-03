package pl.aga.repository.collection;

import lombok.Data;
import pl.aga.repository.FamilyMemberRepository;
import pl.aga.service.domain.Dosage;
import pl.aga.service.domain.FamilyMember;
import pl.aga.service.domain.Medicine;

import java.util.*;

@Data
public class FamilyMemberCollection implements FamilyMemberRepository {

    private final String name;

    List<FamilyMemberCollection> members = new ArrayList<>();

    Map<MedicineCollection, DosageCollection> treatmentsCollection = new HashMap<>();

    private MedicineCollection medicineCollection = new MedicineCollection();
    private DosageCollection dosageCollection = new DosageCollection();


    private Map<MedicineCollection, DosageCollection> of(Map<Medicine, Dosage> treatments) {
        Map<MedicineCollection, DosageCollection> treatmentCollection = new HashMap<>();
        for (Map.Entry<Medicine, Dosage> treatment : treatments.entrySet()) {
            treatmentsCollection.put(medicineCollection.of(treatment.getKey()), dosageCollection.of(treatment.getValue()));
        }
        return treatmentCollection;
    }

    private Map<Medicine, Dosage> map(Map<MedicineCollection, DosageCollection> treatmentsCollection) {
        Map<Medicine, Dosage> treatments = new HashMap<>();
        for (Map.Entry<MedicineCollection, DosageCollection> treatment : treatmentsCollection.entrySet()) {
            treatments.put(medicineCollection.map(treatment.getKey()), dosageCollection.map(treatment.getValue()));
        }
        return treatments;
    }


    @Override
    public void save(FamilyMember familyMember) {
        FamilyMemberCollection familyMemberCollection = new FamilyMemberCollection(familyMember.getName());
        familyMemberCollection.setTreatmentsCollection(this.of(familyMember.getTreatment()));
        members.add(familyMemberCollection);
    }

    @Override
    public Optional<FamilyMember> findByName(FamilyMember familyMember) {

        Optional<FamilyMemberCollection> familyMemberCollection =
                members.stream()
                        .filter(m -> m.getName().equals(familyMember.getName()))
                        .findAny();

        if (familyMemberCollection.isPresent()) {
            FamilyMember familyMemberFromCollection1 = new FamilyMember(familyMemberCollection.get().getName());
            familyMemberFromCollection1.setTreatment(this.map(familyMemberCollection.get().getTreatmentsCollection()));
            return Optional.of(familyMemberFromCollection1);
        }
        return Optional.empty();
    }

    @Override
    public void deleteTreatment(FamilyMember familyMember, Medicine medicine) {
        Optional<Map.Entry<MedicineCollection, DosageCollection>> optionalMap =
                treatmentsCollection.entrySet().stream()
                        .filter(m -> m.getKey().getName().equals(medicine.getName()))
                        .findAny();
        if (optionalMap.isPresent()) {
            treatmentsCollection.remove(optionalMap.get().getKey());
        }
    }
}
