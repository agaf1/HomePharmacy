package pl.aga.repository;

import pl.aga.service.domain.FamilyMember;
import pl.aga.service.domain.Medicine;

import java.util.Optional;

public interface FamilyMemberRepository {

    void save(FamilyMember familyMember);

    Optional<FamilyMember> findById(Integer id);

    void deleteTreatment(FamilyMember familyMember, Medicine medicine);
}
