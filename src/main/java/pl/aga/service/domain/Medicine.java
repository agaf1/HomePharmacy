package pl.aga.service.domain;

import lombok.Data;

import java.time.LocalDate;
import java.time.Period;

@Data
public class Medicine {

    private String name;
    private Type type;
    private double contents;
    private LocalDate termOfValidity;
    private Period allowedDurationOfUse;
}
