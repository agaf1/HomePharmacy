package pl.aga.repository.jpa;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "family_member")
@Data
public class FamilyMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
