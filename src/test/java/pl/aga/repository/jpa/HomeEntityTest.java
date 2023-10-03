package pl.aga.repository.jpa;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class HomeEntityTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Test
    public void should_create_association_oneToMany_for_homeEntity_and_FamilyMemberEntity() {
        HomeEntity home = new HomeEntity();
        home.setFamilyName("Kowalski");

        FamilyMemberEntity member1 = new FamilyMemberEntity();
        member1.setName("Aga");
        FamilyMemberEntity member2 = new FamilyMemberEntity();
        member2.setName("Luke");
        FamilyMemberEntity member3 = new FamilyMemberEntity();
        member3.setName("Sofi");
        FamilyMemberEntity member4 = new FamilyMemberEntity();
        member4.setName("Igo");

        home.addMember(member1).addMember(member2).addMember(member3).addMember(member4);

        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(status -> {

            entityManager.persist(home);
            return null;
        });


        HomeEntity homeRead = entityManager
                .createQuery("from HomeEntity h where h.id=" + home.getId(), HomeEntity.class)
                .getSingleResult();
        entityManager.close();


        Assertions.assertNotNull(homeRead.getId());
        Assertions.assertEquals(homeRead.getFamilyMembers().size(), 4);
    }

}