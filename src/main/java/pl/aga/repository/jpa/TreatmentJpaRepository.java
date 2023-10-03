package pl.aga.repository.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class TreatmentJpaRepository {
    private final TreatmentJpa treatmentJpa;

    public Set<TreatmentEntity> findAll() {
        return new HashSet<TreatmentEntity>((Collection<? extends TreatmentEntity>) treatmentJpa.findAll());
    }

    
    public void delete(TreatmentEntity treatmentEntity) {
        treatmentJpa.deleteById(treatmentEntity.getId());
    }


}
