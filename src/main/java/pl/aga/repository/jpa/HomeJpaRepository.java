package pl.aga.repository.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.aga.repository.HomeRepository;
import pl.aga.service.domain.Home;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Qualifier("HomeJpaRepository")
public class HomeJpaRepository implements HomeRepository {

    private final HomeJpa homeJpa;

    private final HomeMapper homeMapper;

    @Transactional
    public void save(Home home) {
        HomeEntity entity = HomeEntity.of(home);
        homeJpa.save(entity);
    }

    @Transactional(readOnly = true)
    public Home findById(Integer id) {
        Optional<HomeEntity> entity = homeJpa.findById(id);
        return homeMapper.map(entity.orElse(null));
    }

    public long count() {
        return homeJpa.count();
    }

    @Transactional
    public List<HomeEntity> getAll() {
        return (List<HomeEntity>) homeJpa.findAll();
    }

    public void delete(Home home) {
        Integer id = findById(home.getId()).getId();
        homeJpa.deleteById(id);

    }
}
