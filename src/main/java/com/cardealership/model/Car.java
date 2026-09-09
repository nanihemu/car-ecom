package com.cardealership.model;

import com.cardealership.model.enums.CarStatus;
import com.cardealership.model.enums.FuelType;
import com.cardealership.model.enums.TransmissionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cars")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Car {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String make;
    
    @Column(nullable = false)
    private String model;
    
    @Column(nullable = false)
    private Integer year;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(nullable = false)
    private Integer mileage;
    
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;
    
    @Enumerated(EnumType.STRING)
    private TransmissionType transmission;
    
    @Column(nullable = false)
    private String color;
    
    private String engineSize;
    private Integer horsepower;
    private Integer doors;
    private String vin;
    
    @Column(length = 1000)
    private String description;
    
    private String imageUrl;
    private String exteriorImages;
    private String interiorImages;
    
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private CarStatus status = CarStatus.AVAILABLE;
    
    @Builder.Default
    private Boolean featured = false;
    
    @Builder.Default
    private Boolean certified = false;
    
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
}