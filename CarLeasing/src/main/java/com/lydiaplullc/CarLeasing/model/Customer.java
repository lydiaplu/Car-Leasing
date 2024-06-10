package com.lydiaplullc.CarLeasing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String middleName;

    private String gender;

    private LocalDate dateOfBirth;

    private String phone;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String driverLicenseNumber;

    @Lob
    private Blob driverLicenseFrontPhoto;

    @Lob
    private Blob driverLicenseBackPhoto;

    private Integer creditScore;

    private Integer drivingYears;

    private String address;

    private String emergencyContactPhone;

    private Boolean isDisabled;

    private String disabilityDescription;

    private LocalDate registrationDate;

    @OneToMany(mappedBy = "customer",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private List<CarRental> rentals;

    @OneToMany(mappedBy = "customer",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<CarReview> reviews;

    @OneToMany(mappedBy = "customer",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private List<Payment> payments;

    public Customer() {
        this.rentals = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.payments = new ArrayList<>();
    }

    public void addRentals(CarRental rental) {
        this.rentals.add(rental);
        rental.setCustomer(this);
    }

    public void addReviews(CarReview review) {
        this.reviews.add(review);
        review.setCustomer(this);
    }

    public void addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setCustomer(this);
    }
}
