package pl.aga.repository.jpa;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FamilyMemberJpa extends CrudRepository<FamilyMemberEntity, Integer> {

    @Query(value = "select fm from FamilyMemberEntity fm where fm.name in (?1)")
    Optional<FamilyMemberEntity> findByName(String name);

    @Query(value = "delete from treatment_entity e where " +
            " e.medicine_id = (select id from medicine m where m.name=:medicineName ) " +
            " and e.member_id = (select id from family_member where name=:familyMemember ) "
            , nativeQuery = true)
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    void removeTreatment(String familyMemember, String medicineName);

}
