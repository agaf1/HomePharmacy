package pl.aga.service.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.Period;

@EqualsAndHashCode
@Getter
@Setter
@ToString
public class Medicine {

    private Integer id;
    private String name;
    private Type type;
    private double contents;
    private LocalDate termOfValidity;
    private Period allowedDurationOfUse;

    public Medicine() {
    }

    public Medicine(String name, Type type, double contents, LocalDate termOfValidity, Period allowedDurationOfUse) {
        this.name = name;
        this.type = type;
        this.contents = contents;
        this.termOfValidity = termOfValidity;
        this.allowedDurationOfUse = allowedDurationOfUse;
        id = null;
    }
}
