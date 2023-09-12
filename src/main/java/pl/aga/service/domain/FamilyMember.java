package pl.aga.service.domain;

import lombok.Value;

import java.util.Map;

@Value
public class FamilyMember {

    private final String name;

    Map<Medicine, Dosage> medicines;

}
