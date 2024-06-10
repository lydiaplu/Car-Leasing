package com.lydiaplullc.CarLeasing.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarMaintenanceResponse {
    private Long id;
    private String maintenanceDate;
    private String cost;
    private String description;
    private CarResponse car;

    public CarMaintenanceResponse(Long id, LocalDate maintenanceDate, BigDecimal cost, String description, CarResponse car) {
        this.id = id;
        this.maintenanceDate = maintenanceDate != null ? String.valueOf(maintenanceDate) : "";
        this.cost = cost != null ? String.valueOf(cost) : "";
        this.description = description != null ? description : "";
        this.car = car;
    }
}
