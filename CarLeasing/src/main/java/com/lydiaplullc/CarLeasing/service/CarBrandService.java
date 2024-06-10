package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.exception.InternalServerException;
import com.lydiaplullc.CarLeasing.model.CarBrand;
import com.lydiaplullc.CarLeasing.repository.CarBrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarBrandService implements ICarBrandService {
    private final CarBrandRepository carBrandRepository;

    @Override
    public CarBrand addCarBrand(String name, String country, Integer foundedYear, MultipartFile logo) throws SQLException, IOException {
        CarBrand carBrand = new CarBrand();
        carBrand.setName(name);
        carBrand.setCountry(country);
        carBrand.setFoundedYear(foundedYear);

        if (!logo.isEmpty()) {
            // change original format to database format
            // change MultipartFile to byte[]
            byte[] logoBytes = logo.getBytes();
            // change byte[] to Blob
            Blob logoBlob = new SerialBlob(logoBytes);
            carBrand.setLogo(logoBlob);
        }

        // after save, return a carBrand entity
        return carBrandRepository.save(carBrand);
    }

    @Override
    public CarBrand updateCarBrand(Long carBrandId, String name, String country, Integer foundedYear, MultipartFile logo) throws SQLException, IOException {
        // first get old data by id
        CarBrand carBrand = carBrandRepository.findById(carBrandId).get();
        // set new data
        if (name != null) carBrand.setName(name);
        if (country != null) carBrand.setCountry(country);
        if (foundedYear != null) carBrand.setFoundedYear(foundedYear);
        if (logo != null && !logo.isEmpty()) {
            try {
                // change original format to database format
                // change MultipartFile to byte[]
                byte[] logoBytes = logo.getBytes();
                // change byte[] to Blob
                Blob logoBlob = new SerialBlob(logoBytes);
                carBrand.setLogo(logoBlob);
            } catch (SQLException ex) {
                throw new InternalServerException("Fail updating room");
            }
        }
        return carBrandRepository.save(carBrand);
    }

    @Override
    public Optional<CarBrand> getCarBrandById(Long carBrandId) {
        return Optional.of(carBrandRepository.findById(carBrandId).get());
    }

    @Override
    public List<CarBrand> getAllCarBrands() {
        return carBrandRepository.findAll(Sort.by("id").ascending());
    }

    @Override
    public void deleteCarBrand(Long carBrandId) {
        Optional<CarBrand> theCarBrand = carBrandRepository.findById(carBrandId);
        if (theCarBrand.isPresent()) {
            carBrandRepository.deleteById(carBrandId);
        }
    }

    @Override
    public List<String> getAllCountries() {
        return carBrandRepository.findDistinctCountries();
    }
}
