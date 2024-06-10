package com.lydiaplullc.CarLeasing.controller;

import com.lydiaplullc.CarLeasing.exception.PhotoRetrievalException;
import com.lydiaplullc.CarLeasing.exception.ResourceNotFoundException;
import com.lydiaplullc.CarLeasing.model.CarBrand;
import com.lydiaplullc.CarLeasing.response.CarBrandResponse;
import com.lydiaplullc.CarLeasing.service.CarBrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carbrands")
public class CarBrandController {
    private final CarBrandService carBrandService;

    @PostMapping("/add/new-carbrand")
    public ResponseEntity<CarBrandResponse> addCarBrand(
            @RequestParam String name,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) Integer foundedYear,
            @RequestParam MultipartFile logo
    ) throws SQLException, IOException {

        // save data by service, then return entity
        CarBrand savedCarBrand = carBrandService.addCarBrand(name, country, foundedYear, logo);
        // create a response to front-end
        CarBrandResponse carBrandResponse = getCarBrandResponse(savedCarBrand);
        // 用于创建一个表示 HTTP 状态码 200 OK 的响应实体
        return ResponseEntity.ok(carBrandResponse);
    }

    @PutMapping("/update/{carBrandId}")
    public ResponseEntity<CarBrandResponse> updateCarBrand(
            @PathVariable Long carBrandId,
            @RequestParam String name,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) Integer foundedYear,
            @RequestParam MultipartFile logo
    ) throws SQLException, IOException {
        CarBrand savedCarBrand = carBrandService.updateCarBrand(carBrandId, name, country, foundedYear, logo);
        CarBrandResponse carBrandResponse = getCarBrandResponse(savedCarBrand);
        return ResponseEntity.ok(carBrandResponse);
    }

    @GetMapping("/carbrand/{carBrandId}")
    public ResponseEntity<Optional<CarBrandResponse>> getCarBrandById(@PathVariable Long carBrandId) {
        Optional<CarBrand> theCarBrand = carBrandService.getCarBrandById(carBrandId);
        return theCarBrand.map(carBrand -> {
            CarBrandResponse carBrandResponse = getCarBrandResponse(carBrand);
            return ResponseEntity.ok(Optional.of(carBrandResponse));
        }).orElseThrow(() -> new ResourceNotFoundException("Car Brand not found"));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CarBrandResponse>> getAllCarBrands() throws SQLException {
        List<CarBrand> carBrands = carBrandService.getAllCarBrands();

        List<CarBrandResponse> carBrandResponses = new ArrayList<>();
        for(CarBrand carBrand: carBrands) {
            CarBrandResponse carBrandResponse = getCarBrandResponse(carBrand);
            carBrandResponses.add(carBrandResponse);
        }

        return ResponseEntity.ok(carBrandResponses);
    }

    @DeleteMapping("/delete/{carBrandId}")
    public ResponseEntity<Void> deleteCarBrand(@PathVariable Long carBrandId) {
        carBrandService.deleteCarBrand(carBrandId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all-countries")
    public List<String> getAllCountries(){
        return carBrandService.getAllCountries();
    }

    public static CarBrandResponse getCarBrandResponse(CarBrand carBrand) {
        // change blob to bytes
        byte[] logoBytes = null;
        Blob logoBlob = carBrand.getLogo();
        if (logoBlob != null) {
            try {
                logoBytes = logoBlob.getBytes(1, (int) logoBlob.length());
            } catch (SQLException e) {
                throw new PhotoRetrievalException("Error retrieving logo");
            }
        }

        return new CarBrandResponse(
                carBrand.getId(),
                carBrand.getName(),
                carBrand.getCountry(),
                carBrand.getFoundedYear(),
                logoBytes);
    }
}
