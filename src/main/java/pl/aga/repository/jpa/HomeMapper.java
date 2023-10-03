package pl.aga.repository.jpa;

import org.mapstruct.Mapper;
import pl.aga.service.domain.Home;

@Mapper(componentModel = "spring")
interface HomeMapper {
    Home map(HomeEntity entity);

    HomeEntity map(Home home);
}
