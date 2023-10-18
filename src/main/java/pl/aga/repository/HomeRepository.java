package pl.aga.repository;

import pl.aga.service.domain.Home;

public interface HomeRepository {
    void save(Home home);

    long count();
}
