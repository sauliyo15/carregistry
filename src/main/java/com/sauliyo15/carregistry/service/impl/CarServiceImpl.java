package com.sauliyo15.carregistry.service.impl;

import com.sauliyo15.carregistry.entity.CarEntity;
import com.sauliyo15.carregistry.exception.CarNotFoundException;
import com.sauliyo15.carregistry.exception.CarsNotFoundException;
import com.sauliyo15.carregistry.model.Brand;
import com.sauliyo15.carregistry.model.Car;
import com.sauliyo15.carregistry.repository.CarRepository;
import com.sauliyo15.carregistry.service.BrandService;
import com.sauliyo15.carregistry.service.CarService;
import com.sauliyo15.carregistry.service.converters.CarConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    private final BrandService brandService;

    private final CarConverter carConverter;


    public CarServiceImpl(CarRepository carRepository, BrandService brandService, CarConverter carConverter) {
        this.carRepository = carRepository;
        this.brandService = brandService;
        this.carConverter = carConverter;
    }

    @Override
    @Async
    public CompletableFuture<List<Car>> getCars() {
        long startTime = System.currentTimeMillis();
        List<CarEntity> carEntityList = carRepository.findAll();
        if (carEntityList.isEmpty()) {
            throw new CarsNotFoundException();
        }
        List<Car> carList = carConverter.toCarList(carEntityList);
        long endTime = System.currentTimeMillis();
        log.info("Total time elapsed Getting: {}", endTime-startTime);
        return CompletableFuture.completedFuture(carList);
    }

    @Override
    public Car getCarById(Integer id) {
        Optional<CarEntity> carEntityOptional = carRepository.findById(id);
        CarEntity carEntity = carEntityOptional.orElseThrow(() -> new CarNotFoundException(id));
        return carConverter.toCar(carEntity);
    }

    public Car addCar(Car car) {
        Brand brand = brandService.getBrandByName(car.getBrand().getName());
        car.setBrand(brand);
        CarEntity carEntity = carConverter.toCarEntity(car);
        CarEntity savedEntity = carRepository.save(carEntity);
        return carConverter.toCar(savedEntity);
    }

    public Car updateCar(Integer id, Car car) {
        if (!carRepository.existsById(id)) {
            throw new CarNotFoundException(id);
        }
        Brand brand = brandService.getBrandByName(car.getBrand().getName());
        car.setBrand(brand);
        CarEntity carEntity = carConverter.toCarEntity(car);
        carEntity.setId(id);
        return carConverter.toCar(carRepository.save(carEntity));
    }

    public void deleteCar(Integer id) {
        if (!carRepository.existsById(id)) {
            throw new CarNotFoundException(id);
        }
        carRepository.deleteById(id);
    }

    @Override
    @Async
    public CompletableFuture<List<Car>> addCars(List<Car> carList) {
        long starTime = System.currentTimeMillis();
        List<CarEntity> carEntityList = new ArrayList<>();
        for (Car car : carList) {
            Brand brand = brandService.getBrandByName(car.getBrand().getName());
            car.setBrand(brand);
            carEntityList.add(carConverter.toCarEntity(car));
        }
        List<Car> carListSaved = carConverter.toCarList(carRepository.saveAll(carEntityList));
        long endTime = System.currentTimeMillis();
        log.info("Total time elapsed Getting: " + (endTime-starTime));
        return CompletableFuture.completedFuture(carListSaved);
    }

    @Override
    public String downloadCarsCsv() {
        String[] HEADERS = { "model", "brand", "milleage", "price", "year", "description",
                "colour", "fuelType", "numDoors"};

        try {
            List<Car> carList = this.getCars().get();

            StringBuilder csvContent = new StringBuilder();

            csvContent.append(String.join(",", HEADERS)).append("\n");

            carList.forEach(car -> csvContent.append(car.toString()).append("\n"));

            return csvContent.toString();

        } catch (CarsNotFoundException e) {
            return e.getMessage();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while generating CSV", e);
        }
    }

    @Override
    public void uploadCarsCsv(MultipartFile file) {

        List<Car> carList = new ArrayList<>();

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord record : csvRecords) {
                Brand brand = new Brand();
                brand.setName(record.get("brand"));
                Car car = new Car();
                car.setModel(record.get("model"));
                car.setBrand(brand);
                car.setMilleage(Integer.parseInt(record.get("milleage")));
                car.setPrice(Double.parseDouble(record.get("price")));
                car.setYear(Integer.parseInt(record.get("year")));
                car.setDescription(record.get("description"));
                car.setColour(record.get("colour"));
                car.setFuelType(record.get("fuelType"));
                car.setNumDoors(Integer.parseInt(record.get("numDoors")));
                carList.add(car);
            }

            this.addCars(carList).get();
            log.info("File succesfully uploaded");

        } catch (Exception e) {
            log.error("Failed to upload cars from CSV", e);
            throw new RuntimeException("Failed to upload cars from CSV", e);
        }
    }
}

