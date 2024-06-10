package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.model.CarInsurance;
import com.lydiaplullc.CarLeasing.request.CarInsuranceRequest;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ICarInsuranceService {
    CarInsurance addCarInsurance(CarInsuranceRequest carInsuranceRequest) throws SQLException;

    CarInsurance updateCarInsurance(Long carInsuranceId, CarInsuranceRequest carInsuranceRequest) throws SQLException;

    Optional<CarInsurance> getCarInsuranceById(Long carInsuranceId);

    void deleteCarInsurance(Long carInsuranceId);

    Optional<List<CarInsurance>> getCarInsurancesByCarId(Long carId);

    Optional<List<CarInsurance>> getCarInsurancesByLicensePlate(String licensePlate);

    List<CarInsurance> getAllCarInsurances();
}
