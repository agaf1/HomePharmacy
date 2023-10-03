package pl.aga.repository.collection;

import lombok.Data;
import pl.aga.repository.FamilyMemberRepository;
import pl.aga.service.domain.Dosage;
import pl.aga.service.domain.FamilyMember;
import pl.aga.service.domain.Medicine;

import java.util.*;

@Data
public class FamilyMemberCollection implements FamilyMemberRepository {

    private String name;
    private int id;

    List<FamilyMemberCollection> members = new ArrayList<>();

    Map<MedicineCollection, DosageCollection> treatmentsCollection = new HashMap<>();

    private MedicineCollection medicineCollection = new MedicineCollection();
    private DosageCollection dosageCollection = new DosageCollection();

    public FamilyMemberCollection() {
    }

    public FamilyMemberCollection(String name, int id) {
        this.name = name;
        this.id = id;
    }


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
        FamilyMemberCollection familyMemberCollection = new FamilyMemberCollection(familyMember.getName(), familyMember.getId());
        familyMemberCollection.setTreatmentsCollection(this.of(familyMember.getTreatment()));
        members.add(familyMemberCollection);
    }

    @Override
    public Optional<FamilyMember> findById(Integer id) {

        Optional<FamilyMemberCollection> familyMemberCollection =
                members.stream()
                        .filter(m -> m.getId() == id)
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
                        .filter(m -> m.getKey().getId() == (medicine.getId()))
                        .findAny();
        if (optionalMap.isPresent()) {
            treatmentsCollection.remove(optionalMap.get().getKey());
        }
    }
}
