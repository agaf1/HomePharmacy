package pl.aga.repository;

import pl.aga.service.domain.FamilyMember;
import pl.aga.service.domain.Medicine;

import java.util.Optional;

public interface FamilyMemberRepository {

    void save(Integer homeId, FamilyMember familyMember);

    Optional<FamilyMember> findById(Integer id);

    void saveTreatment(Integer familyMemberId, FamilyMember familyMember);

    void saveTreatment(Integer familyMemberId, Integer medicineId, FamilyMember familyMember);

    void deleteTreatment(FamilyMember familyMember, Medicine medicine);
}
