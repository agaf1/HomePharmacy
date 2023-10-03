package pl.aga.repository.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.aga.service.domain.Medicine;
import pl.aga.service.domain.Type;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

class MedicineCollectionTest {

    private MedicineCollection medicineCollection = new MedicineCollection();

    @Test
    public void should_save_one_medicine_to_list_of_medicines() {
        Medicine medicine = getMedicine();

        medicineCollection.save(medicine);

        Assertions.assertEquals(100, medicineCollection.medicines.get(0).getContents());
    }

    @Test
    public void should_find_medicine_from_Collection_by_name() {
        Medicine medicine = getMedicine();
        MedicineCollection newMedicines = medicineCollection.of(medicine);
        newMedicines.getMedicines().add(medicineCollection.of(medicine));

        Medicine foundedMedicine = newMedicines.findById(1).get();

        Assertions.assertEquals(100, foundedMedicine.getContents());
    }

    @Test
    public void should_return_empty_optional_when_not_find_this_medicine() {
        Medicine medicine = getMedicine();
        MedicineCollection newMedicines = medicineCollection.of(medicine);
        newMedicines.getMedicines().add(medicineCollection.of(medicine));

        Optional<Medicine> result = newMedicines.findById(2);

        Assertions.assertEquals(Optional.empty(), result);
    }

    @Test
    public void should_remove_medicine_from_collection() {
        Medicine medicine = getMedicine();
        MedicineCollection newMedicines = medicineCollection.of(medicine);
        newMedicines.getMedicines().add(medicineCollection.of(medicine));

        newMedicines.delete(medicine);

        Assertions.assertEquals(0, newMedicines.medicines.size());
    }

    @Test
    public void should_not_remove_when_not_find_medicine_in_collection() {
        Medicine medicine = getMedicine();
        MedicineCollection newMedicines = medicineCollection.of(medicine);
        newMedicines.getMedicines().add(medicineCollection.of(medicine));

        Medicine medicine1 = getMedicine();
        medicine1.setId(2);
        medicine1.setName("gripex");

        newMedicines.delete(medicine1);

        Assertions.assertEquals(1, newMedicines.medicines.size());
    }

    @Test
    public void should_get_all_medicines_from_collection() {
        Medicine medicine = getMedicine();
        MedicineCollection newMedicines = medicineCollection.of(medicine);
        Medicine medicine1 = getMedicine();
        medicine1.setId(2);
        medicine1.setName("gripex");

        newMedicines.getMedicines().add(medicineCollection.of(medicine));
        newMedicines.getMedicines().add(medicineCollection.of(medicine1));

        List<Medicine> result = newMedicines.getAll();

        Assertions.assertEquals(2, result.size());
    }

    private static Medicine getMedicine() {
        Medicine medicine = new Medicine();
        medicine.setId(1);
        medicine.setName("aspirin");
        medicine.setContents(100);
        medicine.setType(Type.PILLS);
        medicine.setTermOfValidity(LocalDate.of(2024, 8, 30));
        medicine.setAllowedDurationOfUse(Period.ZERO);
        return medicine;
    }
}