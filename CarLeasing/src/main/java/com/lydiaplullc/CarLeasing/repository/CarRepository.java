package com.lydiaplullc.CarLeasing.repository;

import com.lydiaplullc.CarLeasing.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("SELECT DISTINCT c.model FROM Car c")
    List<String> findDistinctModels();

    @Query("SELECT DISTINCT c.available FROM Car c")
    List<String> findDistinctAvailable();

    @Query("SELECT DISTINCT c.fuelType FROM Car c")
    List<String> findDistinctFuelType();

    @Query("SELECT c FROM Car c LEFT JOIN FETCH c.pictures WHERE c.id = :id")
    Car findByIdAndFetchPictures(Long id);

    @Query("SELECT c FROM Car c WHERE c.carType.id = :carTypeId")
    List<Car> findCarByCarTypeId(Long carTypeId);

    @Query("SELECT c.licensePlate FROM Car c")
    List<String> findAllLicensePlate();

    @Query("SELECT c FROM Car c WHERE c.licensePlate = :carLicensePlate")
    Car findByLicensePlate(Long carLicensePlate);

    @Query("SELECT c FROM Car c ORDER BY c.carBrand.id, c.model, c.carType.id, c.year, c.color, c.licensePlate")
    List<Car> findAllCarByGroup();
}
