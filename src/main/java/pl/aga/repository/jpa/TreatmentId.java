package pl.aga.repository.jpa;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class TreatmentId implements Serializable {

    private Integer memberId;
    private Integer medicineId;

    private TreatmentId() {
    }

    public TreatmentId(Integer memberId, Integer medicineId) {
        this.medicineId = medicineId;
        this.memberId = memberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreatmentId that = (TreatmentId) o;
        return memberId != null && medicineId != null && Objects.equals(memberId, that.memberId) && Objects.equals(medicineId, that.medicineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, medicineId);
    }
}
