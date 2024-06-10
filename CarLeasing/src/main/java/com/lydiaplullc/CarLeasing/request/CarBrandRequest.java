package com.lydiaplullc.CarLeasing.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarBrandRequest {
    private String name;
    private String country;
    private Integer foundedYear;
}
