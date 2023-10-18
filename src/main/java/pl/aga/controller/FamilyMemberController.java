package pl.aga.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.aga.service.domain.DailyReport;
import pl.aga.service.domain.Dosage;
import pl.aga.service.domain.FamilyMember;
import pl.aga.service.service.FamilyMemberService;

@RestController
@RequiredArgsConstructor
public class FamilyMemberController {

    private final FamilyMemberService familyMemberService;

    @PostMapping(path = "/family-members/{home-id}")
    public void create(@PathVariable("home-id") Integer homeId, String name) {
        FamilyMember familyMember = new FamilyMember(name);
        familyMemberService.createFamilyMember(homeId, familyMember);
    }

    @PostMapping(path = "/family-members/treatments")
    public void createTreatment(@ModelAttribute TreatmentDTO treatmentDTO) {
        Dosage dosage = treatmentDTO.getDosage();
        familyMemberService.createNewTreatment(treatmentDTO.getFamilyMemberId(), treatmentDTO.getMedicineId(), dosage);
    }

    @GetMapping(path = "/family-members/report")
    public DailyReportDTO createReport(Integer familyMemberId, int days) throws Exception {
        DailyReport dailyReport = familyMemberService.createDailyReportForOneFamilyMember(familyMemberId, days);
        return DailyReportDTO.map(dailyReport);
    }

    
}
