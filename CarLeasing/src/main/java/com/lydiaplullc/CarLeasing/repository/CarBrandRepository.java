package com.lydiaplullc.CarLeasing.repository;

import com.lydiaplullc.CarLeasing.model.CarBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarBrandRepository extends JpaRepository<CarBrand, Long> {
    @Query("SELECT DISTINCT b.country FROM CarBrand b")
    List<String> findDistinctCountries();
}
