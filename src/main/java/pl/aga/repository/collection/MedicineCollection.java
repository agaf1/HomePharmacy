//package pl.aga.repository.collection;
//
//import lombok.Data;
//import pl.aga.repository.MedicineRepository;
//import pl.aga.service.domain.Medicine;
//import pl.aga.service.domain.Type;
//
//import java.time.LocalDate;
//import java.time.Period;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Data
//public class MedicineCollection implements MedicineRepository {
//
//    private String name;
//    private Type type;
//    private double contents;
//    private LocalDate termOfValidity;
//    private Period allowedDurationOfUse;
//
//    List<MedicineCollection> medicines = new ArrayList<>();
//
//    Medicine map(MedicineCollection medicineCollection) {
//        Medicine medicine = new Medicine();
//        medicine.setName(medicineCollection.getName());
//        medicine.setType(medicineCollection.getType());
//        medicine.setContents(medicineCollection.getContents());
//        medicine.setTermOfValidity(medicineCollection.getTermOfValidity());
//        medicine.setAllowedDurationOfUse(medicineCollection.getAllowedDurationOfUse());
//        return medicine;
//    }
//
//    MedicineCollection of(Medicine medicine) {
//        MedicineCollection medicineCollection = new MedicineCollection();
//        medicineCollection.setName(medicine.getName());
//        medicineCollection.setType(medicine.getType());
//        medicineCollection.setContents(medicine.getContents());
//        medicineCollection.setTermOfValidity(medicine.getTermOfValidity());
//        medicineCollection.setAllowedDurationOfUse(medicine.getAllowedDurationOfUse());
//
//        return medicineCollection;
//    }
//
//    @Override
//    public void save(Medicine medicine) {
//        medicines.add(this.of(medicine));
//    }
//
//    @Override
//    public Optional<Medicine> findById(Integer id) {
//        Optional<Medicine> medicine = Optional.empty();
//
//        Optional<MedicineCollection> medicineCollection = medicines.stream().filter(m -> m.getName().equals(name)).findAny();
//
//        if (medicineCollection.isPresent()) {
//            medicine = Optional.of(map(medicineCollection.get()));
//        }
//        return medicine;
//    }
//
//    @Override
//    public void delete(Medicine medicine) {
//        if (this.findByName(medicine.getName()).isPresent()) {
//            medicines.remove(this.of(medicine));
//        }
//    }
//
//    @Override
//    public List<Medicine> getAll() {
//        return medicines.stream().map(m -> this.map(m)).collect(Collectors.toList());
//    }
//}
