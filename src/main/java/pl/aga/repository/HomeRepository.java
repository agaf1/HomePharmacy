package pl.aga.repository;

import pl.aga.service.domain.Home;

public interface HomeRepository {
    Home save(Home home);

    long count();
}
