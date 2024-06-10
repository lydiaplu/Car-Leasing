package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.model.CarType;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ICarTypeService {
    CarType addCarType(String typeName) throws SQLException;

    CarType updateCarType(Long carTypeId, String typeName) throws SQLException;

    List<CarType> getAllCarTypes();

    Optional<CarType> getCarTypeById(Long carTypeId);

    void deleteCarType(Long carTypeId);
}
