package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.model.CarBrand;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ICarBrandService {
    CarBrand addCarBrand(String name,String country,Integer foundedYear, MultipartFile logo) throws SQLException, IOException;

    CarBrand updateCarBrand(Long carBrandId, String name, String country, Integer foundedYear, MultipartFile logo) throws SQLException, IOException;

    Optional<CarBrand> getCarBrandById(Long carBrandId);

    List<CarBrand> getAllCarBrands();

    void deleteCarBrand(Long brandId);

    List<String> getAllCountries();
}
