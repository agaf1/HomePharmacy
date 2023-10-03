package pl.aga.service.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@Getter
@Setter
@ToString
public class Home {
    private Integer id;
    private String familyName;

    private List<FamilyMember> familyMembers = new ArrayList<>();

    public Home(String familyName) {
        this.familyName = familyName;
    }

}
