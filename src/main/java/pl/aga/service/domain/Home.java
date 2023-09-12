package pl.aga.service.domain;

import lombok.*;

import java.util.List;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class Home {

    private final String familyName;

    private List<FamilyMember> familyMembers;

}
