package com.lydiaplullc.CarLeasing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer year;

    @Column(length = 30)
    private String color;

    @Column(length = 30, nullable = false, unique = true)
    private String licensePlate;

    @Column(nullable = false)
    private Integer seats;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal mileage;

    @Column(nullable = false)
    private String fuelType;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    private Boolean AWD;
    private Boolean leatherTrimmedUpholstery;
    private Boolean moonroof;
    private Boolean RAB;
    private Boolean blindSpotAssist;
    private Boolean keylessEntrySystem;
    private Double engineDisplacement;

    @Column(nullable = false)
    private Boolean available;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "car_type_id", nullable = false)
    private CarType carType;

    @ManyToOne
    @JoinColumn(name = "car_brand_id", nullable = false)
    private CarBrand carBrand;

    @OneToMany(mappedBy = "car",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<CarPicture> pictures;

    @OneToMany(mappedBy = "car",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<CarMaintenance> maintenances;

    @OneToMany(mappedBy = "car",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<CarInsurance> insurances;

    @OneToMany(mappedBy = "car",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<CarViolation> violations;

    @OneToMany(mappedBy = "car",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private List<CarRental> rentals;

    @OneToMany(mappedBy = "car",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<CarReview> reviews;

    @OneToMany(mappedBy = "car",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private List<Payment> payments;

    public Car() {
        // 初始化car的时候，将外表初始化为数组
        this.pictures = new ArrayList<>();
        this.maintenances = new ArrayList<>();
        this.insurances = new ArrayList<>();
        this.violations = new ArrayList<>();
        this.rentals = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.payments = new ArrayList<>();
    }

    // 添加picture到car中
    public void addPicture(CarPicture picture) {
        // 将picture实例，添加到pictures的集合中
        this.pictures.add(picture);
        // 将这个car实例，关联到CarPicture实例的car属性上
        picture.setCar(this);
    }

    // 添加maintenance到car中
    public void addMaintenance(CarMaintenance maintenance) {
        this.maintenances.add(maintenance);
        maintenance.setCar(this);
    }

    public void addInsurance(CarInsurance insurance) {
        this.insurances.add(insurance);
        insurance.setCar(this);
    }

    public void addViolation(CarViolation violation) {
        this.violations.add(violation);
        violation.setCar(this);
    }

    public void addRental(CarRental rental) {
        this.rentals.add(rental);
        rental.setCar(this);
    }

    public void addReviews(CarReview review) {
        this.reviews.add(review);
        review.setCar(this);
    }

    public void addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setCar(this);
    }
}