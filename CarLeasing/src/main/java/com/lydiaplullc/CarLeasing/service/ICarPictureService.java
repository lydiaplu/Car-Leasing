package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.model.CarPicture;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

public interface ICarPictureService {
    CarPicture addCarPicture(Long carId, MultipartFile picture) throws SQLException, IOException;

    void deleteCarPicture(Long carId, Long carPictureId);
}
