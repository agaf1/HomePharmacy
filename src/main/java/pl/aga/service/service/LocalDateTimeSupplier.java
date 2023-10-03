package pl.aga.service.service;

import java.time.LocalDate;

public class LocalDateTimeSupplier {
    LocalDate get() {
        return LocalDate.now();
    }
}
