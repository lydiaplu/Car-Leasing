package com.lydiaplullc.CarLeasing.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarMaintenanceRequest {
    private LocalDate maintenanceDate;
    private BigDecimal cost;
    private String description;
    private Long carId;
}
