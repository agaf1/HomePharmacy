package pl.aga.repository.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.aga.repository.FamilyMemberRepository;
import pl.aga.service.domain.FamilyMember;
import pl.aga.service.domain.Medicine;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class FamilyMemberJpaRepository implements FamilyMemberRepository {
    private final FamilyMemberJpa familyMemberJpa;
    private final TreatmentJpaRepository treatmentJpa;
    private final MedicineJpaRepository medicineJpa;

    @Override
    public void save(FamilyMember familyMember) {
        familyMemberJpa.save(FamilyMemberEntity.of(familyMember));
    }

    @Override
    public Optional<FamilyMember> findById(Integer familyMemberId) {
        Optional<FamilyMemberEntity> familyMemberEntity = familyMemberJpa.findById(familyMemberId);
        if (familyMemberEntity.isPresent()) {
            return Optional.of(FamilyMemberEntity.map(familyMemberEntity.get()));
        }
        return Optional.empty();
    }


    @Override
    @Transactional
    public void deleteTreatment(FamilyMember familyMember, Medicine medicine) {
        familyMemberJpa.removeTreatment(familyMember.getName(), medicine.getName());
    }

    public long count() {
        return familyMemberJpa.count();
    }

    public List<FamilyMemberEntity> findAll() {
        return (List<FamilyMemberEntity>) familyMemberJpa.findAll();
    }

    public Set<TreatmentEntity> findAllByFamilyMemberId(Integer familyMemberId) {

        Optional<FamilyMemberEntity> foundedMember = familyMemberJpa.findById(familyMemberId);
        if (foundedMember.isPresent()) {
            Integer memberId = foundedMember.get().getId();
            return treatmentJpa.findAll().stream().filter(t -> t.getId().getMemberId().equals(memberId)).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }


}
