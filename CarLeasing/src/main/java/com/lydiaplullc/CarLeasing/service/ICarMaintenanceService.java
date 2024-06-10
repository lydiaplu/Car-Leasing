package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.model.CarMaintenance;
import com.lydiaplullc.CarLeasing.request.CarMaintenanceRequest;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ICarMaintenanceService {
    CarMaintenance addCarMaintenance(CarMaintenanceRequest carMaintenanceRequest) throws SQLException;

    CarMaintenance updateCarMaintenance(Long carMaintenanceId, CarMaintenanceRequest carMaintenanceRequest) throws SQLException;

    Optional<CarMaintenance> getCarMaintenanceById(Long carMaintenanceId);

    void deleteCarMaintenance(Long carMaintenanceId);

    Optional<List<CarMaintenance>> getCarMaintenancesByCarId(Long carId);

    Optional<List<CarMaintenance>> getCarMaintenancesByLicensePlate(String licensePlate);

    List<CarMaintenance> getAllCarMaintenances();
}
