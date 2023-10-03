package pl.aga.repository.jpa;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import pl.aga.service.domain.FamilyMember;
import pl.aga.service.domain.Home;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "home")
@Data
public class HomeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "family_name")
    private String familyName;

    @OneToMany(mappedBy = "home", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    Set<FamilyMemberEntity> familyMembers = new HashSet<>();

    public HomeEntity addMember(FamilyMemberEntity member) {
        familyMembers.add(member);
        member.setHome(this);
        return this;
    }

    public static HomeEntity of(Home home) {
        HomeEntity homeEntity = new HomeEntity();
        homeEntity.setFamilyName(home.getFamilyName());
        for (FamilyMember member : home.getFamilyMembers()) {
            homeEntity.addMember(FamilyMemberEntity.of(member));
        }
        return homeEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HomeEntity that = (HomeEntity) o;
        return id != null && id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
