package com.lydiaplullc.CarLeasing.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarPictureResponse {
    private Long id;
    private CarResponse car;
    private String picture;
}
