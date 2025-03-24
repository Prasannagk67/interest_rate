package com.intrest.model;

import io.quarkus.arc.All;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@All
public class Duration {
    private String startDate;
    private String endDate;
    public LocalDate getParsedStartDate() {
        return LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public LocalDate getParsedEndDate() {
        return LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
