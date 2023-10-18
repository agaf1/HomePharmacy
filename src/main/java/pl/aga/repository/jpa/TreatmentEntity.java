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
    private String lengthOfTreatment;

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
        if (!(o instanceof TreatmentEntity treatment)) return false;
        return id != null && Objects.equals(id, treatment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public String setLengthOfTreatment(Period lengthOfTreatment) {
        this.lengthOfTreatment = String.join(":",
                String.valueOf(lengthOfTreatment.getYears()),
                String.valueOf(lengthOfTreatment.getMonths()),
                String.valueOf(lengthOfTreatment.getDays()));
        return this.lengthOfTreatment;
    }

    public Period getLengthOfTreatment() {
        if (lengthOfTreatment != null) {
            String[] period = lengthOfTreatment.split(":");
            return Period.of(Integer.valueOf(period[0]), Integer.valueOf(period[1]), Integer.valueOf(period[2]));
        }
        return Period.ZERO;
    }
}
