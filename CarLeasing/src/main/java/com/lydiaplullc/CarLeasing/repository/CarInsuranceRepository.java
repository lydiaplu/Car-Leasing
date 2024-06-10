package com.lydiaplullc.CarLeasing.repository;

import com.lydiaplullc.CarLeasing.model.CarInsurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarInsuranceRepository extends JpaRepository<CarInsurance, Long> {
    @Query("SELECT ci FROM CarInsurance ci WHERE ci.car.id = :carId")
    List<CarInsurance> findCarInsurancesByCarId(Long carId);

    @Query("SELECT ci FROM CarInsurance ci WHERE ci.car.licensePlate = :licensePlate")
    List<CarInsurance> findCarInsurancesByLicensePlate(String licensePlate);
}
