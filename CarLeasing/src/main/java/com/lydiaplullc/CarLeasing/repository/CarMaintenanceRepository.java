package com.lydiaplullc.CarLeasing.repository;

import com.lydiaplullc.CarLeasing.model.CarMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarMaintenanceRepository extends JpaRepository<CarMaintenance, Long> {
    @Query("SELECT cm FROM CarMaintenance cm WHERE cm.car.id = :carId")
    List<CarMaintenance> findCarMaintenancesByCarId(Long carId);

    @Query("SELECT cm FROM CarMaintenance cm WHERE cm.car.licensePlate = :licensePlate")
    List<CarMaintenance> findCarMaintenancesByLicensePlate(String licensePlate);
}
