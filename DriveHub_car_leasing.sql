

CREATE TABLE car_types (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type_name VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE car_brands (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    country VARCHAR(150),
    founded_year INT,
    logo MEDIUMBLOB NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE cars (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    year INT NOT NULL,
    color VARCHAR(30),
    license_plate VARCHAR(30) NOT NULL UNIQUE,
    seats INT NOT NULL,
    mileage DECIMAL(10, 2) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    engine_displacement DOUBLE NOT NULL,
    fuel_type VARCHAR(50),
    awd BOOLEAN,
    leather_trimmed_upholstery BOOLEAN,
    moonroof BOOLEAN,
    rab BOOLEAN,
    blind_spot_assist BOOLEAN,
    keyless_entry_system BOOLEAN,
    available BOOLEAN,
    description TEXT,
    car_type_id BIGINT NOT NULL,
    car_brand_id BIGINT NOT NULL,
    FOREIGN KEY (car_type_id) REFERENCES car_types(id),
    FOREIGN KEY (car_brand_id) REFERENCES car_brands(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE car_insurances (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    insurance_company VARCHAR(255) NOT NULL,
    insurance_amount DECIMAL(10, 2) NOT NULL,
    effective_date DATE NOT NULL,
    expiration_date DATE NOT NULL,
    auto_renewal_date DATE,
    premium DECIMAL(10, 2) NOT NULL,
    description TEXT,
    car_id BIGINT NOT NULL,
    FOREIGN KEY (car_id) REFERENCES cars(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE car_maintenances (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    maintenance_date DATE,
    cost DECIMAL(38, 2),
    description TEXT,
    car_id BIGINT NOT NULL,
    FOREIGN KEY (car_id) REFERENCES cars(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE car_pictures (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    picture MEDIUMBLOB,
    description VARCHAR(255),
    car_id BIGINT NOT NULL,
    FOREIGN KEY (car_id) REFERENCES cars(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE customers (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    middle_name VARCHAR(50),
    gender VARCHAR(50),
    date_of_birth DATE,
    phone VARCHAR(20),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(255),
    driver_license_number VARCHAR(50) UNIQUE,
    driver_license_front_photo MEDIUMBLOB,
    driver_license_back_photo MEDIUMBLOB,
    credit_score INT,
    driving_years INT,
    address VARCHAR(255),
    emergency_contact_phone VARCHAR(20),
    is_disabled BOOLEAN DEFAULT FALSE,
    disability_description VARCHAR(255),
    registration_date DATE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE car_rentals (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    rental_date DATE,
    return_date DATE,
    customer_id BIGINT,
    car_id BIGINT,
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (car_id) REFERENCES cars(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE car_reviews (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    rating INT,
    comment TEXT,
    review_date DATE,
    customer_id BIGINT,
    car_id BIGINT,
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (car_id) REFERENCES cars(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE car_violations (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    violation_date DATE NOT NULL,
    violation_location VARCHAR(255),
    fine_amount DECIMAL(38, 2),
    description TEXT,
    car_id BIGINT NOT NULL,
    FOREIGN KEY (car_id) REFERENCES cars(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE payments (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(10,2),
    payment_date DATE,
    payment_method VARCHAR(50),
    customer_id BIGINT,
    car_id BIGINT,
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (car_id) REFERENCES cars(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
