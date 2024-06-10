package com.lydiaplullc.CarLeasing.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarInsuranceRequest {
    private String insuranceCompany;
    private BigDecimal insuranceAmount;
    private LocalDate effectiveDate;
    private LocalDate expirationDate;
    private LocalDate autoRenewalDate;
    private BigDecimal premium;
    private String description;
    private Long carId;
}
