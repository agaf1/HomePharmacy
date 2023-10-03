package pl.aga.repository.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.aga.service.domain.Medicine;
import pl.aga.service.domain.Type;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "medicine")
@Getter
@Setter
public class MedicineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "contents")
    private double contents;

    @Column(name = "term_of_validity")
    private LocalDate termOfValidity;

    @Column(name = "allowed_duration_of_use")
    private Period allowedDurationOfUse;

    @OneToMany(mappedBy = "medicine", cascade = CascadeType.ALL)
    private Set<TreatmentEntity> treatments;


    public MedicineEntity() {
    }

    public MedicineEntity(String name, String type, double contents, LocalDate termOfValidity, Period allowedDurationOfUse) {
        this.name = name;
        this.type = type;
        this.contents = contents;
        this.termOfValidity = termOfValidity;
        this.allowedDurationOfUse = allowedDurationOfUse;
    }

    static MedicineEntity of(Medicine medicine) {
        MedicineEntity medicineEntity = new MedicineEntity();
        medicineEntity.setName(medicine.getName());
        medicineEntity.setType(medicine.getType().toString());
        medicineEntity.setContents(medicine.getContents());
        medicineEntity.setTermOfValidity(medicine.getTermOfValidity());
        medicineEntity.setAllowedDurationOfUse(medicine.getAllowedDurationOfUse());
        return medicineEntity;
    }

    static Medicine map(MedicineEntity medicineEntity) {
        Medicine medicine = new Medicine();
        medicine.setId(medicineEntity.getId());
        medicine.setName(medicineEntity.getName());
        medicine.setType(Type.valueOf(medicineEntity.getType()));
        medicine.setContents(medicineEntity.getContents());
        medicine.setTermOfValidity(medicineEntity.getTermOfValidity());
        medicine.setAllowedDurationOfUse(medicineEntity.getAllowedDurationOfUse());
        return medicine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicineEntity that = (MedicineEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

