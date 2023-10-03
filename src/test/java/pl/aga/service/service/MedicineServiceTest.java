package pl.aga.service.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.aga.repository.MedicineRepository;
import pl.aga.service.domain.Dosage;
import pl.aga.service.domain.Medicine;
import pl.aga.service.domain.Type;
import pl.aga.service.exception.ContentsException;
import pl.aga.service.exception.TermOfValidityException;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class MedicineServiceTest {
    @Mock
    private MedicineRepository medicineRepository;
    @InjectMocks
    private MedicineService medicineService = new MedicineService();


    @Test
    public void should_update_Contents_Of_Medicine_Package_with_daily_consumption() throws Exception {
        Medicine medicine = newMedicine(10);

        Dosage dosage = new Dosage(3, 1, Period.of(0, 0, 5));
        dosage.setStartOfTreatment(LocalDate.of(2023, 9, 19));

        Mockito.when(medicineRepository.findByName(medicine.getName())).thenReturn(Optional.of(medicine));

        medicineService.updateContentsOfMedicinePackage(medicine, dosage);

        assertThat(medicine.getContents()).isEqualTo(7);
    }

    @Test
    public void should_throw_ContentsException_when_try_update_contents_of_medicine_package() {
        Medicine medicine = newMedicine(2);

        Dosage dosage = new Dosage(3, 1, Period.of(0, 0, 5));
        dosage.setStartOfTreatment(LocalDate.of(2023, 9, 19));

        Mockito.when(medicineRepository.findByName(medicine.getName())).thenReturn(Optional.of(medicine));

        assertThatThrownBy(() -> medicineService.updateContentsOfMedicinePackage(medicine, dosage))
                .isInstanceOf(ContentsException.class)
                .hasMessage("This medicine is already used up.");
    }

    @Test
    public void should_throw_TermOfValidityException_when_Term_of_validity_is_closer_than_3_days() {
        Medicine medicine = newMedicine(10);
        medicine.setTermOfValidity(LocalDate.of(2023, 9, 22));
        int days = 3;

        Mockito.when(medicineRepository.findByName(medicine.getName())).thenReturn(Optional.of(medicine));

        assertThatThrownBy(() -> medicineService.checkTermOfValidity(medicine, days))
                .isInstanceOf(TermOfValidityException.class)
                .hasMessage("This medicine is almost expired.");
    }

    @Test
    public void should_check_term_of_validity_with_out_exception() throws Exception {
        Medicine medicine = newMedicine(10);
        medicine.setTermOfValidity(LocalDate.of(2023, 9, 30));
        int days = 3;

        Mockito.when(medicineRepository.findByName(medicine.getName())).thenReturn(Optional.of(medicine));

        medicineService.checkTermOfValidity(medicine, days);

        assertThat(medicine.getTermOfValidity()).isEqualTo(LocalDate.of(2023, 9, 30));
    }

    @Test
    public void should_get_expired_and_empty_medicines() {
        Medicine medicine1 = newMedicine(4);
        Medicine medicine2 = newMedicine(0);
        Medicine medicine3 = newMedicine(10);
        medicine3.setTermOfValidity(LocalDate.of(2023, 8, 1));
        Medicine medicine4 = newMedicine(0);
        medicine4.setTermOfValidity(LocalDate.of(2022, 7, 1));
        Medicine medicine5 = newMedicine(100);

        List<Medicine> medicines = List.of(medicine1, medicine2, medicine3, medicine4, medicine5);

        Mockito.when(medicineRepository.getAll()).thenReturn(medicines);

        List<Medicine> result = medicineService.getExpiredAndEmptyMedicines();

        assertThat(result.size()).isEqualTo(3);
    }

    private static Medicine newMedicine(int contents) {
        Medicine medicine = new Medicine();
        medicine.setName("aspirin");
        medicine.setType(Type.PILLS);
        medicine.setContents(contents);
        medicine.setTermOfValidity(LocalDate.of(2024, 10, 10));
        medicine.setAllowedDurationOfUse(Period.ZERO);
        return medicine;
    }
}