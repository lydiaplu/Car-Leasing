package com.lydiaplullc.CarLeasing.repository;

import com.lydiaplullc.CarLeasing.model.CarPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarPictureRepository extends JpaRepository<CarPicture, Long> {
    @Query("SELECT cp FROM CarPicture cp WHERE cp.id = :carPictureId AND cp.car.id = :carId")
    CarPicture findCarPictureByIdAndPictureId(Long carId, Long carPictureId);
}
