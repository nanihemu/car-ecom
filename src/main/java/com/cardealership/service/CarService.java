package com.cardealership.service;

import com.cardealership.dto.CarDTO;
import com.cardealership.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface CarService {
    Car createCar(CarDTO carDTO);
    Car updateCar(Long id, CarDTO carDTO);
    void deleteCar(Long id);
    Car getCarById(Long id);
    Page<Car> getAllCars(Pageable pageable);
    Page<Car> getAvailableCars(Pageable pageable);
    List<Car> getFeaturedCars();
    List<String> getAllMakes();
    List<String> getModelsByMake(String make);
    Page<Car> searchCars(String make, String model, BigDecimal minPrice, BigDecimal maxPrice,
                        Integer minYear, Integer maxYear, String fuelType, 
                        String transmission, Pageable pageable);
    Car updateCarStatus(Long id, String status);
}