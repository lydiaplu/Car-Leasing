package com.lydiaplullc.CarLeasing.repository;

import com.lydiaplullc.CarLeasing.model.CarViolation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarViolationRepository extends JpaRepository<CarViolation, Long> {
    @Query("SELECT cv FROM CarViolation cv WHERE cv.car.id = :carId")
    List<CarViolation> findCarViolationsByCarId(Long carId);

    @Query("SELECT cv FROM CarViolation cv WHERE cv.car.licensePlate = :licensePlate")
    List<CarViolation> findCarViolationsByLicensePlate(String licensePlate);
}
