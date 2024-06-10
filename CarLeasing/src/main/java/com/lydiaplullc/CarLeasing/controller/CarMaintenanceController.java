package com.lydiaplullc.CarLeasing.controller;

import com.lydiaplullc.CarLeasing.exception.ResourceNotFoundException;
import com.lydiaplullc.CarLeasing.model.CarMaintenance;
import com.lydiaplullc.CarLeasing.request.CarMaintenanceRequest;
import com.lydiaplullc.CarLeasing.response.CarMaintenanceResponse;
import com.lydiaplullc.CarLeasing.response.CarResponse;
import com.lydiaplullc.CarLeasing.service.CarMaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/maintenances")
public class CarMaintenanceController {
    private final CarMaintenanceService carMaintenanceService;

    @PostMapping("/add/new-maintenance")
    public ResponseEntity<CarMaintenanceResponse> addCarMaintenance(
            @RequestBody CarMaintenanceRequest carMaintenanceRequest
    ) throws SQLException {
        CarMaintenance savedCarMaintenance = carMaintenanceService.addCarMaintenance(carMaintenanceRequest);
        CarMaintenanceResponse carMaintenanceResponse = getCarMaintenanceResponse(savedCarMaintenance);
        return ResponseEntity.ok(carMaintenanceResponse);
    }

    @PutMapping("/update/{carMaintenanceId}")
    public ResponseEntity<CarMaintenanceResponse> updateCarMaintenance(
            @PathVariable Long carMaintenanceId,
            @RequestBody CarMaintenanceRequest carMaintenanceRequest
    ) throws SQLException {
        CarMaintenance savedCarMaintenance = carMaintenanceService.updateCarMaintenance(carMaintenanceId, carMaintenanceRequest);
        CarMaintenanceResponse carMaintenanceResponse = getCarMaintenanceResponse(savedCarMaintenance);
        return ResponseEntity.ok(carMaintenanceResponse);
    }

    @GetMapping("/maintenance/{carMaintenanceId}")
    public ResponseEntity<Optional<CarMaintenanceResponse>> getCarMaintenanceById(
            @PathVariable Long carMaintenanceId
    ) throws SQLException {
        Optional<CarMaintenance> theCarMaintenance = carMaintenanceService.getCarMaintenanceById(carMaintenanceId);
        return theCarMaintenance.map(carMaintenance -> {
            CarMaintenanceResponse carMaintenanceResponse = getCarMaintenanceResponse(carMaintenance);
            return ResponseEntity.ok(Optional.of(carMaintenanceResponse));
        }).orElseThrow(()->new ResourceNotFoundException("Car Maintenance not found"));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CarMaintenanceResponse>> getAllCarMaintenances() throws SQLException {
        List<CarMaintenance> carMaintenances = carMaintenanceService.getAllCarMaintenances();

        List<CarMaintenanceResponse> carMaintenanceResponses = new ArrayList<>();
        for(CarMaintenance carMaintenance : carMaintenances) {
            CarMaintenanceResponse carMaintenanceResponse = getCarMaintenanceResponse(carMaintenance);
            carMaintenanceResponses.add(carMaintenanceResponse);
        }

        return ResponseEntity.ok(carMaintenanceResponses);
    }

    @DeleteMapping("/delete/{carMaintenanceId}")
    public ResponseEntity<Void> deleteCarMaintenance(@PathVariable Long carMaintenanceId) {
        carMaintenanceService.deleteCarMaintenance(carMaintenanceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(params = "carId")
    public ResponseEntity<List<CarMaintenanceResponse>> getCarMaintenancesByCarId(
            @RequestParam Long carId
    ) throws SQLException{
        List<CarMaintenance> carMaintenances = carMaintenanceService.getCarMaintenancesByCarId(carId).get();

        List<CarMaintenanceResponse> carMaintenanceResponses = new ArrayList<>();
        for(CarMaintenance carMaintenance : carMaintenances) {
            CarMaintenanceResponse carMaintenanceResponse = getCarMaintenanceResponse(carMaintenance);
            carMaintenanceResponses.add(carMaintenanceResponse);
        }

        return ResponseEntity.ok(carMaintenanceResponses);
    }

    @GetMapping(params = "licensePlate")
    public ResponseEntity<List<CarMaintenanceResponse>> getCarMaintenanceByLicensePlate(
            @RequestParam String licensePlate
    ) throws SQLException {
        List<CarMaintenance> carMaintenances = carMaintenanceService.getCarMaintenancesByLicensePlate(licensePlate).get();

        List<CarMaintenanceResponse> carMaintenanceResponses = new ArrayList<>();
        for(CarMaintenance carMaintenance:carMaintenances) {
            CarMaintenanceResponse carMaintenanceResponse = getCarMaintenanceResponse(carMaintenance);
            carMaintenanceResponses.add(carMaintenanceResponse);
        }

        return ResponseEntity.ok(carMaintenanceResponses);
    }

    public static CarMaintenanceResponse getCarMaintenanceResponse(CarMaintenance carMaintenance) {
        CarResponse theCarResponse = CarController.getCarResponse(carMaintenance.getCar());

        return new CarMaintenanceResponse(
                carMaintenance.getId(),
                carMaintenance.getMaintenanceDate(),
                carMaintenance.getCost(),
                carMaintenance.getDescription(),
                theCarResponse
        );
    }
}
