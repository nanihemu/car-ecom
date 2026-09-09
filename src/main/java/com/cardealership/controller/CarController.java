package com.cardealership.controller;

import com.cardealership.dto.CarDTO;
import com.cardealership.model.Car;
import com.cardealership.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
@Tag(name = "Car Management", description = "Endpoints for managing car inventory")
public class CarController {
    
    private final CarService carService;
    
    @PostMapping
    @Operation(summary = "Create a new car listing")
    public ResponseEntity<Car> createCar(@Valid @RequestBody CarDTO carDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(carService.createCar(carDTO));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing car listing")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, 
                                         @Valid @RequestBody CarDTO carDTO) {
        return ResponseEntity.ok(carService.updateCar(id, carDTO));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a car listing")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get car by ID")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }
    
    @GetMapping
    @Operation(summary = "Get all cars with pagination")
    public ResponseEntity<Page<Car>> getAllCars(
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(carService.getAllCars(pageable));
    }
    
    @GetMapping("/available")
    @Operation(summary = "Get available cars")
    public ResponseEntity<Page<Car>> getAvailableCars(
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(carService.getAvailableCars(pageable));
    }
    
    @GetMapping("/featured")
    @Operation(summary = "Get featured cars")
    public ResponseEntity<List<Car>> getFeaturedCars() {
        return ResponseEntity.ok(carService.getFeaturedCars());
    }
    
    @GetMapping("/makes")
    @Operation(summary = "Get all makes")
    public ResponseEntity<List<String>> getAllMakes() {
        return ResponseEntity.ok(carService.getAllMakes());
    }
    
    @GetMapping("/makes/{make}/models")
    @Operation(summary = "Get models by make")
    public ResponseEntity<List<String>> getModelsByMake(@PathVariable String make) {
        return ResponseEntity.ok(carService.getModelsByMake(make));
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search cars with filters")
    public ResponseEntity<Page<Car>> searchCars(
            @RequestParam(required = false) String make,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer minYear,
            @RequestParam(required = false) Integer maxYear,
            @RequestParam(required = false) String fuelType,
            @RequestParam(required = false) String transmission,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(carService.searchCars(
                make, model, minPrice, maxPrice, minYear, maxYear,
                fuelType, transmission, pageable));
    }
    
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update car status")
    public ResponseEntity<Car> updateCarStatus(@PathVariable Long id, 
                                               @RequestParam String status) {
        return ResponseEntity.ok(carService.updateCarStatus(id, status));
    }
}