package pl.aga.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.aga.repository.jpa.HomeJpaRepository;
import pl.aga.service.domain.Home;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final HomeJpaRepository homeJpaRepository;

    public void createHome(Home home) {
        homeJpaRepository.save(home);
    }
}
