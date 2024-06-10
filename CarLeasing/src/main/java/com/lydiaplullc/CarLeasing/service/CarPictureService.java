package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.model.Car;
import com.lydiaplullc.CarLeasing.model.CarPicture;
import com.lydiaplullc.CarLeasing.repository.CarPictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class CarPictureService implements ICarPictureService {
    private final CarPictureRepository carPictureRepository;
    private final CarService carService;

    @Override
    public CarPicture addCarPicture(Long carId, MultipartFile picture) throws SQLException, IOException {
        CarPicture carPicture = new CarPicture();

        if(!picture.isEmpty()) {
            // change original format to database format
            // change MultipartFile to byte[]
            byte[] pictureBytes = picture.getBytes();
            // change type[] to Blob
            Blob pictureBlob = new SerialBlob(pictureBytes);
            carPicture.setPicture(pictureBlob);

            Car theCar = carService.getCarById(carId).get();
            carPicture.setCar(theCar);
            theCar.addPicture(carPicture);
        }

        return carPictureRepository.save(carPicture);
    }

    @Override
    public void deleteCarPicture(Long carId, Long carPictureId) {
        CarPicture theCarPicture = carPictureRepository.findCarPictureByIdAndPictureId(carId, carPictureId);
        if(theCarPicture != null) {
            carPictureRepository.deleteById(carPictureId);
        }
    }
}
