package com.lydiaplullc.CarLeasing.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRequest {
    private String model;
    private Integer year;
    private String color;
    private String licensePlate;
    private Integer seats;
    private BigDecimal mileage;
    private String fuelType;
    private BigDecimal price;
    private Boolean AWD;
    private Boolean leatherTrimmedUpholstery;
    private Boolean moonroof;
    private Boolean RAB;
    private Boolean blindSpotAssist;
    private Boolean keylessEntrySystem;
    private Double engineDisplacement;
    private Boolean available;
    private String description;
    private Long carType;
    private Long carBrand;
}
