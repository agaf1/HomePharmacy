package pl.aga.repository.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.aga.repository.MedicineRepository;
import pl.aga.service.domain.Medicine;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MedicineJpaRepository implements MedicineRepository {
    private final MedicineJpa medicineJpa;

    @Override
    public void save(Medicine medicine) {
        medicineJpa.save(MedicineEntity.of(medicine));
    }

    @Override
    public Optional<Medicine> findById(int medicineId) {
        Optional<MedicineEntity> medicineEntity = medicineJpa.findById(medicineId);
        if (medicineEntity.isPresent()) {
            return Optional.of(MedicineEntity.map(medicineEntity.get()));
        }
        return Optional.empty();
    }

    public Optional<MedicineEntity> findEntityById(int id) {
        return medicineJpa.findById(id);
    }

    @Override
    public void delete(Medicine medicine) {
        Optional<MedicineEntity> foundedMedicine = medicineJpa.findById(medicine.getId());
        if (foundedMedicine.isPresent()) {
            medicineJpa.delete(foundedMedicine.get());
        }
    }

    @Override
    public List<Medicine> getAll() {
        List<MedicineEntity> medicines = (List<MedicineEntity>) medicineJpa.findAll();

        return medicines.stream().map(m -> MedicineEntity.map(m)).collect(Collectors.toList());
    }

    public long count() {
        return medicineJpa.count();
    }
}
