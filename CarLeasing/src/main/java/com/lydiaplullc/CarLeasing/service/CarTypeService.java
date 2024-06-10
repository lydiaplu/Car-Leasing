package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.exception.DeleteDataException;
import com.lydiaplullc.CarLeasing.model.Car;
import com.lydiaplullc.CarLeasing.model.CarType;
import com.lydiaplullc.CarLeasing.repository.CarTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarTypeService implements ICarTypeService {
    private final CarTypeRepository carTypeRepository;
    private final CarService carService;

    @Override
    public CarType addCarType(String typeName) throws SQLException {
        CarType carType = new CarType();
        carType.setTypeName(typeName);

        return carTypeRepository.save(carType);
    }

    @Override
    public CarType updateCarType(Long carTypeId, String typeName) throws SQLException {
        CarType carType = carTypeRepository.findById(carTypeId).get();
        if (typeName != null) carType.setTypeName(typeName);

        return carTypeRepository.save(carType);
    }

    @Override
    public Optional<CarType> getCarTypeById(Long carTypeId) {
        return Optional.of(carTypeRepository.findById(carTypeId).get());
    }

    @Override
    public void deleteCarType(Long carTypeId) {
        Optional<List<Car>> theCars = carService.getCarByCarTypeId(carTypeId);
        if(theCars.isPresent() && !theCars.get().isEmpty()) {
            throw new DeleteDataException("Cannot delete car type as there are cars associated with it.");
        }

        Optional<CarType> carType = carTypeRepository.findById(carTypeId);
        if (carType.isPresent()) {
            carTypeRepository.deleteById(carTypeId);
        }
    }

    @Override
    public List<CarType> getAllCarTypes() {
        return carTypeRepository.findAll(Sort.by("id").ascending());
    }
}
