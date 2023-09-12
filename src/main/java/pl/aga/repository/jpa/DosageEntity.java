package pl.aga.repository.jpa;

import jakarta.persistence.*;
import lombok.Data;
import pl.aga.service.domain.Type;

@Entity
@Table(name = "dosage")
@Data
public class DosageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    @Column(name = "times_per_day")
    private int numberOfTimesPerDay;

    @Column(name = "quantity_per_dose")
    private double quantityPerDose;
}
