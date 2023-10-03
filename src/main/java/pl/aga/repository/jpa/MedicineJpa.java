package pl.aga.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MedicineJpa extends CrudRepository<MedicineEntity, Integer> {

    @Query(value = "select m from MedicineEntity m where m.name in (?1)")
    Optional<MedicineEntity> findByName(String name);
}
