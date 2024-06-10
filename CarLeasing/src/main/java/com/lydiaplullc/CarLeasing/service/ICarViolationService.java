package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.model.CarViolation;
import com.lydiaplullc.CarLeasing.request.CarViolationRequest;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ICarViolationService {
    CarViolation addCarViolation(CarViolationRequest carViolationRequest) throws SQLException;

    CarViolation updateCarViolation(Long carViolationId, CarViolationRequest carViolationRequest) throws SQLException;

    Optional<CarViolation> getCarViolationById(Long carViolationId);

    void deleteCarViolation(Long carViolationId);

    Optional<List<CarViolation>> getCarViolationsByCarId(Long carId);

    Optional<List<CarViolation>> getCarViolationsByLicensePlate(String licensePlate);

    List<CarViolation> getAllCarViolations();
}
