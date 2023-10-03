package pl.aga.repository.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.aga.service.domain.Dosage;
import pl.aga.service.domain.FamilyMember;
import pl.aga.service.domain.Medicine;

import java.time.Period;
import java.util.*;

@Entity
@Table(name = "family_member")
@Getter
@Setter
public class FamilyMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    private HomeEntity home;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<TreatmentEntity> treatments = new HashSet<>();

    public FamilyMemberEntity() {
    }

    public FamilyMemberEntity(String name) {
        this.name = name;
    }

    public void addTreatment(MedicineEntity medicine, int numberOfTimesPerDay, double quantityPerDose, Period lengthOfTreatment) {
        TreatmentEntity treatment = new TreatmentEntity(this, medicine);
        treatment.setNumberOfTimesPerDay(numberOfTimesPerDay);
        treatment.setQuantityPerDose(quantityPerDose);
        treatment.setLengthOfTreatment(lengthOfTreatment);
        this.getTreatments().add(treatment);
    }

    static FamilyMemberEntity of(FamilyMember familyMember) {
        FamilyMemberEntity familyMemberEntity = new FamilyMemberEntity();
        familyMemberEntity.setName(familyMember.getName());
        for (Map.Entry<Medicine, Dosage> memberTreatment : familyMember.getTreatment().entrySet()) {
            familyMemberEntity.addTreatment
                    (MedicineEntity.of(memberTreatment.getKey()),
                            memberTreatment.getValue().getNumberOfTimesPerDay(),
                            memberTreatment.getValue().getQuantityPerDose(),
                            memberTreatment.getValue().getLengthOfTreatment());
        }
        return familyMemberEntity;
    }

    static FamilyMember map(FamilyMemberEntity familyMemberEntity) {
        FamilyMember familyMember = new FamilyMember(familyMemberEntity.getName());
        familyMember.setId(familyMemberEntity.getId());
        Map<Medicine, Dosage> treatments = new HashMap<>();
        for (TreatmentEntity treatmentEntity : familyMemberEntity.getTreatments()) {
            Dosage dosage = new Dosage(treatmentEntity.getNumberOfTimesPerDay(),
                    treatmentEntity.getQuantityPerDose(),
                    treatmentEntity.getLengthOfTreatment());
            dosage.setStartOfTreatment(treatmentEntity.getStartOfTreatment());
            treatments.put(MedicineEntity.map(treatmentEntity.getMedicine()), dosage);
        }
        familyMember.setTreatment(treatments);
        return familyMember;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FamilyMemberEntity that = (FamilyMemberEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
