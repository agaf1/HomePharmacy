package pl.aga.service.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.aga.repository.FamilyMemberRepository;
import pl.aga.repository.MedicineRepository;
import pl.aga.service.domain.Dosage;
import pl.aga.service.domain.FamilyMember;
import pl.aga.service.domain.Medicine;
import pl.aga.service.domain.Type;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FamilyMemberServiceTest {

    @Mock
    private MedicineRepository medicineRepository;
    @Mock
    private LocalDateTimeSupplier localDateTimeSupplier;
    @InjectMocks
    private MedicineService medicineService;
    @Mock
    private FamilyMemberRepository repo;
    @InjectMocks
    private FamilyMemberService service;

    FamilyMember member = new FamilyMember("Aga");


    private Dosage getDosageWithDefaultValue() {
        Dosage dosage = new Dosage(2, 1, Period.of(0, 0, 10));
        dosage.setStartOfTreatment(LocalDate.of(2023, 9, 20));
        return dosage;
    }

    private Medicine getMedicineWithDefaultValue() {
        Medicine medicine = new Medicine();
        medicine.setName("aspirin");
        medicine.setType(Type.PILLS);
        medicine.setContents(20);
        medicine.setTermOfValidity(LocalDate.of(2024, 05, 05));
        medicine.setAllowedDurationOfUse(Period.ZERO);
        return medicine;
    }


    @Test
    public void should_create_new_treatment_and_save_to_DB_with_out_change_term_of_validity() {
        Medicine medicine = getMedicineWithDefaultValue();

        Mockito.when(medicineRepository.findById(1)).thenReturn(Optional.of(medicine));
        Mockito.when(repo.findById(1)).thenReturn(Optional.of(member));
        Mockito.when(localDateTimeSupplier.get()).thenReturn(LocalDate.of(2023, 9, 20));


        service.createNewTreatment(1, 1, this.getDosageWithDefaultValue());

        verify(repo, times(1)).saveTreatment(1, 1, member);
    }

    @Test
    public void should_create_new_treatment_and_save_to_DB_with_change_term_of_validity() {
        Medicine medicine = getMedicineWithDefaultValue();
        medicine.setAllowedDurationOfUse(Period.of(0, 6, 0));

        Mockito.when(medicineRepository.findById(1)).thenReturn(Optional.of(medicine));
        Mockito.when(repo.findById(1)).thenReturn(Optional.of(member));
        Mockito.when(localDateTimeSupplier.get()).thenReturn(LocalDate.of(2023, 9, 20));

        service.createNewTreatment(1, 1, this.getDosageWithDefaultValue());

        verify(repo, times(1)).saveTreatment(1, 1, member);

    }

    @Test
    public void should_throw_exception_when_medicine_has_already_expired() {
        Medicine medicine = getMedicineWithDefaultValue();
        medicine.setTermOfValidity(LocalDate.of(2022, 05, 05));
        medicine.setAllowedDurationOfUse(Period.of(0, 6, 0));

        Mockito.when(medicineRepository.findById(1)).thenReturn(Optional.of(medicine));
        Mockito.when(repo.findById(1)).thenReturn(Optional.of(member));
        Mockito.when(localDateTimeSupplier.get()).thenReturn(LocalDate.of(2023, 9, 20));

        assertThatThrownBy(() -> service.createNewTreatment(1, 1, this.getDosageWithDefaultValue()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("This medicine has already expired. Use new one.");
    }


    @Test
    public void should_update_treatment_list_when_treatment_is_ended_should_be_delete() {
        FamilyMember familyMember = new FamilyMember("Aga");

        Map<Medicine, Dosage> todayTreatment = new HashMap<>();

        Medicine medicine1 = getMedicineWithDefaultValue();
        Medicine medicine2 = getMedicineWithDefaultValue();
        medicine2.setName("gripex");
        medicine2.setType(Type.SYRUP);
        medicine2.setContents(200);
        medicine2.setTermOfValidity(LocalDate.of(2023, 12, 30));
        medicine2.setAllowedDurationOfUse(Period.of(0, 6, 0));

        Dosage dosage1 = getDosageWithDefaultValue();
        dosage1.setStartOfTreatment(LocalDate.of(2023, 9, 16));
        Dosage dosage2 = new Dosage(3, 10, Period.of(0, 0, 3));
        dosage2.setStartOfTreatment(LocalDate.of(2023, 9, 16));


        todayTreatment.put(medicine1, dosage1);
        todayTreatment.put(medicine2, dosage2);

        Map<Medicine, Dosage> updatedTreatment =
                service.updateTreatment(familyMember, todayTreatment, LocalDate.of(2023, 9, 20));

        assertThat(updatedTreatment).size().isEqualTo(1);
    }

    @Test
    public void should_update_treatment_list_when_treatment_is_continue_should_return_the_same_list() {
        FamilyMember familyMember = new FamilyMember("Aga");

        Map<Medicine, Dosage> todayTreatment = new HashMap<>();

        Medicine medicine1 = getMedicineWithDefaultValue();
        Medicine medicine2 = getMedicineWithDefaultValue();
        medicine2.setName("gripex");
        medicine2.setType(Type.SYRUP);
        medicine2.setContents(200);
        medicine2.setTermOfValidity(LocalDate.of(2023, 12, 30));
        medicine2.setAllowedDurationOfUse(Period.of(0, 6, 0));

        Dosage dosage1 = getDosageWithDefaultValue();
        dosage1.setStartOfTreatment(LocalDate.of(2023, 9, 19));
        Dosage dosage2 = new Dosage(3, 10, Period.of(0, 0, 3));
        dosage2.setStartOfTreatment(LocalDate.of(2023, 9, 19));

        todayTreatment.put(medicine1, dosage1);
        todayTreatment.put(medicine2, dosage2);

        Map<Medicine, Dosage> updatedTreatment =
                service.updateTreatment(familyMember, todayTreatment, LocalDate.of(2023, 9, 20));

        assertThat(updatedTreatment).size().isEqualTo(2);
    }


}