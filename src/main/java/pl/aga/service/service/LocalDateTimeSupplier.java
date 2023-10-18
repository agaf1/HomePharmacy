package pl.aga.service.service;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LocalDateTimeSupplier {
    LocalDate get() {
        return LocalDate.now();
    }
}
