package com.lydiaplullc.CarLeasing.controller;

import com.lydiaplullc.CarLeasing.exception.ResourceNotFoundException;
import com.lydiaplullc.CarLeasing.model.CarViolation;
import com.lydiaplullc.CarLeasing.request.CarViolationRequest;
import com.lydiaplullc.CarLeasing.response.CarResponse;
import com.lydiaplullc.CarLeasing.response.CarViolationResponse;
import com.lydiaplullc.CarLeasing.service.CarViolationService;
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
@RequestMapping("/violations")
public class CarViolationController {
    private final CarViolationService carViolationService;

    @PostMapping("/add/new-violation")
    public ResponseEntity<CarViolationResponse> addCarViolation(
            @RequestBody CarViolationRequest carViolationRequest
    ) throws SQLException {
        CarViolation savedCarViolation = carViolationService.addCarViolation(carViolationRequest);
        CarViolationResponse carViolationResponse = getCarViolationResponse(savedCarViolation);
        return ResponseEntity.ok(carViolationResponse);
    }

    @PutMapping("/update/{carViolationId}")
    public ResponseEntity<CarViolationResponse> updateCarViolation(
            @PathVariable Long carViolationId,
            @RequestBody CarViolationRequest carViolationRequest
    ) throws SQLException {
        CarViolation savedCarViolation = carViolationService.updateCarViolation(carViolationId, carViolationRequest);
        CarViolationResponse carViolationResponse = getCarViolationResponse(savedCarViolation);
        return ResponseEntity.ok(carViolationResponse);
    }

    @GetMapping("/violation/{carViolationId}")
    public ResponseEntity<Optional<CarViolationResponse>> getCarViolationById(
            @PathVariable Long carViolationId
    ) throws SQLException {
        Optional<CarViolation> theCarViolation = carViolationService.getCarViolationById(carViolationId);
        return theCarViolation.map(carViolation -> {
            CarViolationResponse carViolationResponse = getCarViolationResponse(carViolation);
            return ResponseEntity.ok(Optional.of(carViolationResponse));
        }).orElseThrow(()->new ResourceNotFoundException("Car Violation not found"));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CarViolationResponse>> getAllCarViolations() throws SQLException{
        List<CarViolation> carViolations = carViolationService.getAllCarViolations();

        List<CarViolationResponse> carViolationResponses = new ArrayList<>();
        for(CarViolation carViolation : carViolations) {
            CarViolationResponse carViolationResponse = getCarViolationResponse(carViolation);
            carViolationResponses.add(carViolationResponse);
        }

        return ResponseEntity.ok(carViolationResponses);
    }

    @DeleteMapping("/delete/{carViolationId}")
    public ResponseEntity<Void> deleteCarViolation(@PathVariable Long carViolationId) {
        carViolationService.deleteCarViolation(carViolationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(params = "carId")
    public ResponseEntity<List<CarViolationResponse>> getCarViolationsByCarId(
            @RequestParam Long carId
    ) throws SQLException {
        List<CarViolation> carViolations = carViolationService.getCarViolationsByCarId(carId).get();

        List<CarViolationResponse> carViolationResponses = new ArrayList<>();
        for(CarViolation carViolation : carViolations) {
            CarViolationResponse carViolationResponse = getCarViolationResponse(carViolation);
            carViolationResponses.add(carViolationResponse);
        }

        return ResponseEntity.ok(carViolationResponses);
    }

    @GetMapping(params = "licensePlate")
    public ResponseEntity<List<CarViolationResponse>> getCarViolationsByLicensePlate(
            @RequestParam String licensePlate
    ) throws SQLException {
        List<CarViolation> carViolations = carViolationService.getCarViolationsByLicensePlate(licensePlate).get();

        List<CarViolationResponse> carViolationResponses = new ArrayList<>();
        for(CarViolation carViolation : carViolations) {
            CarViolationResponse carViolationResponse = getCarViolationResponse(carViolation);
            carViolationResponses.add(carViolationResponse);
        }

        return ResponseEntity.ok(carViolationResponses);
    }

    public static CarViolationResponse getCarViolationResponse(CarViolation carViolation) {
        CarResponse carResponse = CarController.getCarResponse(carViolation.getCar());

        return new CarViolationResponse(
                carViolation.getId(),
                carViolation.getViolationDate(),
                carViolation.getViolationLocation(),
                carViolation.getFineAmount(),
                carViolation.getDescription(),
                carResponse
        );
    }
}
