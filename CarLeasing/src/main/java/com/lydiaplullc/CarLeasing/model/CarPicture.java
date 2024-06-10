package com.lydiaplullc.CarLeasing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "car_pictures")
public class CarPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private Blob picture;

    private String description;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    public CarPicture() {

    }
}
