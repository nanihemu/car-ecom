package com.cardealership.config;

import com.cardealership.dto.CarDTO;
import com.cardealership.model.enums.FuelType;
import com.cardealership.model.enums.TransmissionType;
import com.cardealership.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {
    
    private final CarService carService;
    
    @Override
    public void run(String... args) {
        log.info("Loading sample car data...");
        
        // Check if data already exists
        if (carService.getAllCars(PageRequest.of(0, 1)).getTotalElements() > 0) {
            log.info("Sample data already loaded");
            return;
        }
        
        // Sample Car 1
        CarDTO car1 = new CarDTO();
        car1.setMake("Toyota");
        car1.setModel("Camry");
        car1.setYear(2023);
        car1.setPrice(new BigDecimal("35000"));
        car1.setMileage(5000);
        car1.setFuelType(FuelType.PETROL);
        car1.setTransmission(TransmissionType.AUTOMATIC);
        car1.setColor("Silver");
        car1.setEngineSize("2.5L");
        car1.setHorsepower(203);
        car1.setDoors(4);
        car1.setVin("1HGCM82633A123456");
        car1.setDescription("Excellent condition, fully loaded with leather seats and sunroof.");
        car1.setImageUrl("https://images.unsplash.com/photo-1621007947382-bb3c3994e3fb?w=400&h=300&fit=crop");
        car1.setFeatured(true);
        car1.setCertified(true);
        carService.createCar(car1);
        
        // Sample Car 2
        CarDTO car2 = new CarDTO();
        car2.setMake("Honda");
        car2.setModel("Accord");
        car2.setYear(2022);
        car2.setPrice(new BigDecimal("32000"));
        car2.setMileage(8000);
        car2.setFuelType(FuelType.PETROL);
        car2.setTransmission(TransmissionType.CVT);
        car2.setColor("Blue");
        car2.setEngineSize("2.0L");
        car2.setHorsepower(192);
        car2.setDoors(4);
        car2.setVin("1HGCM82633A123457");
        car2.setDescription("Great family car with excellent fuel economy.");
        car2.setImageUrl("https://images.unsplash.com/photo-1583121274602-3e2820c69888?w=400&h=300&fit=crop");
        car2.setFeatured(true);
        car2.setCertified(true);
        carService.createCar(car2);
        
        // Sample Car 3
        CarDTO car3 = new CarDTO();
        car3.setMake("Tesla");
        car3.setModel("Model 3");
        car3.setYear(2023);
        car3.setPrice(new BigDecimal("45000"));
        car3.setMileage(2000);
        car3.setFuelType(FuelType.ELECTRIC);
        car3.setTransmission(TransmissionType.AUTOMATIC);
        car3.setColor("White");
        car3.setEngineSize("Electric");
        car3.setHorsepower(283);
        car3.setDoors(4);
        car3.setVin("1HGCM82633A123458");
        car3.setDescription("Zero emissions, instant torque, and autopilot capability.");
        car3.setImageUrl("https://images.unsplash.com/photo-1560958089-b8a1929cea89?w=400&h=300&fit=crop");
        car3.setFeatured(true);
        car3.setCertified(true);
        carService.createCar(car3);
        
        // Sample Car 4
        CarDTO car4 = new CarDTO();
        car4.setMake("BMW");
        car4.setModel("3 Series");
        car4.setYear(2023);
        car4.setPrice(new BigDecimal("42000"));
        car4.setMileage(3000);
        car4.setFuelType(FuelType.PETROL);
        car4.setTransmission(TransmissionType.AUTOMATIC);
        car4.setColor("Black");
        car4.setEngineSize("2.0L");
        car4.setHorsepower(255);
        car4.setDoors(4);
        car4.setVin("1HGCM82633A123459");
        car4.setDescription("Luxury sedan with advanced technology and premium features.");
        car4.setImageUrl("https://images.unsplash.com/photo-1555215695-3004980ad54e?w=400&h=300&fit=crop");
        car4.setFeatured(true);
        car4.setCertified(true);
        carService.createCar(car4);
        
        // Sample Car 5
        CarDTO car5 = new CarDTO();
        car5.setMake("Mercedes-Benz");
        car5.setModel("C-Class");
        car5.setYear(2022);
        car5.setPrice(new BigDecimal("48000"));
        car5.setMileage(10000);
        car5.setFuelType(FuelType.DIESEL);
        car5.setTransmission(TransmissionType.AUTOMATIC);
        car5.setColor("Grey");
        car5.setEngineSize("2.0L");
        car5.setHorsepower(194);
        car5.setDoors(4);
        car5.setVin("1HGCM82633A123460");
        car5.setDescription("Elegant design with powerful diesel engine and luxury interior.");
        car5.setImageUrl("https://images.unsplash.com/photo-1614200187524-dc4b892acf16?w=400&h=300&fit=crop");
        car5.setFeatured(false);
        car5.setCertified(true);
        carService.createCar(car5);
        
        log.info("Sample car data loaded successfully!");
    }
}