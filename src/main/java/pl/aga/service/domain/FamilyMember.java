package pl.aga.service.domain;

import lombok.*;

import java.util.Map;

@RequiredArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class FamilyMember {

    private final String name;

    Map<Medicine, Dosage> medicines;

}
