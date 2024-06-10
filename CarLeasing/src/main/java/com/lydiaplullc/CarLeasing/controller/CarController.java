package com.lydiaplullc.CarLeasing.controller;

import com.lydiaplullc.CarLeasing.exception.ResourceNotFoundException;
import com.lydiaplullc.CarLeasing.model.Car;
import com.lydiaplullc.CarLeasing.model.CarPicture;
import com.lydiaplullc.CarLeasing.request.CarRequest;
import com.lydiaplullc.CarLeasing.response.CarBrandResponse;
import com.lydiaplullc.CarLeasing.response.CarPictureResponse;
import com.lydiaplullc.CarLeasing.response.CarResponse;
import com.lydiaplullc.CarLeasing.response.CarTypeResponse;
import com.lydiaplullc.CarLeasing.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    @PostMapping("/add/new-car")
    public ResponseEntity<CarResponse> addCar(
            @RequestBody CarRequest carRequest
    ) throws SQLException {
        Car savedCar = carService.addCar(carRequest);
        CarResponse carResponse = getCarResponse(savedCar);
        return ResponseEntity.ok(carResponse);
    }

    @PutMapping("/update/{carId}")
    public ResponseEntity<CarResponse> updateCar(
            @PathVariable Long carId,
            @RequestBody CarRequest carRequest
    ) throws SQLException {
        Car savedCar = carService.updateCar(carId, carRequest);
        CarResponse carResponse = getCarResponse(savedCar);
        return ResponseEntity.ok(carResponse);
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<Optional<CarResponse>> getCarById(@PathVariable Long carId) {
        Optional<Car> theCar = carService.getCarById(carId);
        return theCar.map(car -> {
            CarResponse carResponse = getCarResponse(car);
            return ResponseEntity.ok(Optional.of(carResponse));
        }).orElseThrow(() -> new ResourceNotFoundException("car not found"));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CarResponse>> getAllCars() throws SQLException {
        List<Car> cars = carService.getAllCar();

        List<CarResponse> carResponses = new ArrayList<>();
        for(Car car: cars) {
            CarResponse carResponse = getCarResponse(car);
            carResponses.add(carResponse);
        }

        return ResponseEntity.ok(carResponses);
    }

    @GetMapping("/all-by-group")
    public ResponseEntity<List<CarResponse>> getAllCarsByGroup() throws SQLException {
        List<Car> cars = carService.getAllCarByGroup();

        List<CarResponse> carResponses = new ArrayList<>();
        for(Car car: cars) {
            CarResponse carResponse = getCarResponse(car);
            carResponses.add(carResponse);
        }

        return ResponseEntity.ok(carResponses);
    }

    @DeleteMapping("/delete/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long carId) {
        carService.deleteCar(carId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/licenseplate/all")
    public List<String> getAllLicensePlate(){
        return carService.getAllLicensePlate();
    }

    @GetMapping("/car/licenseplate/{carLicensePlate}")
    public ResponseEntity<Optional<CarResponse>> getCarByLicensePlate(@PathVariable Long carLicensePlate) {
        Optional<Car> theCar = carService.getCarByLicensePlate(carLicensePlate);
        return theCar.map(car -> {
            CarResponse carResponse = getCarResponse(car);
            return ResponseEntity.ok(Optional.of(carResponse));
        }).orElseThrow(() -> new ResourceNotFoundException("car not found"));
    }

    @GetMapping("/all-models")
    public List<String> getAllModels(){
        return carService.getAllModels();
    }

    @GetMapping("/all-available")
    public List<String> getAllAvailable(){
        return carService.getAllAvailable();
    }

    @GetMapping("/all-fueltype")
    public List<String> getAllFuelType(){
        return carService.getAllFuelType();
    }

    public static CarResponse getCarResponse(Car car) {
        CarBrandResponse carBrandResponse = CarBrandController.getCarBrandResponse(car.getCarBrand());
        CarTypeResponse carTypeResponse = CarTypeController.getCarTypeResponse(car.getCarType());

        List<CarPictureResponse> carPictureResponses = new ArrayList<>();
        for(CarPicture carPicture: car.getPictures()) {
            CarPictureResponse carPictureResponse = CarPictureController.getCarPictureResponse(carPicture, null);
            carPictureResponses.add(carPictureResponse);
        }

        return new CarResponse(
                car.getId(),
                car.getModel(),
                car.getYear(),
                car.getColor(),
                car.getLicensePlate(),
                car.getSeats(),
                car.getMileage(),
                car.getFuelType(),
                car.getPrice(),
                car.getAWD(),
                car.getLeatherTrimmedUpholstery(),
                car.getMoonroof(),
                car.getRAB(),
                car.getBlindSpotAssist(),
                car.getKeylessEntrySystem(),
                car.getEngineDisplacement(),
                car.getAvailable(),
                car.getDescription(),
                carTypeResponse,
                carBrandResponse,
                carPictureResponses
        );
    }
}
