package com.lydiaplullc.CarLeasing.controller;

import com.lydiaplullc.CarLeasing.exception.ResourceNotFoundException;
import com.lydiaplullc.CarLeasing.model.CarInsurance;
import com.lydiaplullc.CarLeasing.request.CarInsuranceRequest;
import com.lydiaplullc.CarLeasing.response.CarInsuranceResponse;
import com.lydiaplullc.CarLeasing.response.CarResponse;
import com.lydiaplullc.CarLeasing.service.CarInsuranceService;
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
@RequestMapping("/insurances")
public class CarInsuranceController {
    private final CarInsuranceService carInsuranceService;

    @PostMapping("/add/new-insurance")
    public ResponseEntity<CarInsuranceResponse> addCarInsurance(
            @RequestBody CarInsuranceRequest carInsuranceRequest
    ) throws SQLException {
        CarInsurance savedCarInsurance = carInsuranceService.addCarInsurance(carInsuranceRequest);
        CarInsuranceResponse carInsuranceResponse = getCarInsuranceResponse(savedCarInsurance);
        return ResponseEntity.ok(carInsuranceResponse);
    }

    @PutMapping("/update/{carInsuranceId}")
    public ResponseEntity<CarInsuranceResponse> updateCarInsurance(
            @PathVariable Long carInsuranceId,
            @RequestBody CarInsuranceRequest carInsuranceRequest
    ) throws SQLException {
        CarInsurance savedCarInsurance = carInsuranceService.updateCarInsurance(carInsuranceId, carInsuranceRequest);
        CarInsuranceResponse carInsuranceResponse = getCarInsuranceResponse(savedCarInsurance);
        return ResponseEntity.ok(carInsuranceResponse);
    }

    @GetMapping("/insurance/{carInsuranceId}")
    public ResponseEntity<Optional<CarInsuranceResponse>> getCarInsuranceById(
            @PathVariable Long carInsuranceId
    ) throws SQLException {
        Optional<CarInsurance> theCarInsurance = carInsuranceService.getCarInsuranceById(carInsuranceId);
        return theCarInsurance.map(carInsurance -> {
            CarInsuranceResponse carInsuranceResponse = getCarInsuranceResponse(carInsurance);
            return ResponseEntity.ok(Optional.of(carInsuranceResponse));
        }).orElseThrow(()->new ResourceNotFoundException("Car Insurance not found"));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CarInsuranceResponse>> getAllCarInsurances() throws SQLException{
        List<CarInsurance> carInsurances = carInsuranceService.getAllCarInsurances();

        List<CarInsuranceResponse> carInsuranceResponses = new ArrayList<>();
        for(CarInsurance carInsurance: carInsurances) {
            CarInsuranceResponse carInsuranceResponse = getCarInsuranceResponse(carInsurance);
            carInsuranceResponses.add(carInsuranceResponse);
        }

        return ResponseEntity.ok(carInsuranceResponses);
    }

    @DeleteMapping("/delete/{carInsuranceId}")
    public ResponseEntity<Void> deleteCarInsurance(@PathVariable Long carInsuranceId) {
        carInsuranceService.deleteCarInsurance(carInsuranceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(params = "carId")
    public ResponseEntity<List<CarInsuranceResponse>> getCarInsurancesByCarId(
            @RequestParam Long carId
    ) throws SQLException {
        List<CarInsurance> carInsurances = carInsuranceService.getCarInsurancesByCarId(carId).get();

        List<CarInsuranceResponse> carInsuranceResponses = new ArrayList<>();
        for(CarInsurance carInsurance:carInsurances) {
            CarInsuranceResponse carInsuranceResponse = getCarInsuranceResponse(carInsurance);
            carInsuranceResponses.add(carInsuranceResponse);
        }

        return ResponseEntity.ok(carInsuranceResponses);
    }

    @GetMapping(params = "licensePlate")
    public ResponseEntity<List<CarInsuranceResponse>> getCarInsurancesByLicensePlate(
            @RequestParam String licensePlate
    ) {
        List<CarInsurance> carInsurances = carInsuranceService.getCarInsurancesByLicensePlate(licensePlate).get();

        List<CarInsuranceResponse> carInsuranceResponses = new ArrayList<>();
        for(CarInsurance carInsurance: carInsurances) {
            CarInsuranceResponse carInsuranceResponse = getCarInsuranceResponse(carInsurance);
            carInsuranceResponses.add(carInsuranceResponse);
        }

        return ResponseEntity.ok(carInsuranceResponses);
    }

    public static CarInsuranceResponse getCarInsuranceResponse(CarInsurance carInsurance) {
        CarResponse theCarResponse = CarController.getCarResponse(carInsurance.getCar());

        return new CarInsuranceResponse(
                carInsurance.getId(),
                carInsurance.getInsuranceCompany(),
                carInsurance.getInsuranceAmount(),
                carInsurance.getEffectiveDate(),
                carInsurance.getExpirationDate(),
                carInsurance.getAutoRenewalDate(),
                carInsurance.getPremium(),
                carInsurance.getDescription(),
                theCarResponse
        );
    }
}
