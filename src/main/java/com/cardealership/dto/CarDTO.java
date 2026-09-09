package com.cardealership.dto;

import com.cardealership.model.enums.FuelType;
import com.cardealership.model.enums.TransmissionType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CarDTO {
    @NotBlank(message = "Make is required")
    private String make;
    
    @NotBlank(message = "Model is required")
    private String model;
    
    @NotNull(message = "Year is required")
    @Min(value = 1900, message = "Year must be greater than 1900")
    @Max(value = 2026, message = "Year must be less than 2026")
    private Integer year;
    
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;
    
    @NotNull(message = "Mileage is required")
    @PositiveOrZero(message = "Mileage must be positive or zero")
    private Integer mileage;
    
    @NotNull(message = "Fuel type is required")
    private FuelType fuelType;
    
    @NotNull(message = "Transmission type is required")
    private TransmissionType transmission;
    
    @NotBlank(message = "Color is required")
    private String color;
    
    private String engineSize;
    private Integer horsepower;
    private Integer doors;
    private String vin;
    private String description;
    private String imageUrl;
    private String exteriorImages;
    private String interiorImages;
    private Boolean featured;
    private Boolean certified;
}