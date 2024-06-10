package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.model.Car;
import com.lydiaplullc.CarLeasing.model.CarBrand;
import com.lydiaplullc.CarLeasing.model.CarType;
import com.lydiaplullc.CarLeasing.repository.CarBrandRepository;
import com.lydiaplullc.CarLeasing.repository.CarRepository;
import com.lydiaplullc.CarLeasing.repository.CarTypeRepository;
import com.lydiaplullc.CarLeasing.request.CarRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService implements ICarService{
    private final CarRepository carRepository;
    private final CarTypeRepository carTypeRepository;
    private final CarBrandRepository carBrandRepository;

    @Override
    public Car addCar(CarRequest carRequest) throws SQLException {
        Car car = new Car();
        setCarModel(car, carRequest);
        return carRepository.save(car);
    }

    @Override
    public Car updateCar(Long carId, CarRequest carRequest) throws SQLException {
        Car car = getCarById(carId).get();
        setCarModel(car, carRequest);
        return carRepository.save(car);
    }

    @Override
    public Optional<Car> getCarById(Long carId) {
        return carRepository.findById(carId);
    }

    @Override
    public Optional<Car> getCarByIdAndFetchPictures(Long carId) {
        Car theCar = carRepository.findByIdAndFetchPictures(carId);
        return Optional.ofNullable(theCar);
    }

    @Override
    public Optional<List<Car>> getCarByCarTypeId(Long carTypeId) {
        List<Car> theCars = carRepository.findCarByCarTypeId(carTypeId);
        return Optional.ofNullable(theCars);
    }

    @Override
    public List<Car> getAllCar() {
        return carRepository.findAll(Sort.by("id").ascending());
    }

    @Override
    public List<Car> getAllCarByGroup() {
        return carRepository.findAllCarByGroup();
    }

    @Override
    public void deleteCar(Long carId) {
        Optional<Car> theCar = carRepository.findById(carId);
        if(theCar.isPresent()){
            carRepository.deleteById(carId);
        }
    }

    public List<String> getAllLicensePlate() {
        return carRepository.findAllLicensePlate();
    }

    public Optional<Car> getCarByLicensePlate(Long carLicensePlate) {
        return Optional.of(carRepository.findByLicensePlate(carLicensePlate));
    }

    @Override
    public List<String> getAllModels() {
        return carRepository.findDistinctModels();
    }

    @Override
    public List<String> getAllAvailable() {
        return carRepository.findDistinctAvailable();
    }

    @Override
    public List<String> getAllFuelType() {
        return carRepository.findDistinctFuelType();
    }

    private void setCarModel(Car car, CarRequest carRequest) {
        CarType cartype = carTypeRepository.findById(carRequest.getCarType())
                .orElseThrow(()->new EntityNotFoundException("CarType not found with id " + carRequest.getCarType()));

        CarBrand carBrand = carBrandRepository.findById(carRequest.getCarBrand())
                .orElseThrow(()->new EntityNotFoundException("CarBrand not found with id " + carRequest.getCarBrand()));

        car.setModel(carRequest.getModel());
        car.setYear(carRequest.getYear());
        car.setColor(carRequest.getColor());
        car.setLicensePlate(carRequest.getLicensePlate());
        car.setSeats(carRequest.getSeats());
        car.setMileage(carRequest.getMileage());
        car.setFuelType(carRequest.getFuelType());
        car.setPrice(carRequest.getPrice());
        car.setAWD(carRequest.getAWD());
        car.setLeatherTrimmedUpholstery(carRequest.getLeatherTrimmedUpholstery());
        car.setMoonroof(carRequest.getMoonroof());
        car.setRAB(carRequest.getRAB());
        car.setBlindSpotAssist(carRequest.getBlindSpotAssist());
        car.setKeylessEntrySystem(carRequest.getKeylessEntrySystem());
        car.setEngineDisplacement(carRequest.getEngineDisplacement());
        car.setAvailable(carRequest.getAvailable());
        car.setDescription(carRequest.getDescription());

        car.setCarType(cartype);
        car.setCarBrand(carBrand);
    }
}
