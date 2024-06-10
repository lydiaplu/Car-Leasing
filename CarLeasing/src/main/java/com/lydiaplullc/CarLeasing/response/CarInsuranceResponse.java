package com.lydiaplullc.CarLeasing.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarInsuranceResponse {
    private Long id;
    private String insuranceCompany;
    private String insuranceAmount;
    private String effectiveDate;
    private String expirationDate;
    private String autoRenewalDate;
    private String premium;
    private String description;
    private CarResponse car;

    public CarInsuranceResponse(
            Long id,
            String insuranceCompany,
            BigDecimal insuranceAmount,
            LocalDate effectiveDate,
            LocalDate expirationDate,
            LocalDate autoRenewalDate,
            BigDecimal premium,
            String description,
            CarResponse car
    ) {
        this.id = id;
        this.insuranceCompany = insuranceCompany != null ? insuranceCompany : "";
        this.insuranceAmount = insuranceAmount != null ? String.valueOf(insuranceAmount) : "";
        this.effectiveDate = effectiveDate != null ? String.valueOf(effectiveDate) : "";
        this.expirationDate = expirationDate != null ? String.valueOf(expirationDate) : "";
        this.autoRenewalDate = autoRenewalDate != null ? String.valueOf(autoRenewalDate) : "";
        this.premium = premium != null ? String.valueOf(premium) : "";
        this.description = description != null ? description : "";
        this.car = car;
    }
}
