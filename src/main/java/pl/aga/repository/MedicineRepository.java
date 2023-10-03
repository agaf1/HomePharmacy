package pl.aga.repository;

import pl.aga.service.domain.Medicine;

import java.util.List;
import java.util.Optional;

public interface MedicineRepository {

    void save(Medicine medicine);

    Optional<Medicine> findByName(String name);

    void delete(Medicine medicine);

    List<Medicine> getAll();
}
