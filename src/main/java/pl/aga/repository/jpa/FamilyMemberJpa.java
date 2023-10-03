package pl.aga.repository.jpa;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FamilyMemberJpa extends CrudRepository<FamilyMemberEntity, Integer> {

    @Query(value = "delete from treatment_entity e where " +
            " e.medicine_id = (select id from medicine m where m.name=:medicineName ) " +
            " and e.member_id = (select id from family_member where name=:familyMember ) "
            , nativeQuery = true)
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    void removeTreatment(String familyMember, String medicineName);

}
