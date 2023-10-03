package pl.aga.repository.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

@Entity
@Getter
@Setter
public class TreatmentEntity {

    @EmbeddedId
    private TreatmentId id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapsId("memberId")
    private FamilyMemberEntity member;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapsId("medicineId")
    private MedicineEntity medicine;

    @Column(name = "times_per_day")
    private int numberOfTimesPerDay;

    @Column(name = "quantity_per_dose")
    private double quantityPerDose;

    @Column(name = "length_of_treatment")
    private Period lengthOfTreatment;

    @Column(name = "start_of_treatment")
    private LocalDate startOfTreatment;

    private TreatmentEntity() {
    }

    public TreatmentEntity(FamilyMemberEntity member, MedicineEntity medicine) {
        this.member = member;
        this.medicine = medicine;
        this.id = new TreatmentId(member.getId(), medicine.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (this == null || getClass() != o.getClass()) return false;
        TreatmentEntity that = (TreatmentEntity) o;
        return Objects.equals(this.medicine, that.medicine) && Objects.equals(this.member, that.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member, medicine);
    }
}
