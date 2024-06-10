package com.lydiaplullc.CarLeasing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "car_insurances")
public class CarInsurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String insuranceCompany;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal insuranceAmount;

    @Column(nullable = false)
    private LocalDate effectiveDate;

    @Column(nullable = false)
    private LocalDate expirationDate;

    private LocalDate autoRenewalDate;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal premium;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    public CarInsurance() {

    }
}
