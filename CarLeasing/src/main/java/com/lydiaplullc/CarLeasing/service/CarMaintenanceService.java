package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.model.Car;
import com.lydiaplullc.CarLeasing.model.CarMaintenance;
import com.lydiaplullc.CarLeasing.repository.CarMaintenanceRepository;
import com.lydiaplullc.CarLeasing.request.CarMaintenanceRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarMaintenanceService implements ICarMaintenanceService {
    private final CarMaintenanceRepository carMaintenanceRepository;
    private final CarService carService;

    @Override
    public CarMaintenance addCarMaintenance(CarMaintenanceRequest carMaintenanceRequest) throws SQLException {
        CarMaintenance carMaintenance = new CarMaintenance();
        setCarMaintenanceModel(carMaintenance, carMaintenanceRequest);
        return carMaintenanceRepository.save(carMaintenance);
    }

    @Override
    public CarMaintenance updateCarMaintenance(Long carMaintenanceId, CarMaintenanceRequest carMaintenanceRequest) throws SQLException {
        CarMaintenance carMaintenance = getCarMaintenanceById(carMaintenanceId).get();
        setCarMaintenanceModel(carMaintenance, carMaintenanceRequest);
        return carMaintenanceRepository.save(carMaintenance);
    }

    @Override
    public Optional<CarMaintenance> getCarMaintenanceById(Long carMaintenanceId) {
        return carMaintenanceRepository.findById(carMaintenanceId);
    }

    @Override
    public void deleteCarMaintenance(Long carMaintenanceId) {
        Optional<CarMaintenance> theCarMaintenance = carMaintenanceRepository.findById(carMaintenanceId);
        if (theCarMaintenance.isPresent()) {
            carMaintenanceRepository.deleteById(carMaintenanceId);
            System.out.println("");
        }
    }

    @Override
    public Optional<List<CarMaintenance>> getCarMaintenancesByCarId(Long carId) {
        List<CarMaintenance> theCarMaintenances = carMaintenanceRepository.findCarMaintenancesByCarId(carId);
        return Optional.ofNullable(theCarMaintenances);
    }

    @Override
    public Optional<List<CarMaintenance>> getCarMaintenancesByLicensePlate(String licensePlate) {
        List<CarMaintenance> theCarMaintenances = carMaintenanceRepository.findCarMaintenancesByLicensePlate(licensePlate);
        return Optional.ofNullable(theCarMaintenances);
    }

    @Override
    public List<CarMaintenance> getAllCarMaintenances() {
        return carMaintenanceRepository.findAll();
    }

    private void setCarMaintenanceModel(CarMaintenance carMaintenance, CarMaintenanceRequest carMaintenanceRequest) {
        Car theCar = carService.getCarById(carMaintenanceRequest.getCarId())
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id " + carMaintenanceRequest.getCarId()));

        carMaintenance.setMaintenanceDate(carMaintenanceRequest.getMaintenanceDate());
        carMaintenance.setCost(carMaintenanceRequest.getCost());
        carMaintenance.setDescription(carMaintenanceRequest.getDescription());
        carMaintenance.setCar(theCar);

        if (carMaintenance.getId() == null) {
            theCar.addMaintenance(carMaintenance);
        }
    }
}
