package com.lydiaplullc.CarLeasing.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarResponse {
    private Long id;
    private String model;
    private String year;
    private String color;
    private String licensePlate;
    private String seats;
    private String mileage;
    private String fuelType;
    private String price;
    private String AWD;
    private String leatherTrimmedUpholstery;
    private String moonroof;
    private String RAB;
    private String blindSpotAssist;
    private String keylessEntrySystem;
    private String engineDisplacement;
    private String available;
    private String description;
    private CarTypeResponse carType;
    private CarBrandResponse carBrand;
    private List<CarPictureResponse> carPicture;

    public CarResponse(Long id, String model, Integer year, String color, String licensePlate, Integer seats,
                       BigDecimal mileage, String fuelType, BigDecimal price, Boolean AWD,
                       Boolean leatherTrimmedUpholstery, Boolean moonroof, Boolean RAB, Boolean blindSpotAssist,
                       Boolean keylessEntrySystem, Double engineDisplacement, Boolean available, String description,
                       CarTypeResponse carType, CarBrandResponse carBrand, List<CarPictureResponse> carPicture) {
        this.id = id;
        this.model = model;
        this.year = String.valueOf(year); // Integer 转 String
        this.color = color;
        this.licensePlate = licensePlate;
        this.seats = String.valueOf(seats); // Integer 转 String
        this.mileage = String.valueOf(mileage); // BigDecimal 转 String
        this.fuelType = fuelType;
        this.price = String.valueOf(price); // BigDecimal 转 String
        this.AWD = AWD != null ? String.valueOf(AWD) : "";
        this.leatherTrimmedUpholstery = leatherTrimmedUpholstery != null ? String.valueOf(leatherTrimmedUpholstery) : "";
        this.moonroof = moonroof != null ? String.valueOf(moonroof) : "";
        this.RAB = RAB != null ? String.valueOf(RAB) : "";
        this.blindSpotAssist = blindSpotAssist != null ? String.valueOf(blindSpotAssist) : "";
        this.keylessEntrySystem = keylessEntrySystem != null ? String.valueOf(keylessEntrySystem) : "";
        this.engineDisplacement = engineDisplacement != null ? String.valueOf(engineDisplacement) : "";
        this.available = available != null ? String.valueOf(available) : "";
        this.description = description != null ? String.valueOf(description) : "";
        this.carType = carType;
        this.carBrand = carBrand;
        this.carPicture = carPicture;
    }
}