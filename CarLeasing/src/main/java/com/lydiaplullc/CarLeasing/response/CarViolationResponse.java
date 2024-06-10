package com.lydiaplullc.CarLeasing.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarViolationResponse {
    private Long id;
    private String violationDate;
    private String violationLocation;
    private String fineAmount;
    private String description;
    private CarResponse car;

    public CarViolationResponse(
            Long id,
            LocalDate violationDate,
            String violationLocation,
            BigDecimal fineAmount,
            String description,
            CarResponse car
    ) {
        this.id = id;
        this.violationDate = violationDate != null ? String.valueOf(violationDate) : "";
        this.violationLocation = violationDate != null ? String.valueOf(violationLocation) : "";
        this.fineAmount = violationDate != null ? String.valueOf(fineAmount) : "";
        this.description = violationDate != null ? String.valueOf(description) : "";
        this.car = car;
    }
}
