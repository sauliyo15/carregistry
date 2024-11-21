package com.sauliyo15.carregistry.service.impl;

import com.sauliyo15.carregistry.entity.CarEntity;
import com.sauliyo15.carregistry.exception.BrandNotFoundException;
import com.sauliyo15.carregistry.exception.CarNotFoundException;
import com.sauliyo15.carregistry.exception.CarsNotFoundException;
import com.sauliyo15.carregistry.model.Brand;
import com.sauliyo15.carregistry.model.Car;
import com.sauliyo15.carregistry.repository.CarRepository;
import com.sauliyo15.carregistry.service.BrandService;
import com.sauliyo15.carregistry.service.converters.CarConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarConverter carConverter;

    @Mock
    private BrandService brandService;


    @Test
    void getCars_test() throws Exception {

        //Given
        int carId = 1;

        List<CarEntity> carEntityList = new ArrayList<>();
        CarEntity carEntity = new CarEntity();
        carEntity.setId(carId);
        carEntityList.add(carEntity);

        List<Car> carList = new ArrayList<>();
        Car car = new Car();
        car.setId(carId);
        carList.add(car);

        //When
        when(carRepository.findAll()).thenReturn(carEntityList);
        when(carConverter.toCarList(carEntityList)).thenReturn(carList);

        //Then
        CompletableFuture<List<Car>> result = carService.getCars();
        assertEquals(carList, result.get());
    }

    @Test
    void getCars_test_ko() {

        // Given
        List<CarEntity> emptyCarEntityList = new ArrayList<>();

        // When
        when(carRepository.findAll()).thenReturn(emptyCarEntityList);

        // Then
        CarsNotFoundException exception = assertThrows(CarsNotFoundException.class, () -> carService.getCars());
        assertEquals("No cars found", exception.getMessage());
    }

    @Test
    void getCarById_test() {

        //Given
        int carId = 1;

        CarEntity carEntity = new CarEntity();
        carEntity.setId(carId);

        Car car = new Car();
        car.setId(carId);

        //When
        when(carRepository.findById(carId)).thenReturn(Optional.of(carEntity));
        when(carConverter.toCar(carEntity)).thenReturn(car);

        //Then
        Car result = carService.getCarById(carId);
        assertEquals(result, car);
    }

    @Test
    void getCarById_test_ko() {

        //Given
        int carId = 1;

        //When
        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        //Then
        CarNotFoundException exception = assertThrows(CarNotFoundException.class, () -> carService.getCarById(carId));
        assertEquals("Car not found with ID: " + carId, exception.getMessage());
    }

    @Test
    void addCar_test() {

        //Given
        int carId = 1;
        String brandName = "Audi";

        Brand brand = new Brand();
        brand.setName(brandName);

        Car carToSave = new Car();
        carToSave.setBrand(brand);

        CarEntity carEntityToSave = new CarEntity();

        CarEntity carEntitySaved = new CarEntity();
        carEntitySaved.setId(carId);

        Car carSaved = new Car();
        carSaved.setId(carId);

        //When
        when(brandService.getBrandByName(brandName)).thenReturn(brand);
        when(carConverter.toCarEntity(carToSave)).thenReturn(carEntityToSave);
        when(carRepository.save(carEntityToSave)).thenReturn(carEntitySaved);
        when(carConverter.toCar(carEntitySaved)).thenReturn(carSaved);

        //Then
        Car result = carService.addCar(carToSave);
        assertEquals(carSaved, result);
    }

    @Test
    void addCar_test_ko() {

        //Given
        String brandName = "Audi";

        Brand brand = new Brand();
        brand.setName(brandName);

        Car carToSave = new Car();
        carToSave.setBrand(brand);

        CarEntity carEntityToSave = new CarEntity();

        //When
        when(brandService.getBrandByName(brandName)).thenReturn(brand);
        when(carConverter.toCarEntity(carToSave)).thenReturn(carEntityToSave);
        when(carRepository.save(carEntityToSave))
                .thenThrow(new RuntimeException("Error saving car in repository"));

        //Then
        Exception exception = assertThrows(RuntimeException.class, () -> carService.addCar(carToSave));
        assertEquals("Error saving car in repository", exception.getMessage());
    }

    @Test
    void addCar_test_ko_brandNotFound() {

        //Given
        String brandName = "Audi";

        Brand brand = new Brand();
        brand.setName(brandName);

        Car carToSave = new Car();
        carToSave.setBrand(brand);

        //When
        when(brandService.getBrandByName(brandName))
                .thenThrow(new BrandNotFoundException(brandName));

        //Then
        BrandNotFoundException exception = assertThrows(BrandNotFoundException.class, () -> carService.addCar(carToSave));
        assertEquals("Brand not found with NAME: Audi", exception.getMessage());
    }

    @Test
    void updateCar_test() {

        //Given
        int carId = 1;
        String brandName = "Audi";

        Brand brand = new Brand();
        brand.setName(brandName);

        Car carToUpdate = new Car();
        carToUpdate.setBrand(brand);

        CarEntity carEntityToUpdate = new CarEntity();
        carEntityToUpdate.setId(carId);

        CarEntity carEntityUpdated = new CarEntity();

        Car carUpdated = new Car();

        //When
        when(carRepository.existsById(carId)).thenReturn(true);
        when(brandService.getBrandByName(brandName)).thenReturn(brand);
        when(carConverter.toCarEntity(carToUpdate)).thenReturn(carEntityToUpdate);
        when(carRepository.save(carEntityToUpdate)).thenReturn(carEntityUpdated);
        when(carConverter.toCar(carEntityUpdated)).thenReturn(carUpdated);

        //Then
        Car result = carService.updateCar(carId, carToUpdate);
        assertEquals(carUpdated, result);
    }

    @Test
    void updateCar_test_ko() {

        // Given
        int carId = 1;
        String brandName = "Audi";

        Brand brand = new Brand();
        brand.setName(brandName);

        Car carToUpdate = new Car();
        carToUpdate.setBrand(brand);

        CarEntity carEntityToUpdate = new CarEntity();
        carEntityToUpdate.setId(carId);

        // When
        when(carRepository.existsById(carId)).thenReturn(true);
        when(brandService.getBrandByName(brandName)).thenReturn(brand);
        when(carConverter.toCarEntity(carToUpdate)).thenReturn(carEntityToUpdate);
        when(carRepository.save(carEntityToUpdate))
                .thenThrow(new RuntimeException("Error updating car in repository"));

        // Then
        Exception exception = assertThrows(RuntimeException.class, () -> carService.updateCar(carId, carToUpdate));
        assertEquals("Error updating car in repository", exception.getMessage());
    }

    @Test
    void updateCar_test_ko_CarNotFound() {

        //Given
        int carId = 1;

        Car carToUpdate = new Car();

        //When
        when(carRepository.existsById(carId)).thenReturn(false);

        //Then
        CarNotFoundException exception = assertThrows(CarNotFoundException.class, () -> carService.updateCar(carId, carToUpdate));
        assertEquals("Car not found with ID: " + carId, exception.getMessage());
    }

    @Test
    void updateCar_test_ko_BrandNotFound() {

        //Given
        int carId = 1;
        String brandName = "Audi";

        Car carToUpdate = new Car();

        Brand brand = new Brand();
        brand.setName(brandName);
        carToUpdate.setBrand(brand);

        CarEntity carEntityToUpdate = new CarEntity();
        carEntityToUpdate.setId(carId);

        //When
        when(carRepository.existsById(carId)).thenReturn(true);
        when(brandService.getBrandByName(brandName))
                .thenThrow(new BrandNotFoundException(brandName));

        //Then
        BrandNotFoundException exception = assertThrows(BrandNotFoundException.class, () -> carService.updateCar(carId, carToUpdate));
        assertEquals("Brand not found with NAME: " + brandName, exception.getMessage());
    }

    @Test
    void deleteCar_test() {

        //Given
        int carId = 1;

        //When
        when(carRepository.existsById(carId)).thenReturn(true);

        //Then
        carService.deleteCar(carId);
    }

    @Test
    void deleteCar_test_ko() {

        //Given
        int carId = 1;

        //When
        when(carRepository.existsById(carId)).thenReturn(false);

        //Then
        CarNotFoundException exception = assertThrows(CarNotFoundException.class, () -> carService.deleteCar(carId));
        assertEquals("Car not found with ID: " + carId, exception.getMessage());
    }

    @Test
    void addCars_test() throws Exception{

        //Given
        String brandName = "Audi";

        Brand brand = new Brand();
        brand.setName(brandName);

        Car car1 = new Car();
        car1.setBrand(brand);

        Car car2 = new Car();
        car2.setBrand(brand);

        List<Car> carListToAdd = new ArrayList<>();
        carListToAdd.add(car1);
        carListToAdd.add(car2);

        List<CarEntity> carEntityListToAdd = new ArrayList<>();
        carListToAdd.forEach(car -> carEntityListToAdd.add(new CarEntity()));

        List<CarEntity> carEntityListAdded = new ArrayList<>();
        carEntityListAdded.add(new CarEntity());
        carEntityListAdded.add(new CarEntity());

        List<Car> carListAdded = new ArrayList<>();
        carListAdded.add(new Car());
        carListAdded.add(new Car());

        //When
        when(brandService.getBrandByName(brandName)).thenReturn(brand);
        when(carConverter.toCarEntity(any(Car.class))).thenReturn(new CarEntity());
        when(carRepository.saveAll(carEntityListToAdd)).thenReturn(carEntityListAdded);
        when(carConverter.toCarList(carEntityListAdded)).thenReturn(carListAdded);

        //Then
        CompletableFuture<List<Car>> result = carService.addCars(carListToAdd);
        assertEquals(carListAdded, result.get());
    }

    @Test
    void addCars_test_ko() {

        // Given
        String brandName = "Audi";

        Brand brand = new Brand();
        brand.setName(brandName);

        Car car1 = new Car();
        car1.setBrand(brand);

        Car car2 = new Car();
        car2.setBrand(brand);

        List<Car> carListToAdd = List.of(car1, car2);

        CarEntity carEntity1 = new CarEntity();
        CarEntity carEntity2 = new CarEntity();
        List<CarEntity> carEntityListToAdd = List.of(carEntity1, carEntity2);

        // When
        when(brandService.getBrandByName(brandName)).thenReturn(brand);
        when(carConverter.toCarEntity(car1)).thenReturn(carEntity1);
        when(carConverter.toCarEntity(car2)).thenReturn(carEntity2);
        when(carRepository.saveAll(carEntityListToAdd))
                .thenThrow(new RuntimeException("Error saving cars in repository"));

        // Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> carService.addCars(carListToAdd).join());
        assertEquals("Error saving cars in repository", exception.getMessage());
    }

    @Test
    void addCars_test_ko_carEmptyList() throws Exception {

        // Given
        List<Car> carListToAdd = new ArrayList<>();

        // When
        CompletableFuture<List<Car>> result = carService.addCars(carListToAdd);

        // Then
        assertTrue(result.get().isEmpty(), "The result should be an empty list");
    }

    @Test
    void addCars_test_ko_BrandNotFound() {

        // Given
        String brandName = "Audi";

        Brand brand = new Brand();
        brand.setName(brandName);

        Car car1 = new Car();
        car1.setBrand(brand);

        Car car2 = new Car();
        car2.setBrand(brand);

        List<Car> carListToAdd = List.of(car1, car2);

        // When
        when(brandService.getBrandByName(brandName))
                .thenThrow(new BrandNotFoundException(brandName));

        // Then
        BrandNotFoundException exception = assertThrows(BrandNotFoundException.class, () -> carService.addCars(carListToAdd).join());
        assertEquals("Brand not found with NAME: " + brandName, exception.getMessage());
    }
}
