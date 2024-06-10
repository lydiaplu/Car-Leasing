package com.lydiaplullc.CarLeasing.controller;

import com.lydiaplullc.CarLeasing.exception.ResourceNotFoundException;
import com.lydiaplullc.CarLeasing.model.CarType;
import com.lydiaplullc.CarLeasing.response.CarTypeResponse;
import com.lydiaplullc.CarLeasing.service.CarTypeService;
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
@RequestMapping("/cartypes")
public class CarTypeController {
    private final CarTypeService carTypeService;

    @PostMapping("/add/new-cartype")
    public ResponseEntity<CarTypeResponse> addCarType(
            @RequestParam String typeName
    ) throws SQLException {
        CarType savedCarType = carTypeService.addCarType(typeName);
        CarTypeResponse carTypeResponse = getCarTypeResponse(savedCarType);
        return ResponseEntity.ok(carTypeResponse);
    }

    @PutMapping("/update/{carTypeId}")
    public ResponseEntity<CarTypeResponse> updateCarType(
            @PathVariable Long carTypeId,
            @RequestParam String typeName
    ) throws SQLException {
        CarType carType = carTypeService.updateCarType(carTypeId, typeName);
        CarTypeResponse carTypeResponse = getCarTypeResponse(carType);
        return ResponseEntity.ok(carTypeResponse);
    }

    @GetMapping("/cartype/{carTypeId}")
    public ResponseEntity<Optional<CarTypeResponse>> getCarTypeById(
            @PathVariable Long carTypeId
    ) {
        Optional<CarType> theCarType = carTypeService.getCarTypeById(carTypeId);
        return theCarType.map(carType -> {
            CarTypeResponse carTypeResponse = getCarTypeResponse(carType);
            return ResponseEntity.ok(Optional.of(carTypeResponse));
        }).orElseThrow(() -> new ResourceNotFoundException("Car Type not found"));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CarTypeResponse>> getAllCarTypes() throws SQLException {
        List<CarType> carTypes = carTypeService.getAllCarTypes();

        List<CarTypeResponse> carTypeResponses = new ArrayList<>();
        for (CarType carType : carTypes) {
            CarTypeResponse carTypeResponse = getCarTypeResponse(carType);
            carTypeResponses.add(carTypeResponse);
        }

        return ResponseEntity.ok(carTypeResponses);
    }

    @DeleteMapping("/delete/{carTypeId}")
    public ResponseEntity<Void> deleteCarType(
            @PathVariable Long carTypeId
    ) {
        carTypeService.deleteCarType(carTypeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public static CarTypeResponse getCarTypeResponse(CarType carType) {
        return new CarTypeResponse(
                carType.getId(),
                carType.getTypeName()
        );
    }
}
