package com.cardealership.service.impl;

import com.cardealership.dto.CarDTO;
import com.cardealership.exception.ResourceNotFoundException;
import com.cardealership.model.Car;
import com.cardealership.model.enums.CarStatus;
import com.cardealership.model.enums.FuelType;
import com.cardealership.model.enums.TransmissionType;
import com.cardealership.repository.CarRepository;
import com.cardealership.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CarServiceImpl implements CarService {
    
    private final CarRepository carRepository;
    
    @Override
    public Car createCar(CarDTO carDTO) {
        log.info("Creating new car: {} {}", carDTO.getMake(), carDTO.getModel());
        
        Car car = Car.builder()
                .make(carDTO.getMake())
                .model(carDTO.getModel())
                .year(carDTO.getYear())
                .price(carDTO.getPrice())
                .mileage(carDTO.getMileage())
                .fuelType(carDTO.getFuelType())
                .transmission(carDTO.getTransmission())
                .color(carDTO.getColor())
                .engineSize(carDTO.getEngineSize())
                .horsepower(carDTO.getHorsepower())
                .doors(carDTO.getDoors())
                .vin(carDTO.getVin())
                .description(carDTO.getDescription())
                .imageUrl(carDTO.getImageUrl())
                .exteriorImages(carDTO.getExteriorImages())
                .interiorImages(carDTO.getInteriorImages())
                .status(CarStatus.AVAILABLE)
                .featured(carDTO.getFeatured() != null && carDTO.getFeatured())
                .certified(carDTO.getCertified() != null && carDTO.getCertified())
                .build();
        
        return carRepository.save(car);
    }
    
    @Override
    public Car updateCar(Long id, CarDTO carDTO) {
        log.info("Updating car with id: {}", id);
        
        Car car = getCarById(id);
        car.setMake(carDTO.getMake());
        car.setModel(carDTO.getModel());
        car.setYear(carDTO.getYear());
        car.setPrice(carDTO.getPrice());
        car.setMileage(carDTO.getMileage());
        car.setFuelType(carDTO.getFuelType());
        car.setTransmission(carDTO.getTransmission());
        car.setColor(carDTO.getColor());
        car.setEngineSize(carDTO.getEngineSize());
        car.setHorsepower(carDTO.getHorsepower());
        car.setDoors(carDTO.getDoors());
        car.setVin(carDTO.getVin());
        car.setDescription(carDTO.getDescription());
        car.setImageUrl(carDTO.getImageUrl());
        car.setExteriorImages(carDTO.getExteriorImages());
        car.setInteriorImages(carDTO.getInteriorImages());
        car.setFeatured(carDTO.getFeatured() != null && carDTO.getFeatured());
        car.setCertified(carDTO.getCertified() != null && carDTO.getCertified());
        
        return carRepository.save(car);
    }
    
    @Override
    public void deleteCar(Long id) {
        log.info("Deleting car with id: {}", id);
        Car car = getCarById(id);
        car.setStatus(CarStatus.SOLD);
        carRepository.save(car);
    }
    
    @Override
    public Car getCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
    }
    
    @Override
    public Page<Car> getAllCars(Pageable pageable) {
        return carRepository.findAll(pageable);
    }
    
    @Override
    public Page<Car> getAvailableCars(Pageable pageable) {
        return carRepository.findByStatus(CarStatus.AVAILABLE, pageable);
    }
    
    @Override
    public List<Car> getFeaturedCars() {
        return carRepository.findByFeaturedTrueAndStatus(CarStatus.AVAILABLE);
    }
    
    @Override
    public List<String> getAllMakes() {
        return carRepository.findDistinctMakes(CarStatus.AVAILABLE);
    }
    
    @Override
    public List<String> getModelsByMake(String make) {
        return carRepository.findDistinctModelsByMake(make, CarStatus.AVAILABLE);
    }
    
    @Override
    public Page<Car> searchCars(String make, String model, BigDecimal minPrice, BigDecimal maxPrice,
                               Integer minYear, Integer maxYear, String fuelTypeStr, 
                               String transmissionStr, Pageable pageable) {
        FuelType fuelType = fuelTypeStr != null && !fuelTypeStr.isEmpty() ? 
                FuelType.valueOf(fuelTypeStr.toUpperCase()) : null;
        TransmissionType transmission = transmissionStr != null && !transmissionStr.isEmpty() ? 
                TransmissionType.valueOf(transmissionStr.toUpperCase()) : null;
        
        return carRepository.searchCars(
                make, model, minPrice, maxPrice, minYear, maxYear,
                fuelType, transmission, CarStatus.AVAILABLE, pageable);
    }
    
    @Override
    public Car updateCarStatus(Long id, String statusStr) {
        Car car = getCarById(id);
        car.setStatus(CarStatus.valueOf(statusStr.toUpperCase()));
        return carRepository.save(car);
    }
}