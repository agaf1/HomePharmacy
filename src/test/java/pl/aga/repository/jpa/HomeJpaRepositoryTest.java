package pl.aga.repository.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pl.aga.service.domain.FamilyMember;
import pl.aga.service.domain.Home;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HomeJpaRepositoryTest {
    @Autowired
    private HomeJpa homeJpa;

    private HomeMapper entityMapper = Mappers.getMapper(HomeMapper.class);

    private HomeJpaRepository homeJpaRepository;

    @BeforeEach
    void setup() {
        homeJpaRepository = new HomeJpaRepository(homeJpa, entityMapper);
    }

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate table treatment_entity")
    @Sql(statements = "truncate table family_member")
    @Sql(statements = "truncate table home")
    @Sql(statements = "truncate table medicine")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_save_home_entity() {
        Home home = new Home("Kowalski");
        homeJpaRepository.save(home);
        assertThat(1).isEqualTo(homeJpaRepository.count());
    }

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate table treatment_entity")
    @Sql(statements = "truncate table family_member")
    @Sql(statements = "truncate table home")
    @Sql(statements = "truncate table medicine")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_save_home_entity_with_list_of_family_member_entity() {
        Home home = new Home("Kowalski");
        FamilyMember member1 = new FamilyMember("Aga");
        FamilyMember member2 = new FamilyMember("Luke");
        home.getFamilyMembers().add(member1);
        home.getFamilyMembers().add(member2);

        Home savedHome = homeJpaRepository.save(home);
        Home readedHome = homeJpaRepository.findById(home.getId());

        assertThat(savedHome.getId()).isEqualTo(readedHome.getId());
        assertThat(readedHome.getFamilyMembers());
        assertThat(1).isEqualTo(homeJpaRepository.count());
        assertThat(2).isEqualTo(homeJpaRepository.getAll().get(0).getFamilyMembers().size());
    }

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate table treatment_entity")
    @Sql(statements = "truncate table family_member")
    @Sql(statements = "truncate table home")
    @Sql(statements = "truncate table medicine")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_delete_home() {
        Home home = new Home("Kowalski");
        FamilyMember member1 = new FamilyMember("Aga");
        FamilyMember member2 = new FamilyMember("Luke");
        home.getFamilyMembers().add(member1);
        home.getFamilyMembers().add(member2);

        Home savedHome = homeJpaRepository.save(home);

        assertThat(homeJpaRepository.getAll().size()).isEqualTo(1);

        homeJpaRepository.delete(home);

        assertThat(homeJpaRepository.getAll().size()).isEqualTo(0);

    }
}