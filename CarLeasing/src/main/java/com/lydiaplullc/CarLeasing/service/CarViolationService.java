package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.model.Car;
import com.lydiaplullc.CarLeasing.model.CarViolation;
import com.lydiaplullc.CarLeasing.repository.CarViolationRepository;
import com.lydiaplullc.CarLeasing.request.CarViolationRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarViolationService implements ICarViolationService {
    private final CarViolationRepository carViolationRepository;
    private final CarService carService;

    @Override
    public CarViolation addCarViolation(CarViolationRequest carViolationRequest) throws SQLException {
        CarViolation carViolation = new CarViolation();
        setCarViolationModel(carViolation, carViolationRequest);
        return carViolationRepository.save(carViolation);
    }

    @Override
    public CarViolation updateCarViolation(Long carViolationId, CarViolationRequest carViolationRequest) throws SQLException {
        CarViolation carViolation = getCarViolationById(carViolationId).get();
        setCarViolationModel(carViolation, carViolationRequest);
        return carViolationRepository.save(carViolation);
    }

    @Override
    public Optional<CarViolation> getCarViolationById(Long carViolationId) {
        return carViolationRepository.findById(carViolationId);
    }

    @Override
    public void deleteCarViolation(Long carViolationId) {
        Optional<CarViolation> theCarViolation = getCarViolationById(carViolationId);
        if (theCarViolation.isPresent()) {
            carViolationRepository.deleteById(carViolationId);
        }
    }

    @Override
    public Optional<List<CarViolation>> getCarViolationsByCarId(Long carId) {
        List<CarViolation> theCarViolations = carViolationRepository.findCarViolationsByCarId(carId);
        return Optional.ofNullable(theCarViolations);
    }

    @Override
    public Optional<List<CarViolation>> getCarViolationsByLicensePlate(String licensePlate) {
        List<CarViolation> theCarViolations = carViolationRepository.findCarViolationsByLicensePlate(licensePlate);
        return Optional.ofNullable(theCarViolations);
    }

    @Override
    public List<CarViolation> getAllCarViolations() {
        return carViolationRepository.findAll();
    }

    private void setCarViolationModel(CarViolation carViolation, CarViolationRequest carViolationRequest) {
        Car theCar = carService.getCarById(carViolationRequest.getCarId())
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id " + carViolationRequest.getCarId()));

        carViolation.setViolationDate(carViolationRequest.getViolationDate());
        carViolation.setViolationLocation(carViolationRequest.getViolationLocation());
        carViolation.setFineAmount(carViolationRequest.getFineAmount());
        carViolation.setDescription(carViolationRequest.getDescription());
        carViolation.setCar(theCar);

        if (carViolation.getId() == null) {
            theCar.addViolation(carViolation);
        }
    }
}
