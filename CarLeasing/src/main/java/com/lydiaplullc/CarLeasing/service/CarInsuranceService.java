package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.model.Car;
import com.lydiaplullc.CarLeasing.model.CarInsurance;
import com.lydiaplullc.CarLeasing.repository.CarInsuranceRepository;
import com.lydiaplullc.CarLeasing.request.CarInsuranceRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarInsuranceService implements ICarInsuranceService {
    private final CarInsuranceRepository carInsuranceRepository;
    private final CarService carService;

    @Override
    public CarInsurance addCarInsurance(CarInsuranceRequest carInsuranceRequest) throws SQLException {
        CarInsurance carInsurance = new CarInsurance();
        setCarInsuranceModel(carInsurance, carInsuranceRequest);
        return carInsuranceRepository.save(carInsurance);
    }

    @Override
    public CarInsurance updateCarInsurance(Long carInsuranceId, CarInsuranceRequest carInsuranceRequest) throws SQLException {
        CarInsurance carInsurance = getCarInsuranceById(carInsuranceId).get();
        setCarInsuranceModel(carInsurance, carInsuranceRequest);
        return carInsuranceRepository.save(carInsurance);
    }

    @Override
    public Optional<CarInsurance> getCarInsuranceById(Long carInsuranceId) {
        return Optional.of(carInsuranceRepository.findById(carInsuranceId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id " + carInsuranceId))
        );
    }

    @Override
    public void deleteCarInsurance(Long carInsuranceId) {
        Optional<CarInsurance> theCarInsurance = getCarInsuranceById(carInsuranceId);
        if (theCarInsurance.isPresent()) {
            carInsuranceRepository.deleteById(carInsuranceId);
        }
    }

    @Override
    public Optional<List<CarInsurance>> getCarInsurancesByCarId(Long carId) {
        List<CarInsurance> theCarInsurances = carInsuranceRepository.findCarInsurancesByCarId(carId);
        return Optional.ofNullable(theCarInsurances);
    }

    @Override
    public Optional<List<CarInsurance>> getCarInsurancesByLicensePlate(String licensePlate) {
        List<CarInsurance> theCarInsurances = carInsuranceRepository.findCarInsurancesByLicensePlate(licensePlate);
        return Optional.ofNullable(theCarInsurances);
    }

    @Override
    public List<CarInsurance> getAllCarInsurances() {
        return carInsuranceRepository.findAll();
    }

    private void setCarInsuranceModel(CarInsurance carInsurance, CarInsuranceRequest carInsuranceRequest) {
        Car theCar = carService.getCarById(carInsuranceRequest.getCarId())
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id " + carInsuranceRequest.getCarId()));

        carInsurance.setInsuranceCompany(carInsuranceRequest.getInsuranceCompany());
        carInsurance.setInsuranceAmount(carInsuranceRequest.getInsuranceAmount());
        carInsurance.setEffectiveDate(carInsuranceRequest.getEffectiveDate());
        carInsurance.setExpirationDate(carInsuranceRequest.getExpirationDate());
        carInsurance.setAutoRenewalDate(carInsuranceRequest.getAutoRenewalDate());
        carInsurance.setPremium(carInsuranceRequest.getPremium());
        carInsurance.setDescription(carInsuranceRequest.getDescription());
        carInsurance.setCar(theCar);

        if (carInsurance.getId() == null) {
            theCar.addInsurance(carInsurance);
        }
    }
}
