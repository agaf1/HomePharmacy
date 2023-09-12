package pl.aga.service.domain;

import lombok.Value;

import java.time.LocalDate;
import java.time.Period;

@Value
public class Medicine {

    private final String name;
    private final Contents contents;
    private final LocalDate termOfValidity;
    private final Period allowedDurationOfUse;
}
