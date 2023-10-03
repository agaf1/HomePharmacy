package pl.aga.service.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
@Getter
@Setter
@ToString
public class FamilyMember {

    private Integer id;
    private String name;

    Map<Medicine, Dosage> treatment = new HashMap<>();

    public FamilyMember(String name) {
        this.name = name;
        id = null;
    }

    public FamilyMember() {
    }

}
