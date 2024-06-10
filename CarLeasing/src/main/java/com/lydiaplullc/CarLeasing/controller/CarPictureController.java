package com.lydiaplullc.CarLeasing.controller;

import com.lydiaplullc.CarLeasing.exception.PhotoRetrievalException;
import com.lydiaplullc.CarLeasing.model.CarPicture;
import com.lydiaplullc.CarLeasing.response.CarPictureResponse;
import com.lydiaplullc.CarLeasing.response.CarResponse;
import com.lydiaplullc.CarLeasing.service.CarPictureService;
import com.lydiaplullc.CarLeasing.service.CarService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carpictures")
public class CarPictureController {
    private final CarPictureService carPictureService;
    private final CarService carService;

    @PostMapping("add/new-carpicture/{carId}")
    public ResponseEntity<Void> addCarPictures(
            @PathVariable Long carId,
            @RequestParam MultipartFile picture
    ) throws SQLException, IOException {
        carPictureService.addCarPicture(carId, picture);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete/{carPictureId}")
    public ResponseEntity<Void> deleteCarPicture(
            @PathVariable Long carPictureId,
            @RequestParam Long carId
    ) {
        carPictureService.deleteCarPicture(carId, carPictureId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public static CarPictureResponse getCarPictureResponse(CarPicture carPicture) {
        CarResponse carResponse = CarController.getCarResponse(carPicture.getCar());
        return getCarPictureResponse(carPicture, carResponse);
    }

    public static CarPictureResponse getCarPictureResponse(CarPicture carPicture, CarResponse carResponse) {
        // change blob to bytes
        byte[] pictureBytes = null;
        Blob pictureBlob = carPicture.getPicture();
        if (pictureBlob != null) {
            try {
                pictureBytes = pictureBlob.getBytes(1, (int) pictureBlob.length());
            } catch (SQLException e) {
                throw new PhotoRetrievalException("Error retrieving logo");
            }
        }

        return new CarPictureResponse(
                carPicture.getId(),
                carResponse,
                pictureBytes != null ? Base64.encodeBase64String(pictureBytes) : null
        );
    }
}
