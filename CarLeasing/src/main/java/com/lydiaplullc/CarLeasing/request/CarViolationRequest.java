package com.lydiaplullc.CarLeasing.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarViolationRequest {
    private LocalDate violationDate;
    private String violationLocation;
    private BigDecimal fineAmount;
    private String description;
    private Long carId;
}
