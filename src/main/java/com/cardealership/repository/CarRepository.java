package com.cardealership.repository;

import com.cardealership.model.Car;
import com.cardealership.model.enums.CarStatus;
import com.cardealership.model.enums.FuelType;
import com.cardealership.model.enums.TransmissionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    
    Page<Car> findByStatus(CarStatus status, Pageable pageable);
    
    List<Car> findByFeaturedTrueAndStatus(CarStatus status);
    
    @Query("SELECT DISTINCT c.make FROM Car c WHERE c.status = :status ORDER BY c.make")
    List<String> findDistinctMakes(@Param("status") CarStatus status);
    
    @Query("SELECT DISTINCT c.model FROM Car c WHERE c.make = :make AND c.status = :status ORDER BY c.model")
    List<String> findDistinctModelsByMake(@Param("make") String make, @Param("status") CarStatus status);
    
    @Query("SELECT c FROM Car c WHERE " +
           "(:make IS NULL OR c.make = :make) AND " +
           "(:model IS NULL OR c.model = :model) AND " +
           "(:minPrice IS NULL OR c.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR c.price <= :maxPrice) AND " +
           "(:minYear IS NULL OR c.year >= :minYear) AND " +
           "(:maxYear IS NULL OR c.year <= :maxYear) AND " +
           "(:fuelType IS NULL OR c.fuelType = :fuelType) AND " +
           "(:transmission IS NULL OR c.transmission = :transmission) AND " +
           "c.status = :status")
    Page<Car> searchCars(@Param("make") String make,
                         @Param("model") String model,
                         @Param("minPrice") BigDecimal minPrice,
                         @Param("maxPrice") BigDecimal maxPrice,
                         @Param("minYear") Integer minYear,
                         @Param("maxYear") Integer maxYear,
                         @Param("fuelType") FuelType fuelType,
                         @Param("transmission") TransmissionType transmission,
                         @Param("status") CarStatus status,
                         Pageable pageable);
}