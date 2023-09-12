package pl.aga.repository.jpa;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "home")
@Data
public class HomeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "family_name")
    private String familyName;
}
