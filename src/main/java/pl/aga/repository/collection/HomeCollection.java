package pl.aga.repository.collection;

import pl.aga.repository.HomeRepository;
import pl.aga.service.domain.FamilyMember;
import pl.aga.service.domain.Home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeCollection implements HomeRepository {

    Map<String, List<FamilyMemberCollection>> homeCollection = new HashMap<>();


    @Override
    public void save(Home home) {
        String homeName = home.getFamilyName();
        int idHome = home.getId();
        List<FamilyMemberCollection> members = new ArrayList<>();
        for (FamilyMember familyMember : home.getFamilyMembers()) {
            members.add(new FamilyMemberCollection(familyMember.getName(), familyMember.getId()));
        }
        homeCollection.put(homeName, members);
    }

    @Override
    public long count() {
        return homeCollection.size();
    }
}
