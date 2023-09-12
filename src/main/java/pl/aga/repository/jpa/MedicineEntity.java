package pl.aga.repository.jpa;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "medicine")
@Data
public class MedicineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
}
